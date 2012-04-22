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
@Path("/medication/")
public class MedicationHandler {

    protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

    private Connector connector;

    @OPTIONS
    @Path("/all")
    public Response returnMedicationHandeler(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return null;

    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnMedication(@Context HttpServletResponse servlerResponse,
                                   @QueryParam("session") String session,
                                   @QueryParam("patientId") String patientId) {

        String response = new DTOWrapper().wrapError("Error while retrieving Medication");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<?> meds = connector.returnMedicationsForPatient(patientId);
            response = new DTOWrapper().wrap(meds);

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("return medication method returned json object: " + response);


        return response;

    }


}