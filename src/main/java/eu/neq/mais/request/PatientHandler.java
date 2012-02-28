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
@Path("/patients/")
public class PatientHandler {

	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

	private Connector connector;

	/**
	 * @param session - current session
	 * @param query - query parameter
	 * @return successfull: json object
	 *         Request failed: false
	 */
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnPatient(@QueryParam("session") String session,
			@QueryParam("query") String param) {

		String patientList = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			patientList = connector.searchForAPatient(param);
		} catch (Exception e) {
			e.printStackTrace();
			patientList = "false";
		}
		logger.info("return patient method returned json object: " + patientList);
		return patientList;

	}

	/**
	 * 
	 * @param session
	 * @return successfull: json object
	 *         Request failed: false
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAllPatients(@QueryParam("session") String session) {

		String patientList = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			patientList = connector.returnAllPatientsForUIList();

		} catch (Exception e) {
			e.printStackTrace();
			patientList = "false";
		}
		logger.info("return patients method returned json object filled with al patients: " + patientList);
		return patientList;

	}
	
	
	/**
	 * 
	 * @param session
	 * @return successfull: json object
	 *         Request failed: false
	 * 
	 */
	@GET
	@Path("/all_for_user")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAUsersPatients(@Context HttpServletResponse servlerResponse, @QueryParam("session") String session) {
		
	  
	    servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
	    servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
	    servlerResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:10920"); 
	    servlerResponse.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With"); 
	    
		String patientList = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			patientList = connector.returnPersonalPatientsForUIList(session);
		} catch (Exception e) {
			e.printStackTrace();
			patientList = "false";
		}
		
		logger.info("return patients method returned json object filled with all patients for a specific primary care doctor: " + patientList);
		
		servlerResponse.setContentType(patientList);
		return servlerResponse.getContentType();

	}
	
	@OPTIONS
	@Path("/all_for_user")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAUsersPatients_Options(@Context HttpServletResponse servlerResponse, @QueryParam("session") String session) {

		
		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
	    servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
	    servlerResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:10920"); 
	    servlerResponse.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With"); 
	    //servlerResponse.addHeader("Access-Control-Max-Age", "60"); 
	    
		return servlerResponse.getContentType();

	}
	

}
