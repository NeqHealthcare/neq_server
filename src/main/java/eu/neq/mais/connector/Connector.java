package eu.neq.mais.connector;

/**
 * Connector interface to standardize the connector implemetations.
 * One connector builds one connection to one back-end HIS, like GNU Health or SAP Healthcare.
 * @author seba
 *
 */
public interface Connector {
	
	/**
	 * Loging into the Backend
	 * @param username
	 * @param password
	 * @return Session ID
	 */
	public String login(String username, String password);
	
	/**
	 * Logout
	 */
	public void logout();
	
	/**
	 * Dummy method...
	 * @param o
	 */
	public Object exec(Object o);
	
}
