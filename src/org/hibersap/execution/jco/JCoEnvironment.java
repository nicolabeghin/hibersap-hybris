/*
 * Copyright (c) 2008-2014 akquinet tech@spree GmbH
 *
 * This file is part of Hibersap.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this software except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hibersap.execution.jco;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibersap.HibersapException;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;


/**
 * This class acts as a wrapper for the ugly static JCo classes.
 *
 * @author Carsten Erker
 */
public class JCoEnvironment
{

	private static final Log LOG = LogFactory.getLog(JCoEnvironment.class);
	public static String RFC_DESTINATION_NAME = null;
	/**
	 * JCo's Environment class doesn't offer any methods to check if a provider class is already registered, but we need
	 * to dynamically register destinations
	 */
	private static final JCoDataProvider destinationDataProvider = new JCoDataProvider();

	private JCoEnvironment()
	{
		// should not be instantiated
	}

	public static void registerDestination(final String destinationName, final Properties jcoProperties)
	{
		LOG.info("Disabled - Registering destination " + destinationName);

		// if ( !destinationDataProvider.hasDestinations() ) {
		// registerDestinationDataProvider();
		// }
		//
		// destinationDataProvider.addDestination( destinationName,
		// jcoProperties );
	}

	public static void unregisterDestination(final String destinationName)
	{
		LOG.info("Disabled - Unregistering destination " + destinationName);
		//
		// destinationDataProvider.removeDestination( destinationName );
		//
		// if ( !destinationDataProvider.hasDestinations() ) {
		// unregisterDestinationDataProvider();
		// }
	}

	public static JCoDestination getDestination(final String destinationName)
	{
		try
		{
			if (RFC_DESTINATION_NAME == null || RFC_DESTINATION_NAME.isEmpty())
			{
				throw new JCoException(0, "JCOEnvironment.RFC_DESTINATION_NAME not set");
			}
			return JCoDestinationManager.getDestination(RFC_DESTINATION_NAME);
		}
		catch (final JCoException e)
		{
			throw new HibersapException("Destination named '" + destinationName + "' is not registered with JCo", e);
		}
	}

	private static void registerDestinationDataProvider()
	{
		LOG.info("Disabled - Registering DestinationDataProvider with JCo");

		// Environment.registerDestinationDataProvider( destinationDataProvider
		// );
	}

	private static void unregisterDestinationDataProvider()
	{
		LOG.info("Disabled - Unregistering DestinationDataProvider from JCo");

		// Environment.unregisterDestinationDataProvider(
		// destinationDataProvider );
	}
}
