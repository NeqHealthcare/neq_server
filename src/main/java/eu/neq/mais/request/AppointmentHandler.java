package eu.neq.mais.request;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;
import eu.neq.mais.technicalservice.Settings;
import org.eclipse.jetty.server.Response;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Jan Gansen
 */
@Path("/appointment/")
public class AppointmentHandler {

    protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

    private Connector connector;

    @OPTIONS
    @Path("/latest")
    public Response returnAppointmentsOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return null;

    }

    @GET
    @Path("/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnAppointments(@Context HttpServletResponse servlerResponse,
                                   @QueryParam("session") String session,
                                   @QueryParam("count") String count) {

        String response = new DTOWrapper().wrapError("Error while retrieving appointments");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<?> appointments = connector.returnAppointments(Integer.parseInt(count), NeqServer.getSessionStore().getUserId(session));
            response = new DTOWrapper().wrap(appointments);

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("return appointment method returned json object: " + response);


        return response;

    }
}
