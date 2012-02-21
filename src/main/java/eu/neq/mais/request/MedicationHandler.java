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
@Path("/medication/")
public class MedicationHandler {

	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

	private Connector connector;
	
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnDiagnose(@QueryParam("session") String session,
			@QueryParam("patientId") String patientId) {
		
		String medications = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			medications = connector.returnMedicationsForPatient(patientId);

		} catch (Exception e) {
			e.printStackTrace();
			medications = "false";
		}
		logger.info("return medication method returned json object: " + medications);
		return medications;
		
		
	}


}