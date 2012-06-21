package eu.neq.mais.request;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.server.Response;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.Settings;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;

/**
 * @author Jan Gansen
 */
@Path("/chatter/")
public class ChatterHandler {
	
	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

    private Connector connector;

    @OPTIONS
    @Path("/people")
    public Response returnPeopleOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return null;

    }

    @GET
    @Path("/people")
    @Produces(MediaType.APPLICATION_JSON)
    public String returPeople(@Context HttpServletResponse servlerResponse,
                                   @QueryParam("session") String session) {

        String response = new DTOWrapper().wrapError("Error while retrieving people");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<?> people = connector.returnChatterUsers(NeqServer.getSessionStore().getUserId(session));
            response = new DTOWrapper().wrap(people);

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("return chatter people method returned json object: " + response);


        return response;

    }

    @POST
    @Path("/people")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUser(@Context HttpServletResponse servlerResponse,
                                   @QueryParam("session") String session,
                                   @QueryParam("userId") String userId,
                                   @QueryParam("isFollowed") String isFollowed) {

        String response = new DTOWrapper().wrapError("Error while retrieving people");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
        	
        	boolean is_followed = Boolean.parseBoolean(isFollowed);
        	Integer user_id = Integer.parseInt(userId);
        	
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<Boolean> result = (List<Boolean>) connector.updateChatterUser(NeqServer.getSessionStore().getUserId(session),user_id,is_followed);
            response = new DTOWrapper().wrap(result);
            

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("update chatter user method returned json object: " + response);


        return response;

    }
    
}
