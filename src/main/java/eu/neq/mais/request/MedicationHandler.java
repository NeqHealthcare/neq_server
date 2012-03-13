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
import eu.neq.mais.domain.Medication;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.SessionStore;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;


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
	public String returnDiagnose(@Context HttpServletResponse servlerResponse,
			@QueryParam("session") String session,
			@QueryParam("patientId") String patientId) {
		
		String response = new DTOWrapper().wrapError("Error while retrieving Medication");

		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*"); 
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With"); 
        servlerResponse.addHeader("Access-Control-Max-Age", "60"); 
		
		try {
			connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
			List<?> meds = connector.returnMedicationsForPatient(patientId);
			response = new DTOWrapper().wrap(meds);
			
		} catch (Exception e) {
			e.printStackTrace();
		} catch (NoSessionInSessionStoreException e) {
			response = new DTOWrapper().wrapError(e.toString());
		}
		logger.info("return medication method returned json object: " + response);

		servlerResponse.setContentType(response);
		return servlerResponse.getContentType();
		
	}


}