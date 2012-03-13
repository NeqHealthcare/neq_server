package eu.neq.mais.request;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.SessionStore;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;

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
	public String login(@Context HttpServletResponse servlerResponse,
			@QueryParam("backendSid") String backendSid,
			@QueryParam("username") String username,
			@QueryParam("password") String password) {


		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*"); 
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With"); 
        servlerResponse.addHeader("Access-Control-Max-Age", "60"); 
        
        String response = new DTOWrapper().wrapError("Error while login");
        String session  = "false";
        
		try {
			connector = ConnectorFactory.getConnector(backendSid);
			session = connector.login(username, password, backendSid);
			
			if (session.contains("false")) response = new DTOWrapper().wrapError("Wrong login credentials (username/password). Please try again.");
			else response = new DTOWrapper().wrap(session);
		} catch (Exception e) {
			e.printStackTrace();
		} 

		logger.info("login method returned json object: " + response);
		
		servlerResponse.setContentType(response);
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
	public String logout(@Context HttpServletResponse servlerResponse,
			@QueryParam("username") String username,
			@QueryParam("session") String session) {

		String response = new DTOWrapper().wrapError("Error while logout");
		String result = "false";
		
		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*"); 
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With"); 
        servlerResponse.addHeader("Access-Control-Max-Age", "60"); 

		try {
			connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
			result = connector.logout(username, session);
			response = new DTOWrapper().wrap(result);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (NoSessionInSessionStoreException e) {
			response = new DTOWrapper().wrapError(e.toString());
		}
		if(result.equals("true")){
			NeqServer.getSessionStore().removeKeyValuePair(session);
		}
		logger.info("logout method returned json object: " + response);
		servlerResponse.setContentType(response);
		return servlerResponse.getContentType();
	}

}
