package eu.neq.mais.connector;

import java.util.Map;

import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.FileHandler;

/**
 * Loads a back-end connector based no the configuration of the mais.
 * @author seba
 *
 */
public abstract class ConnectorFactory {
	

	public static Connector getConnector(String backendUri, String sid) throws Exception {
		
		Map<String,Backend> backendMap = FileHandler.getBackendMap();
		
		Backend requiredBackend = backendMap.get(backendUri+" "+sid);
		
		Connector connector = (Connector) (Connector.class.getClassLoader().loadClass(requiredBackend.getConnector())).newInstance();
		connector.setConfigurationData(requiredBackend);
		return connector;
	}


}