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
 * @author Denny, Jan
 *
 */
@Path("/diagnose/")
public class DiagnoseHandler {
	

	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

	private Connector connector;
	
//	Example request:
//	http://localhost:8080/diagnosis?id=1&session=SESSION

	//Example response:
//	 {"status": "u", "pathology.rec_name": "Paratyphoid fever B", "pregnancy_warning": 
//		false, "is_active": true, "short_comment": null, "id": 1, "diagnosed_date": {"month": 1, "__class__": "date", "day": 13, "year": 2011}, 
//		"healed_date": {"month": 1, "__class__": "date", "day": 3, "year": 2012}, "pathology": 8, "disease_severity": "2_mo", "is_infectious": true, 
//		"is_allergy": true}
//	
	
	
	@GET
	@Path("/one")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnDiagnose(@QueryParam("session") String session,
			@QueryParam("id") String id) {
		
		String diagnose = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			diagnose = connector.returnDiagnose(session, id);

		} catch (Exception e) {
			e.printStackTrace();
			diagnose = "false";
		}
		logger.info("return diagnose method returned json object: " + diagnose);
		return diagnose;
		
		
	}





	
}
