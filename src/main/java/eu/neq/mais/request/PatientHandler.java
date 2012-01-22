package eu.neq.mais.request;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
			patientList = connector.searchForAPatient(session,param);
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
			patientList = connector.returnAllPatientsForUIList(session);

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
	public String returnAUsersPatients(@QueryParam("session") String session) {

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
		return patientList;

	}

}
