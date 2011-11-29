package eu.neq.mais.request;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.SessionStore;

@Path("/connection/")
public class ConnectionHandler {
	
	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");
	
	private Connector connector;

	 /**
	 * Example url: http://localhost:8080/connection/login?username=admin&password=iswi223<<&backendSid=gnuhealth1
	 *
	 * A json object is returned:
	 * Login successful: session
	 * Login unsuccessful: false as string
	 * 
	 * @param backendSid
	 * @param username
	 * @param password
	 * @return
	 */
	@GET
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(
			@QueryParam("backendSid") String backendSid
			,@QueryParam("username") String username,
			@QueryParam("password") String password){
		
		try {
			connector = ConnectorFactory.getConnector(backendSid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String session = connector.login(username, password);
		//works only as long as nobody changes the connector.login method :)
		if(session.length()>5){
			SessionStore.put(session, backendSid);
		}
		logger.info("login method: returned json object: "+new Gson().toJson(session));
		return new Gson().toJson(session);
	}
	
	/**
	 * Example url: http://localhost:8080/connection/logout?username=admin&session=SESSION_VARIABLE
	 * 
	 * @param backendSid
	 * @param username
	 * @param session
	 * 
	 * Login successful: true as string
	 * Login unsuccessful: false as string
	 */
	@GET
	@Path("logout")
	@Produces(MediaType.APPLICATION_JSON)
	public String logout(@QueryParam("username") String username,@QueryParam("session") String session){
		System.out.println("session: "+session);
		try {
			connector = ConnectorFactory.getConnector(SessionStore.getBackendSid(session));
			String result = connector.logout(username, session);
			logger.info("logout successful: returned json object: "+new Gson().toJson(result));
			return new Gson().toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		logger.info("logout unsuccessful: returned json object: "+new Gson().toJson("false"));
		return new Gson().toJson("false");
	}

}
