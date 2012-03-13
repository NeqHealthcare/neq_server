package eu.neq.mais.request;

import java.util.List;
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
@Path("/patient/")
public class PatientHandler {

	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

	private Connector connector;

	/**
	 * 
	 * @param session
	 * @return successfull: json object
	 *         Request failed: false
	 * 
	 */
	@GET
	@Path("/bla")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnPatient(@Context HttpServletResponse servletResponse, @QueryParam("session") String session,
			@QueryParam("query") String query,
			@QueryParam("ownonly") String ownonly
			)
	{

		String response = new DTOWrapper().wrapError("Error while retrieving patient");
		
		servletResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
        servletResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
        servletResponse.addHeader("Access-Control-Allow-Origin", "*"); 
        servletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With"); 
        servletResponse.addHeader("Access-Control-Max-Age", "60"); 
		
		try {
			
	    connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
			
	    if 
		(ownonly == "true")
		{
			List<?> patientList = connector.returnPersonalPatientsForUIList(session);
			response = new DTOWrapper().wrap(patientList);
			logger.info("return patients method returned json object filled with all patients for a specific primary care doctor: " + response);
		}
		else if
		(query != null)
		{
			List<?> patientList = connector.searchForAPatient(query);
			response = new DTOWrapper().wrap(patientList);
			logger.info("return patient method returned json object: " + response);
		}
		else {
			List<?> patientList = connector.returnAllPatientsForUIList();
			response = new DTOWrapper().wrap(patientList);
			logger.info("return patients method returned json object filled with all patients: " + response);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} catch (NoSessionInSessionStoreException es) {
			response = new DTOWrapper().wrapError(es.toString());
		}
		
		servletResponse.setContentType(response);
		return servletResponse.getContentType();
	
	}

}
