package eu.neq.mais.request;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.Diagnose;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;
import eu.neq.mais.technicalservice.Settings;
import org.eclipse.jetty.server.Response;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Denny, Jan
 */
@Path("/diagnose/")
public class DiagnoseHandler {

    protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

    private Connector connector;


    @OPTIONS
    @Path("/one")
    public Response optionsOne(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return null;

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
    public Response optionsAll(@Context HttpServletResponse servlerResponse) {

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
    public String returnDashboardData(@Context HttpServletResponse servlerResponse, @QueryParam("session") String session,
                                      @QueryParam("id") String patientId) {

        String response = new DTOWrapper().wrapError("Error while retrieving DashboardData");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<?> dashboard = connector.returnDiagnosesForPatient(session, patientId);
            response = new DTOWrapper().wrap(dashboard);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException es) {
            response = new DTOWrapper().wrapError(es.toString());
        }
        logger.info("return diagnose method returned json object: " + response);


        return response;


    }


    @OPTIONS
    @Path("/procedures")
    public Response proceduresOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return null;

    }

    @GET
    @Path("/procedures")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnProcedures(@Context HttpServletResponse servlerResponse, @QueryParam("session") String session) {

        String response = new DTOWrapper().wrapError("Error while retrieving procedures");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<?> resultList = connector.returnProcedures();
            response = new DTOWrapper().wrap(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException es) {
            response = new DTOWrapper().wrapError(es.toString());
        }
        logger.info("return procedures method returned json object: " + response);


        return response;


    }


    @OPTIONS
    @Path("/diseases")
    public Response diseasesOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return null;

    }

    @GET
    @Path("/diseases")
    @Produces(MediaType.APPLICATION_JSON)
    public String returnDiseases(@Context HttpServletResponse servlerResponse, @QueryParam("session") String session) {

        String response = new DTOWrapper().wrapError("Error while retrieving procedures");

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {
            connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
            List<?> resultList = connector.returnDiseases();
            response = new DTOWrapper().wrap(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSessionInSessionStoreException es) {
            response = new DTOWrapper().wrapError(es.toString());
        }
        logger.info("return diseases method returned json object: " + response);


        return response;


    }

    @OPTIONS
    @Path("/create")
    public Response createDiagnoseOptions(@Context HttpServletResponse servlerResponse) {

        servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", Settings.ALLOW_ORIGIN_ADRESS);
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");


        return null;
    }


    // http://localhost:8080/diagnose/create?session=SESSION&status=STATUS&is_allergy...

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public String createDiagnose(
            @Context HttpServletResponse servlerResponse,
            @QueryParam("session") String session,
            @QueryParam("status") String status,
            @QueryParam("is_allergy") String is_allergy, //b
            @QueryParam("doctor") String doctor, //i
            @QueryParam("pregnancy_warning") String pregnancy_warning,//b
            @QueryParam("age") String age, //i
            @QueryParam("weeks_of_pregnancy") String weeks_of_pregnancy, //i
            @QueryParam("date_start_treatment") String date_start_treatment,
            @QueryParam("short_comment") String short_comment,
            @QueryParam("is_on_treatment") String is_on_treatment,//b
            @QueryParam("is_active") String is_active,//b
            @QueryParam("diagnosed_date") String diagnosed_date,
            @QueryParam("treatment_description") String treatment_description,
            @QueryParam("healed_date") String healed_date,
            @QueryParam("date_stop_treatment") String date_stop_treatment,
            @QueryParam("pcs_code") String pcs_code, //i
            @QueryParam("pathology") String pathology, //i
            @QueryParam("allergy_type") String allergy_type,
            @QueryParam("disease_severity") String disease_severity,
            @QueryParam("is_infectious") String is_infectious,//b
            @QueryParam("extra_info") String extra_info,
            @QueryParam("patient_id") String patient_id) //i 
    {

        String response = new DTOWrapper()
                .wrapError("Error while creating diagnose");

        servlerResponse.addHeader("Allow-Control-Allow-Methods",
                "POST,GET,PUT,OPTIONS");
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*");
        servlerResponse.addHeader("Access-Control-Allow-Headers",
                "Content-Type,X-Requested-With");
        servlerResponse.addHeader("Access-Control-Max-Age", "60");

        try {

            connector = ConnectorFactory.getConnector(NeqServer
                    .getSessionStore().getBackendSid(session));

            Map<Object, Object> params = new HashMap<Object, Object>();
            
            
            
            
            System.out.println("status "+status);
            System.out.println("is_allergy "+is_allergy);
            System.out.println("doctor "+doctor);
            System.out.println("pregnancy_warning "+pregnancy_warning);
            System.out.println("age "+age);
            System.out.println("weeks_of_pregnancy "+weeks_of_pregnancy);
            System.out.println("date_start_treatment "+date_start_treatment);
            System.out.println("short_comment "+short_comment);
            System.out.println("is_on_treatment "+is_on_treatment);
            System.out.println("is_active "+is_active);
            System.out.println("diagnosed_date "+diagnosed_date);
            System.out.println("treatment_description "+treatment_description);
            System.out.println("healed_date "+healed_date);
            System.out.println("date_stop_treatment "+date_stop_treatment);
            System.out.println("pcs_code "+pcs_code);
            System.out.println("pathology "+pathology);
            System.out.println("allergy_type "+allergy_type);
            System.out.println("disease_severity "+disease_severity);
            System.out.println("is_infectious "+is_infectious);
            System.out.println("extra_info "+extra_info);
            System.out.println("patient_id "+patient_id);
           
            

            if(status == "null"){
            	params.put("status",false);
            }else{
            	params.put("status", status); //e.g. c
            }
            params.put("is_allergy", Boolean.parseBoolean(is_allergy)); //e.g. true
            params.put("doctor", Integer.parseInt(doctor)); //e.g. 1
            params.put("pregnancy_warning", Boolean.parseBoolean(pregnancy_warning)); //e.g. true
            params.put("age",  Integer.parseInt(age)); //e.g. 15
            params.put("weeks_of_pregnancy",  Integer.parseInt(weeks_of_pregnancy)); //e.g. 10
            if(date_start_treatment.equals("false")){
            	params.put("date_start_treatment",false);
            }else{
            	params.put("date_start_treatment", date_start_treatment);
            } //e.g. 489534758098
            if(short_comment.equals("false")){
            	params.put("short_comment",false);
            }else{
            	params.put("short_comment", short_comment); //e.g. text
            }
            params.put("is_on_treatment", Boolean.parseBoolean(is_on_treatment)); //e.g. true
            params.put("is_active", Boolean.parseBoolean(is_active)); //e.g. true
            
            if(diagnosed_date.equals("false")){
            	params.put("diagnosed_date",false);
            }else{
            	params.put("diagnosed_date", diagnosed_date);
            } //e.g. 489534758098
            if(treatment_description.equals("false")){
            	params.put("treatment_description",false);
            }else{
            	params.put("treatment_description", treatment_description); //e.g. text
            }
            if(healed_date.equals("false")){
            	params.put("healed_date",false);
            }else{
            	params.put("healed_date", healed_date);
            } //e.g. 489534758098
            
            if(date_stop_treatment.equals("false")){
            	params.put("date_stop_treatment",false);
            }else{
            	params.put("date_stop_treatment", date_stop_treatment);
            }//e.g. 489534758098
            if(pcs_code.equals("false")){
            	params.put("pcs_code", false);
            }else{
            	params.put("pcs_code",  Integer.parseInt(pcs_code)); //e.g. 5
            }
            if(pathology.equals("false")|| pathology.equals("null")){
            	params.put("pathology", false);
            }else{
            	params.put("pathology",  Integer.parseInt(pathology));
            } //e.g. 11
            params.put("allergy_type", allergy_type); //e.g. fa
            params.put("disease_severity", disease_severity); //e.g. 3_sv

            params.put("is_infectious", Boolean.parseBoolean(is_infectious));// e.g. true
            if(extra_info.equals("false")){
            	params.put("extra_info", false);
            }else{
            	params.put("extra_info", extra_info); // e.g. extra info text
            }
            params.put("patient_id", Integer.parseInt(patient_id)); // e.g. 22
            
            List<?> returnMessage = connector
                    .createDiagnose(params);

            response = new DTOWrapper().wrap(returnMessage);
        } catch (Exception e) {
            e.printStackTrace();

        } catch (NoSessionInSessionStoreException e) {
            response = new DTOWrapper().wrapError(e.toString());
        }
        logger.info("return message of create diagnose method: " + response);
        return response;

    }

}
