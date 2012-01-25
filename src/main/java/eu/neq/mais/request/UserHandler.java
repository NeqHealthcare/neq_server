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
@Path("/user/")
public class UserHandler {
	
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
	@Path("/personalInformation")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAUsersPatients(@QueryParam("session") String session,
			@QueryParam("picture") String picture,
			@QueryParam("name") String name) {

		String personalInformation;

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			personalInformation = connector.returnPersonalInformation(session, Boolean.parseBoolean(name), Boolean.parseBoolean(picture));
		} catch (Exception e) {
			e.printStackTrace();
			personalInformation = "false";
		}
		logger.info("return personal information for a specific user: " + personalInformation);
		return personalInformation;

	}
}
