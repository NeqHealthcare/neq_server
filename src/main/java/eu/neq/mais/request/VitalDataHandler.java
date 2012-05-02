package eu.neq.mais.request;

import eu.neq.mais.domain.VitalData;
import eu.neq.mais.domain.gnuhealth.VitalDataGnu;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.storage.DbHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 29.04.12
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */

@Path("/vitaldata")
public class VitalDataHandler {


    protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String getVitalDataForPatient(@QueryParam("session") String sessionString, @QueryParam("patient_id") String patient_id,
                                         @QueryParam("start_Date") String startDate, @QueryParam("end_Date") String endDate) {


        String response;
        //Parse Dates


        //Check for right assignmrnt of values
        //Date startDate_sql = new Date(startDate.getMonth(), startDate.getDay(), startDate.getYear());
        //Date endDate_sql = new Date(endDate.getMonth(), endDate.getDay(), endDate.getYear());

        Date startDate_sql = new Date(5, 12, 2012);
        Date endDate_sql = new Date(6, 20, 2012);

        try {

            DbHandler dbh = new DbHandler();
            List<eu.neq.mais.technicalservice.storage.VitalData> vitalDataEntries = dbh.getVitalData(String
                    .valueOf(patient_id), startDate_sql);


            if (vitalDataEntries.isEmpty()) {
                dbh.close();
                return null;
            }

            List<VitalData> vitalDataListGnu = new ArrayList<VitalData>();


            for (eu.neq.mais.technicalservice.storage.VitalData e : vitalDataEntries) {
                vitalDataListGnu.add(new VitalDataGnu(
                        e.getUser_id(),
                        e.getBmi(),
                        e.getTemprature(),
                        e.getPulse(),
                        e.getFluid_balance(),
                        Long.valueOf(e.getDate())
                ));

            }

            /* List<?> labTests = returnAllLabTestRequests();

            for (eu.neq.mais.technicalservice.storage.LabTestRequest labTestRequest : openRequests) {
                for (Object uncastLabTest : labTests) {
                    LabTestRequestGnu labTestRequestGnu = (LabTestRequestGnu) uncastLabTest;

                    if (valueOf(
                            labTestRequest.getRequest_id().replaceAll(" ", ""))
                            .equals(labTestRequestGnu.getId())) {
                        if (labTestRequestGnu.getState().equals("tested")) {
                            //dbh.removeLabTestRequest(labTestRequest.getRequest_id()); OUTSOURCED TO OWN FUNCTION;
                            recentlyTestedRequestsIds.add(new Integer(labTestRequest
                                    .getRequest_id().replaceAll(" ", "")));
                        }
                    }

                }
            }
            */
            dbh.close();

            response = new DTOWrapper().wrap(vitalDataListGnu);
        } catch (Exception e) {
            e.printStackTrace();

            response = new DTOWrapper().wrapError("Error while retrieving vital data information: " + e.toString());
        }
        return response;
        //return "test";
    }

}
