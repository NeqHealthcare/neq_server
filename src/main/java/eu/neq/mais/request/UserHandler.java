package eu.neq.mais.request;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.User;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.ImageHandler;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;
import eu.neq.mais.technicalservice.Settings;
import eu.neq.mais.technicalservice.storage.DbHandler;
import eu.neq.mais.technicalservice.storage.Login;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Jan Gansen
 */
@Path("/user/")
public class UserHandler {


    protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

    private Connector connector;

    /**
     * @return successfull: json object
     *         Request failed: false
     */

    @OPTIONS
    @Path("/personalInformation")
    public Response returnPersnoalDataOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return null;

    }

    @GET
    @Path("/personalInformation")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnPersnoalData(@Context HttpServletResponse servlerResponse, @QueryParam("session") String session) {


        String response = new DTOWrapper().wrapError("Error while retrieving your personal information");
        User person = null;

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            String user_id = String.valueOf(NeqServer.getSessionStore().getUserId(session));
            person = connector.returnPersonalInformation(user_id);

            /*
            * Number of patients
            */
            List<?> nrOfpatients = connector.returnPersonalPatientsForUIList(session);
            person.setNumber_of_patients(String.valueOf(nrOfpatients.size()));

            /*
            * last login
            */
            DbHandler dbH = new DbHandler();
            Login l = dbH.getLatestLogin(user_id);
            dbH.close();
            person.setLastLogin(l.getDateOfLogin());

            response = new DTOWrapper().wrap(person);
        } catch (Exception e) {
            e.printStackTrace();
            response = new DTOWrapper().wrapError("Error while retrieving your personal information: " + e.toString());
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError("Error while retrieving your personal information: " + e.toString());
        }

        return response;
    }


    @OPTIONS
	@Path("/image")
	public Response optionsPic(@Context HttpServletResponse servlerResponse) {

		servlerResponse.addHeader("Allow-Control-Allow-Methods",
				"POST,GET,OPTIONS");
		servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
		servlerResponse.addHeader("Access-Control-Allow-Origin",
				Settings.ALLOW_ORIGIN_ADRESS);
		servlerResponse.addHeader("Access-Control-Allow-Headers",
				"Content-Type,X-Requested-With");
		servlerResponse.addHeader("Access-Control-Max-Age", "60");

		return null;

	}

	@GET
	@Path("/image/{id}")
	@Produces("image/png")
	public Response getPic(@PathParam("id") String id,
			@QueryParam("width") String width,
			@QueryParam("height") String height,
			@Context HttpServletResponse servlerResponse) {

		servlerResponse.addHeader("Allow-Control-Allow-Methods",
				"POST,GET,OPTIONS");
		servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
		servlerResponse.addHeader("Access-Control-Allow-Origin",
				Settings.ALLOW_ORIGIN_ADRESS);
		servlerResponse.addHeader("Access-Control-Allow-Headers",
				"Content-Type,X-Requested-With");
		servlerResponse.addHeader("Access-Control-Max-Age", "60");

		if (!id.contains(".jpg"))
			id += ".jpg";

		String path = System.getProperty("user.dir")+Settings.DOCTOR_IMAGE_PATH;
		File img = new File(path + id);

		if (!img.exists()) {
			img = new File(path+"default_user.png");
		}

		BufferedImage image;
		try {
			image = ImageIO.read(img);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			if (height != null && width != null) {
				image = ImageHandler.getScaledImage(image, Integer.parseInt(width), Integer.parseInt(height));
			}
			
			ImageIO.write(image, "png", baos);

			return Response.ok(baos.toByteArray()).build();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}
	
	
    @GET
    @Path("/lastLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnLastLogin(@Context HttpServletResponse servlerResponse, @QueryParam("session") String session) {

        String response = new DTOWrapper().wrapError("Error while recieving latest login for user");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*");
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");
        try {
            response = new DTOWrapper().wrapError("Please use the user/personalInformation webservice to access this information");
        } catch (Exception e) {
            e.printStackTrace();
        }
        servlerResponse.setContentType(response);
        return servlerResponse.getContentType();

    }


}

