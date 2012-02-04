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
 */

/**
 * @author user
 *
 */

@Path("/dashboard/")
public class PatientDashBoardHandler {

	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

	private Connector connector;
	
//	Example request:
//	http://localhost:8080/diagnosis?id=1&session=SESSION
	
	@GET
	@Path("/one")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnDashboardData(@QueryParam("session") String session,
			@QueryParam("id") String id) {
		
		String dashboard = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			dashboard = connector.returnDashBoardData(session, id);

		} catch (Exception e) {
			e.printStackTrace();
			dashboard = "false";
		}
		logger.info("return diagnose method returned json object: " + dashboard);
		return dashboard;
		
		
	}



}
