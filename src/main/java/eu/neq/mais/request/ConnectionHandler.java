package eu.neq.mais.request;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.server.Response;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.SessionStore;

/**
 * 
 * @author Jan Gansen
 *
 */



@Path("/connection/")
public class ConnectionHandler {

	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

	private Connector connector;

	/**
	 * Example url:
	 * http://localhost:8080/connection/login?username=admin&password
	 * =iswi223<<&backendSid=gnuhealth1
	 * 
	 * A json object is returned: Login successful: session 
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
	public String login(@Context HttpServletResponse servlerResponse, @QueryParam("backendSid") String backendSid,
		@QueryParam("username") String username,
		@QueryParam("password") String password) {
		
		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*"); 
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With"); 
        servlerResponse.addHeader("Access-Control-Max-Age", "60"); 
        

		String session = "false";

		try {
			
			connector = ConnectorFactory.getConnector(backendSid);
			session = connector.login(username, password,backendSid);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("login method returned json object: " + session);
		servlerResponse.setContentType(session);
		return servlerResponse.getContentType();
	}
	
	@OPTIONS
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public String loginOptions(@Context HttpServletResponse servlerResponse, @QueryParam("backendSid") String backendSid,
		@QueryParam("username") String username,
		@QueryParam("password") String password) {
		
		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*"); 
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With"); 
        servlerResponse.addHeader("Access-Control-Max-Age", "60"); 
        

		String session = "false";

		try {
			
			connector = ConnectorFactory.getConnector(backendSid);
			session = connector.login(username, password,backendSid);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("login method returned json object: " + session);
		servlerResponse.setContentType(session);
		return servlerResponse.getContentType();
	}

	/**
	 * Example url:
	 * http://localhost:8080/connection/logout?username=admin&session
	 * =SESSION_VARIABLE
	 * 
	 * @param backendSid
	 * @param username
	 * @param session
	 * 
	 * Logout successful: true as string 
	 * Logout unsuccessful: false as string
	 */
	@GET
	@Path("logout")
	@Produces(MediaType.APPLICATION_JSON)
	public String logout(@QueryParam("username") String username,
			@QueryParam("session") String session) {

		String result = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			result = connector.logout(username, session);
		} catch (Exception e) {
			e.printStackTrace();
			result = "false";
		}
		if(result.equals("true")){
			SessionStore.removeKeyValuePair(session);
		}
		logger.info("logout method returned json object: " + result);
		return result;
	}

}
