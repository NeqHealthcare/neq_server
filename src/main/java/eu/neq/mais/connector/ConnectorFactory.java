package eu.neq.mais.connector;

import java.util.Map;

import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.FileHandler;

/**
 * Loads a back-end connector based no the configuration of the mais.
 * @author Sebastian Schütz, Jan Gansen
 *
 */
public abstract class ConnectorFactory {
	
	private static Map<String,Backend> backendMap = null;

	public static Connector getConnector(String backendSid) throws Exception {
		
		Backend requiredBackend = FileHandler.getBackendMap().get(backendSid);
		
		Connector connector;
		
		if(requiredBackend.getConnectorImpl() == null){
			connector = (Connector) (Connector.class.getClassLoader().loadClass(requiredBackend.getConnector())).newInstance();
			connector.setBackend(requiredBackend);
			requiredBackend.setConnectorImpl(connector);
		}else{
			connector = requiredBackend.getConnectorImpl();
		}
		return connector;
	}
	


}