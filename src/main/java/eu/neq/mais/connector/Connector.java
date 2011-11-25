package eu.neq.mais.connector;

import eu.neq.mais.technicalservice.Backend;

/**
 * Connector interface to standardize the connector implemetations.
 * One connector builds one connection to one back-end HIS, like GNU Health or SAP Healthcare.
 * @author seba
 *
 */
public abstract class Connector {
	
	private Backend backend;
	
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
	public abstract void logout();
	
	/**
	 * Dummy method...
	 * @param o
	 */
	public abstract String exec(String method, String[] params, String id);
	
	
	public void setBackend(Backend backend){
		this.backend = backend;
	}
	
	public Backend getBackend(){
		return this.backend;
	}
	
}
