package eu.neq.mais.connector;

import eu.neq.mais.Main;
import eu.neq.mais.domain.Patient;

/**
 * Loads a back-end connector based no the configuration of the mais.
 * @author seba
 *
 */
public abstract class ConnectorFactory {
	
	// Classname of the connector. Since no init.file yet, simply a preset string...
	private static String connectorClass = "eu.neq.mais.connector.impl.TestDataConnectorImpl";

	/**
	 * Returns the configured connector object.
	 * Get and set this with the corresponding getter/setter method.
	 * @return Connector Instance
	 */
	public static Connector createConnector() {

		ClassLoader loader = Main.class.getClassLoader();

		Connector tmpConnector = null;
		
		try {
			Class tmpClass = loader.loadClass(connectorClass);
			tmpConnector = (Connector) tmpClass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return tmpConnector;
	}

	public static String getConnectorClass() {
		return connectorClass;
	}

	public static void setConnectorClass(String connectorClass) {
		ConnectorFactory.connectorClass = connectorClass;
	}


}