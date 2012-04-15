package eu.neq.mais.request;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.Settings;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;

/**
 * @author Jan Gansen
 */
@Path("/appointment/")
public class AppointmentHandler {

	 protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

    private Connector connector;
	    
    @OPTIONS
    @Path("/latest")
    public String returnMedicationHandeler(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return servlerResponse.getContentType();

    }

    @GET
    @Path("/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnMedication(@Context HttpServletResponse servlerResponse,
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
            List<?> appointments = connector.returnAppointments(Integer.parseInt(count),NeqServer.getSessionStore().getUserId(session));
            response = new DTOWrapper().wrap(appointments);

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("return medication method returned json object: " + response);


        return response;

    }
}
