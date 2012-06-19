package eu.neq.mais.request;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;
import eu.neq.mais.technicalservice.Settings;

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
@Path("/patient")
public class PatientHandler {

    protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

    private Connector connector;


    @OPTIONS
    @Path("/")
    public Response optionsBla(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return null;

    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnPatient(@Context HttpServletResponse servletResponse, @QueryParam("session") String session,
                                @QueryParam("query") String query,
                                @QueryParam("ownonly") boolean ownonly
    ) {

        String response = new DTOWrapper().wrapError("Error while retrieving patient");

        servletResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servletResponse.addHeader("Access-Control-Allow-Origin", "*");
        servletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servletResponse.addHeader("Access-Control-Max-Age", "60");

        try {

            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));

            if
            (ownonly == true) {
                List<?> patientList = connector.returnPersonalPatientsForUIList(session);
                response = new DTOWrapper().wrap(patientList);
                logger.info("return patients method returned json object filled with all patients for a specific primary care doctor: " + response);
            } else if (query != null) {
                List<?> patientList = connector.searchForAPatient(query);
                response = new DTOWrapper().wrap(patientList);
                logger.info("return patient method returned json object: " + response);
            } 
            
            if(ownonly == false && query != null){
                List<?> patientList = connector.returnAllPatientsForUIList();
                response = new DTOWrapper().wrap(patientList);
                logger.info("return patients method returned json object filled with all patients: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException es) {
            response = new DTOWrapper().wrapError(es.toString());
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

		String path = System.getProperty("user.dir")+"/resources/pimages/";
		File img = new File(path + id);

		if (!img.exists()) {
			img = new File(path+"default_user.png");
		}

		BufferedImage image;
		try {
			image = ImageIO.read(img);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			if (height != null && width != null) {
				image = getScaledImage(image, Integer.parseInt(width), Integer.parseInt(height));
			}
			
			ImageIO.write(image, "png", baos);

			return Response.ok(baos.toByteArray()).build();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}
	
	public BufferedImage getScaledImage(BufferedImage originalImage, int IMG_MAX_WIDTH, int IMG_MAX_HEIGHT) {
		try {

			int IMG_WIDTH = IMG_MAX_WIDTH, IMG_HEIGHT = IMG_MAX_HEIGHT;

			if (originalImage.getWidth() > IMG_MAX_WIDTH
					|| originalImage.getHeight() > IMG_MAX_HEIGHT) {
				if (originalImage.getWidth() >= originalImage.getHeight()) {
					float factor = (float) IMG_MAX_WIDTH
							/ originalImage.getWidth();
					IMG_WIDTH = IMG_MAX_WIDTH;
					IMG_HEIGHT = Math.round(originalImage.getHeight() * factor);
				} else {
					float factor = (float) IMG_MAX_HEIGHT
							/ originalImage.getHeight();
					IMG_WIDTH = Math.round(originalImage.getWidth() * factor);
					IMG_HEIGHT = IMG_MAX_HEIGHT;
				}
			} else {
				IMG_WIDTH = originalImage.getWidth();
				IMG_HEIGHT = originalImage.getHeight();
			}
			BufferedImage resizedImage = new BufferedImage(IMG_WIDTH,
					IMG_HEIGHT, originalImage.getType());
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
			g.dispose();

			return resizedImage;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


}
