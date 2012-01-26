package eu.neq.mais.request;

import java.io.File;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.SessionStore;
import javax.activation.MimetypesFileTypeMap;

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
	public String returnPersnoalData(@QueryParam("session") String session,
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

	@GET
	@Path("/image/")
	@Produces("image/*")
	public Response returnImage(@QueryParam("session") String session, @QueryParam("size") String size){
		String imagePath = "false";
		File image = new File(imagePath);
		
		if(!image.exists()){
			throw new WebApplicationException(404);
		}
		
		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
//			imagePath = connector.getImage(session,size);
		} catch (Exception e) {
			e.printStackTrace();
			imagePath = "false";
		}
		String mt = new MimetypesFileTypeMap().getContentType(image);

		logger.info("return image for a specific user: ");
		return Response.ok(image, mt).build();
	}
	
}

