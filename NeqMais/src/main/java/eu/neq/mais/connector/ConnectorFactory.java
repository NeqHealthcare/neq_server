package eu.neq.mais.connector;

import eu.neq.mais.Main;
import eu.neq.mais.domain.Patient;

public abstract class ConnectorFactory {
	
	private static String connectorClass = "eu.neq.mais.connector.impl.TestDataConnectorImpl";

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