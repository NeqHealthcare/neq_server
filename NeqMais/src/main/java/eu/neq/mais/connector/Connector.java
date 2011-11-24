package eu.neq.mais.connector;

/**
 * 
 * @author seba
 *
 */
public interface Connector {
	

	public String login(String username, String password);
	public void logout();
	
	public void exec(Object o);
	
}
