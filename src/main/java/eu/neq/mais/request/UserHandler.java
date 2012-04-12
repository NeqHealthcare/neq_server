package eu.neq.mais.request;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.User;
import eu.neq.mais.domain.gnuhealth.UserGnu;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;
import eu.neq.mais.technicalservice.Settings;
import eu.neq.mais.technicalservice.storage.DbHandler;
import eu.neq.mais.technicalservice.storage.Login;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
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
    public String returnPersnoalDataOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return servlerResponse.getContentType();

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
            
            List<?> nrOfpatients = connector.returnPersonalPatientsForUIList(session);
            person.setNumber_of_patients(String.valueOf(nrOfpatients.size()));
            
            response = new DTOWrapper().wrap(person);
        } catch (Exception e) {
            e.printStackTrace();
            response = new DTOWrapper().wrapError("Error while retrieving your personal information: "+e.toString());
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError("Error while retrieving your personal information: "+e.toString());
        }
        
        return response;
    }


    @OPTIONS
    @Path("//image/")
    public String returnImageOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return servlerResponse.getContentType();

    }

    @GET
    @Path("/image/")
    @Produces("image/*")
    public Response returnImage(@Context HttpServletResponse servlerResponse, @QueryParam("session") String session, @QueryParam("size") String size) {
        String imagePath = "false";
        File image = new File(imagePath);

        String response = "";

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        if (!image.exists()) {
            throw new WebApplicationException(404);
        }

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
//			imagePath = connector.getImage(session,size);
        } catch (Exception e) {
            e.printStackTrace();
            imagePath = "false";
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        String mt = new MimetypesFileTypeMap().getContentType(image);

        logger.info("return image for a specific user: ");
        return Response.ok(image, mt).build();
//		servlerResponse.setContentType(session);
//		return servlerResponse.getContentType();
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
            String userId = String.valueOf(NeqServer.getSessionStore().getUserId(session));
            DbHandler dbH = new DbHandler();
            Login l = dbH.getLatestLogin(userId);
            dbH.close();
            response = new DTOWrapper().wrap(l.getDateOfLogin());
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        servlerResponse.setContentType(response);
        return servlerResponse.getContentType();

    }
    

}

