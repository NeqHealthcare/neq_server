package eu.neq.mais.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.SessionStore;

@Path("/connection/")
public class ConnectionHandler {
	
	private Connector connector;
	
	/**
	 *
	 * A json object is returned:
	 * Login successful: session
	 * Login unsuccessful: empty string
	 * 
	 * @param backendSid
	 * @param username
	 * @param password
	 * @return
	 */
	@GET
	@Path("login/{username}/{password}/{backendSid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@PathParam("backendSid") String backendSid
			,@PathParam("username") String username,@PathParam("password") String password){
		
		try {
			connector = ConnectorFactory.getConnector(backendSid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String session = connector.login(username, password);
		//works only as long as nobody changes the connector.login method :)
		if(session.length()>0){
			SessionStore.put(session, backendSid);
		}
	
		return new Gson().toJson(session);
	}
	
	/**
	 * Still problems with the SESSION String (contains lots of /// :()
	 * @param backendUri
	 * @param backendSid
	 * @param username
	 * @param session
	 */
	@GET
	@Path("logout/{username}/{session}")
	@Produces(MediaType.APPLICATION_JSON)
	public String logout(@PathParam("username") String username,@PathParam("session") String session){
		
		try {
			connector = ConnectorFactory.getConnector(SessionStore.getBackendSid(session));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return new Gson().toJson(connector.logout(username, session));
	}

}
