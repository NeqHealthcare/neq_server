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
	 * @param username
	 * @param password
	 * @return Session ID
	 */
	public abstract String login(String username, String password);
	
	/**
	 * Logout
	 */
	public abstract String logout(String username, String Session);
	
	/**
	 * Dummy method...
	 * @param o
	 */
	public abstract String db_exec(String method, String[] params, String id);
	
	
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
	
}
