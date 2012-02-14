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
	
	@GET
	@Path("/one")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnDiagnose(@QueryParam("session") String session,
			@QueryParam("id") String id) {
		
		String diagnose = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			diagnose = connector.returnDiagnose(id);

		} catch (Exception e) {
			e.printStackTrace();
			diagnose = "false";
		}
		logger.info("return diagnose method returned json object: " + diagnose);
		return diagnose;
		
		
	}





	
}
