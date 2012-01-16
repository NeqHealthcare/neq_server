package eu.neq.mais.connector;

import java.util.logging.Level;
import java.util.logging.Logger;

import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.Settings;

/**
 * Connector interface to standardize the connector implemetations.
 * One connector builds one connection to one back-end HIS, like GNU Health or SAP Healthcare.
 * @author seba
 *
 */
public abstract class Connector {
	
	private Backend backend;
	protected static Logger logger = Logger.getLogger("eu.neq.mais.connector");
	
	public Connector() {
		logger.setLevel(Level.ALL); // DEBUG PURPOSES
		logger.addHandler(FileHandler.getLogFileHandler(Settings.LOG_FILE_CONNECTORS));
	}
	
	/**
	 * Loging into the Backend
	 * 
	 *
	 * @param username
	 * @param password
	 * @return Session ID:
	 * Login successful: session
	 * Login unsuccessful: false as string
	 */
	public abstract String login(String username, String password);
	
	/**
	 * 
	 * @param username
	 * @param Session
	 * @return
	 */
	public abstract String logout(String username, String Session);
	

	/**
	 * 
	 * @param session
	 * @param method
	 * @param params
	 * @return
	 */
	public abstract String execute(String session, String method, Object[] params);
		
	public void setBackend(Backend backend){
		this.backend = backend;
	}
	
	public Backend getBackend(){
		return this.backend;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		Connector.logger = logger;
	}
	
	
	
	/*-----  BACKEND METHODS  ----*/
	
	public abstract String getLoginMethod();
	public abstract String getLogoutMethod();
	public abstract String getPatientSearchMethod();
	public abstract String getPatientReadMethod();
	public abstract String getReferencesMethod();
	public abstract String getDiagnoseReadMethod();
	
	
	
	/*-----  BACKEND METHOD PARAMS  ----*/
	
	public abstract Object[] getReturnAllPatientsParams(String session);
	
	public abstract Object[] getReturnPatientParams(String session, String id);
	public abstract Object[] getReturnDiagnoseParams(String session, String id);
}
