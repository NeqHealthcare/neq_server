package eu.neq.mais.request;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;

@Path("/{backendUri}/{backendSid}/connection/")
public class ConnectionHandler {
	
	private Connector connector;
	
	@GET
	@Path("{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@PathParam("backendUri") String backendUri,@PathParam("backendSid") String backendSid
			,@PathParam("username") String username,@PathParam("password") String password){
		
		try {
			connector = ConnectorFactory.getConnector(backendUri, backendSid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String session = connector.login(username, password);
		
		return session;

	}
	
	/**
	 * Still problems with the SESSION String (contains lots of /// :()
	 * @param backendUri
	 * @param backendSid
	 * @param username
	 * @param session
	 */
	@PUT
	@Path("logout/{username}/{session}")
	@Produces(MediaType.APPLICATION_JSON)
	public void logout(@PathParam("backendUri") String backendUri,@PathParam("backendSid") String backendSid
			,@PathParam("username") String username,@PathParam("session") String session){
		
		try {
			connector = ConnectorFactory.getConnector(backendUri, backendSid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connector.logout(username, session);
	}

}
