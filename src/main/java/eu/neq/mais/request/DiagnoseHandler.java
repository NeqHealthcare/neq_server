package eu.neq.mais.request;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.Diagnose;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;
import eu.neq.mais.technicalservice.Settings;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Denny, Jan
 */
@Path("/diagnose/")
public class DiagnoseHandler {

    protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

    private Connector connector;

    // Example request:
    // http://localhost:8080/diagnosis?id=1&session=SESSION

    @OPTIONS
    @Path("/one")
    public String optionsOne(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return servlerResponse.getContentType();

    }

    @GET
    @Path("/one")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnDiagnose(@Context HttpServletResponse servlerResponse,
                                 @QueryParam("session") String session, @QueryParam("id") String id) {

        String response = new DTOWrapper().wrapError("Error while retrieving Diagnose");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            Diagnose diagnose = connector.returnDiagnose(id);
            response = new DTOWrapper().wrap(diagnose);
        } catch (Exception e) {
            e.printStackTrace();

        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("return diagnose method returned json object: " + response);


        return response;

    }

    @OPTIONS
    @Path("/all")
    public String optionsAll(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return servlerResponse.getContentType();

    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnDashboardData(@Context HttpServletResponse servlerResponse, @QueryParam("session") String session,
                                      @QueryParam("id") String id) {

        String response = new DTOWrapper().wrapError("Error while retrieving DashboardData");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<?> dashboard = connector.returnDashBoardData(session, id);
            response = new DTOWrapper().wrap(dashboard);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException es) {
            response = new DTOWrapper().wrapError(es.toString());
        }
        logger.info("return diagnose method returned json object: " + response);


        return response;


    }

}
