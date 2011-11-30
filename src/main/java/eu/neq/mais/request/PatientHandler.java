package eu.neq.mais.request;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.SessionStore;


@Path("/patients/")
public class PatientHandler {
	
	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");
	
	private Connector connector;

	/**
	 * Example url: http://localhost:8080/patients/one?id=SAMPLE_ID&session=SESSION_VARIABLE
	 * 
	 * sample result: "{\"id\": 59, \"result\": [{\"rec_name\": \"Fernandez, Maria\", \"diseases\": [1], \"age\": \"41y 10m 3d\", \"sex\": \"f\", \"id\": 4, \"primary_care_doctor\": 1}]}"
	 * the \ character is used to indicate that a special character is included -> was automatically added during the parsing from string to json
	 */
	@GET
	@Path("/one")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnPatient(@QueryParam("session") String session,@QueryParam("id") String id){
		
		String patient = "false";
		
		try {
			connector = ConnectorFactory.getConnector(SessionStore.getBackendSid(session));
			patient = connector.execute(session, connector.getPatientReadMethod(), connector.getReturnPatientParams(session,id));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			patient = "false";
		}		
		logger.info("logout method returned json object: "+new Gson().toJson(patient));
		return new Gson().toJson(patient);

	}	
	
	/**
	 * Example url: http://localhost:8080/patients/all?session=SESSION_VARIABLE
	 * 
	 * @param session
	 * @return json object
	 * 
	 * example result"{\"id\": 58, \"result\": [{\"rec_name\": \"Miller, Frank\", \"diseases\": [], \"age\": \"38y 0m 8d\", \"sex\": \"m\", \"id\": 1, \"primary_care_doctor\": 1}, {\"rec_name\": \"Lasson, Sophie\", \"diseases\": [6], \"age\": \"20y 0m 21d\", \"sex\": \"f\", \"id\": 2, \"primary_care_doctor\": 1}, {\"rec_name\": \"Schuhmacher, Max\", \"diseases\": [5], \"age\": \"10y 4m 14d\", \"sex\": \"m\", \"id\": 3, \"primary_care_doctor\": 2}, {\"rec_name\": \"Fernandez, Maria\", \"diseases\": [1], \"age\": \"41y 10m 3d\", \"sex\": \"f\", \"id\": 4, \"primary_care_doctor\": 1}, {\"rec_name\": \"Hermann, Silke\", \"diseases\": [], \"age\": \"1y 0m 29d\", \"sex\": \"f\", \"id\": 5, \"primary_care_doctor\": 1}, {\"rec_name\": \"Buchmann, Hildegard\", \"diseases\": [2], \"age\": \"69y 3m 12d\", \"sex\": \"f\", \"id\": 6, \"primary_care_doctor\": 1}, {\"rec_name\": \"Cutler, Samuel\", \"diseases\": [], \"age\": \"44y 4m 26d\", \"sex\": \"m\", \"id\": 7, \"primary_care_doctor\": 1}, {\"rec_name\": \"Li, Ping\", \"diseases\": [4], \"age\": \"31y 9m 25d\", \"sex\": \"f\", \"id\": 8, \"primary_care_doctor\": 1}, {\"rec_name\": \"Moore, Austin\", \"diseases\": [], \"age\": \"15y 10m 14d\", \"sex\": \"m\", \"id\": 9, \"primary_care_doctor\": 1}, {\"rec_name\": \"Bauer, Yannick\", \"diseases\": [], \"age\": \"27y 6m 3d\", \"sex\": null, \"id\": 10, \"primary_care_doctor\": 1}, {\"rec_name\": \"Schweizer, Jochen\", \"diseases\": [3], \"age\": \"44y 9m 3d\", \"sex\": \"m\", \"id\": 11, \"primary_care_doctor\": 1}]}"
	 * the \ character is used to indicate that a special character is included -> was automatically added during the parsing from string to json
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAllPatients(@QueryParam("session")String session){
		
		String patientList = "false";
		
		try {
			connector = ConnectorFactory.getConnector(SessionStore.getBackendSid(session));
			patientList = connector.execute(session, connector.getPatientReadMethod(), connector.getReturnAllPatientsParams(session));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			patientList = "false";
		}		
		logger.info("logout method returned json object: "+new Gson().toJson(patientList));
		return new Gson().toJson(patientList);

	}	

}
			
