package eu.neq.mais.request;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.gnuhealth.DocumentGnu;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.SessionStore;
import eu.neq.mais.technicalservice.Settings;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 15.03.12
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
@Path(value = "/document")
public class DocumentHandler {

    protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

    private Connector connector;


    @OPTIONS
    @Path("/image")
    public String returnDocumentImageOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return servlerResponse.getContentType();

    }


    @GET
    @Path("/image")
    @Produces("image/jpeg")
    public byte[] returnDocumentImage(@Context HttpServletResponse servlerResponse,
                                      @QueryParam("session") String session, @QueryParam("id") String id) {

        //String response = new DTOWrapper().wrapError("Error while retrieving Document List");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");
        byte[] response = null;
        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<DocumentGnu> document = connector.returnDocumentData(id);
            response = document.get(0).getImage();
            logger.info(response.toString());

        } catch (Exception e) {
            e.printStackTrace();

        } catch (SessionStore.NoSessionInSessionStoreException e) {
            //response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("return image");

        //servlerResponse.setContentLength(response.length());
        return response;

    }

    @OPTIONS
    @Path("/image/thumbnaul")
    public String returnDocumentThnumbnailOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return servlerResponse.getContentType();

    }

    @GET
    @Path("/image/thumbnail")
    @Produces("image/jpeg")
    public byte[] returnDocumentThumbnail(@Context HttpServletResponse servlerResponse,
                                          @QueryParam("session") String session, @QueryParam("id") String id) {

        //String response = new DTOWrapper().wrapError("Error while retrieving Document List");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");
        byte[] response = null;
        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<DocumentGnu> document = connector.returnDocumentData(id);
            response = document.get(0).getImage();
            logger.info(response.toString());

        } catch (Exception e) {
            e.printStackTrace();

        } catch (SessionStore.NoSessionInSessionStoreException e) {
            //response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("return image");

        //servlerResponse.setContentLength(response.length());
        return response;

    }

    @OPTIONS
    @Path("/list")
    public String returnDocumentOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return servlerResponse.getContentType();

    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnDocument(@Context HttpServletResponse servlerResponse, @QueryParam("session") String session,
                                 @QueryParam("id") String id) {

        String response = new DTOWrapper().wrapError("Error while retrieving Document Data");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<?> document = connector.returnDocumentList(id);
            response = new DTOWrapper().wrap(document);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (SessionStore.NoSessionInSessionStoreException es) {
            response = new DTOWrapper().wrapError(es.toString());
        }
        logger.info("return diagnose method returned json object: " + response);


        return response;


    }
}
