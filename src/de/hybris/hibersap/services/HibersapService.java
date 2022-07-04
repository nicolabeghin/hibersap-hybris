/**
 *
 */
package de.hybris.hibersap.services;

import java.util.List;

import de.hybris.hibersap.models.BapiMain;
import org.hibersap.session.SessionManager;



/**
 * @author nbeghin
 *
 */
public interface HibersapService
{

	public abstract void setRfcDestinationName(String rfcDestinationName);

	public abstract String getRfcDestinationName();

	public abstract SessionManager getSessionManager() throws Exception;

	public List<Class> getBapiClasses();

	public void execute(BapiMain bapi) throws Exception;

	void addBapiClass(Class c);
}