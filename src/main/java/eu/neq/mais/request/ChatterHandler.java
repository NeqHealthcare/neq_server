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
    public String returnPeople(@Context HttpServletResponse servlerResponse,
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
            List<?> resultList = connector.updateChatterUser(NeqServer.getSessionStore().getUserId(session),user_id,is_followed);
            
            Boolean result = (Boolean) resultList.get(0);
            if (result) {
                response = new DTOWrapper().wrap(result);
            } else {
                response = new DTOWrapper().wrapError("Error: could not save your (un)follow request: ");
            }
                   
            
            

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("update chatter user method returned json object: " + response);


        return response;

    }
    
    @OPTIONS
    @Path("/posts")
    public Response postsOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return null;

    }

    @POST
    @Path("/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public String savePost(@Context HttpServletResponse servlerResponse,
                                   @QueryParam("session") String session,
                                   @QueryParam("message") String message,
                                   @QueryParam("parentId") String parentId ){

        String response = new DTOWrapper().wrapError("Error while saving the post");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
        	

            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<?> resultList = connector.saveChatterPost(NeqServer.getSessionStore().getUserId(session),message,Long.parseLong(parentId));
            Boolean result = (Boolean) resultList.get(0);
            if (result) {
                response = new DTOWrapper().wrap(result);
            } else {
                response = new DTOWrapper().wrapError("Error: could not save post: ");
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("save chatter post method returned json object: " + response);


        return response;

    }
    
    @GET
    @Path("/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnPosts(@Context HttpServletResponse servlerResponse,
                                   @QueryParam("session") String session) {

        String response = new DTOWrapper().wrapError("Error while retrieving posts");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<?> posts = connector.returnChatterPosts(NeqServer.getSessionStore().getUserId(session));
            response = new DTOWrapper().wrap(posts);
           

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("return chatter posts method returned json object: " + response);


        return response;

    }
}
