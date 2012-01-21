package eu.neq.mais.request;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.SessionStore;


/**
 * 
 * @author Jan Gansen
 *
 */
@Path("/patients/")
public class PatientHandler {

	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");

	private Connector connector;

	/**
	 * Example url: http://localhost:8080/patients/one?id=SAMPLE_ID&session=
	 * SESSION_VARIABLE
	 * 
	 * Successfull: {id: 59, result: [{rec_name: Fernandez, Maria, diseases:
	 * [1], age: 41y 10m 3d, sex: f, id: 4, primary_care_doctor: 1}]} ; Request
	 * failed: false
	 */
	@GET
	@Path("/one")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnPatient(@QueryParam("session") String session,
			@QueryParam("id") String id) {

		String patient = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			patient = connector.execute(session,
					connector.getPatientReadMethod(),
					connector.getReturnPatientParams(session, id));

		} catch (Exception e) {
			e.printStackTrace();
			patient = "false";
		}
		logger.info("return patient method returned json object: " + patient);
		return patient;

	}

	/**
	 * Example url: http://localhost:8080/patients/all?session=SESSION_VARIABLE
	 * 
	 * @param session
	 * @return json object
	 * 
	 *         Successfull: {"id": 56, "result": [{"rec_name": "Miller, Frank",
	 *         "diseases": [], "age": "38y 1m 17d", "sex": "m", "id": 1,
	 *         "primary_care_doctor": 1}, {"rec_name": "Lasson, Sophie",
	 *         "diseases": [6], "age": "20y 1m 30d", "sex": "f", "id": 2,
	 *         "primary_care_doctor": 1}, {"rec_name": "Schweizer, Jochen",
	 *         "diseases": [3], "age": "44y 10m 12d", "sex": "m", "id": 11,
	 *         "primary_care_doctor": 1}]} ; 
	 *         Request failed: false
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAllPatients(@QueryParam("session") String session) {

		String patientList = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			patientList = connector.execute(session,
					connector.getPatientReadMethod(),
					connector.getReturnAllPatientsParams(session));

		} catch (Exception e) {
			e.printStackTrace();
			patientList = "false";
		}
		logger.info("return patients method returned json object filled with al patients: " + patientList);
		return patientList;

	}
	
	
	/**
	 * Example url: http://localhost:8080/patients/all?session=SESSION_VARIABLE&primary_care_doctor=PRIMARY_CARE_DOCTOR_ID
	 * 
	 * @param session
	 * @return json object
	 * 
	 *         Successfull: {"id": 56, "result": [{"rec_name": "Miller, Frank",
	 *         "diseases": [], "age": "38y 1m 17d", "sex": "m", "id": 1,
	 *         "primary_care_doctor": 1}, {"rec_name": "Lasson, Sophie",
	 *         "diseases": [6], "age": "20y 1m 30d", "sex": "f", "id": 2,
	 *         "primary_care_doctor": 1}, {"rec_name": "Schweizer, Jochen",
	 *         "diseases": [3], "age": "44y 10m 12d", "sex": "m", "id": 11,
	 *         "primary_care_doctor": 1}]} ; 
	 *         Request failed: false
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAllPatients(@QueryParam("session") String session,@QueryParam("primary_care_doctor") String primaryCareDoctor) {

		String patientList = "false";

		try {
			connector = ConnectorFactory.getConnector(SessionStore
					.getBackendSid(session));
			patientList = connector.execute(session,
					connector.getPatientReadMethod(),
					connector.getReturnAllPatientsParams(session));

		} catch (Exception e) {
			e.printStackTrace();
			patientList = "false";
		}
		logger.info("return patients method returned json object filled with all patients for a specific primary care doctor: " + patientList);
		return patientList;

	}

}
