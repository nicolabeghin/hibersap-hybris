/**
 *
 */
package de.hybris.hibersap.services.impl;

import de.hybris.hibersap.models.BapiMain;
import de.hybris.hibersap.services.HibersapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hybris.platform.sap.core.configuration.model.SAPConfigurationModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibersap.annotations.Bapi;
import org.hibersap.configuration.AnnotationConfiguration;
import org.hibersap.configuration.xml.SessionManagerConfig;
import org.hibersap.execution.jco.JCoContext;
import org.hibersap.execution.jco.JCoEnvironment;
import org.hibersap.session.Session;
import org.hibersap.session.SessionManager;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @author nbeghin
 *
 */
@Service
public class DefaultHibersapService implements HibersapService {
    private String rfcDestinationName;
    private final Logger LOG = Logger.getLogger(DefaultHibersapService.class);
    private final List<Class> bapiClasses = new ArrayList<Class>();
    private BaseStoreService baseStoreService;
    private SessionManager sessionManager;
    private boolean addedNewBapiClasses = true;
    private ObjectMapper objectMapper = new ObjectMapper();

    public DefaultHibersapService(final String rfcDestinationName) {
        super();
        this.rfcDestinationName = rfcDestinationName;
        LOG.info("Initializing target RFC destination " + rfcDestinationName);

        addInitialClasses("de.hybris.hibersap");
    }

    public DefaultHibersapService() {
        super();
        LOG.info("Initializing without target RFC destination");

        addInitialClasses("de.hybris.hibersap");
    }

    /*
     * (non-Javadoc)
     *
     * @see HibersapService#getSessionManager()
     */
    @Override
    public SessionManager getSessionManager() throws Exception {
        if (sessionManager != null && addedNewBapiClasses == false) {
            return sessionManager;
        }
        String rfcName = this.rfcDestinationName; // default as fallback
        if (baseStoreService.getCurrentBaseStore() != null && baseStoreService.getCurrentBaseStore().getSAPConfiguration() != null) {
            SAPConfigurationModel sapConfiguration = baseStoreService.getCurrentBaseStore().getSAPConfiguration();
            if (sapConfiguration.getSAPRFCDestination() != null && StringUtils.isNotBlank(sapConfiguration.getSAPRFCDestination().getRfcDestinationName())) {
                    rfcName = sapConfiguration.getSAPRFCDestination().getRfcDestinationName();
                    LOG.info("RFC destination name for current base store is " + rfcName);
            }
        }
        JCoEnvironment.RFC_DESTINATION_NAME = rfcName;
        final SessionManagerConfig cfg = new SessionManagerConfig(rfcName).setContext(JCoContext.class.getName());
        for (final Class annotatedClass : bapiClasses) {
            cfg.addAnnotatedClass(annotatedClass);
        }
        final AnnotationConfiguration configuration = new AnnotationConfiguration(cfg);
        sessionManager = configuration.buildSessionManager();
        addedNewBapiClasses = false;
        return sessionManager;
    }

    /*
     * (non-Javadoc)
     *
     * @see HibersapService#setRfcDestinationName(java.lang.String)
     */
    @Override
    @Required
    public void setRfcDestinationName(final String rfcDestinationName) {
        LOG.info("Setting target RFC destination " + rfcDestinationName);
        this.rfcDestinationName = rfcDestinationName;
    }

    /*
     * (non-Javadoc)
     *
     * @see HibersapService#getRfcDestinationName()
     */
    @Override
    public String getRfcDestinationName() {
        return rfcDestinationName;
    }

    /*
     * (non-Javadoc)
     *
     * @see HibersapService#getBapiClasses()
     */
    @Override
    public List<Class> getBapiClasses() {
        return bapiClasses;
    }

    @Override
    public void execute(BapiMain bapi) throws Exception {
        this.addBapiClass(bapi.getClass());

        Session session = getSessionManager().openSession();
        try {
            session.execute(bapi);
        } finally {
            session.close();

            String bapiClass = bapi.getClass().toString();
            if(Config.getBoolean("logging.hibersap."+bapiClass, false)) {
                LOG.debug("execute BAPI [" + bapiClass + "] -> " + objectMapper.writeValueAsString(bapi));
            }
        }
    }

    @Override
    public void addBapiClass(Class c) {
        if (!getBapiClasses().contains(c)) {
            LOG.info("Adding bapi class " + c);
            getBapiClasses().add(c); // dynamically add the current BAPI class to the session manager
            addedNewBapiClasses = true;
        }
    }

    private void addInitialClasses(String basePackage){
        Reflections reflections = new Reflections(basePackage);
        Set<Class<? extends BapiMain>> subTypes = reflections.getSubTypesOf(BapiMain.class);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Bapi.class);

        for (Class cl: annotated) {
            addBapiClass(cl);
        }
    }

    public BaseStoreService getBaseStoreService() {
        return baseStoreService;
    }

    public void setBaseStoreService(BaseStoreService baseStoreService) {
        this.baseStoreService = baseStoreService;
    }
}
