package eu.neq.mais.request;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.SessionStore;


@Path("/patients/")
public class PatientHandler {
	
	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");
	
	private Connector connector;

	@GET
	@Path("/{id}/{session}")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnPatient(@PathParam("session") String session,@PathParam("id") String id){
		
		String patient = "false";
		
		try {
			connector = ConnectorFactory.getConnector(SessionStore.getBackendSid(session));
			patient = connector.execute(session, connector.getPatientSearchMethod(), connector.getPatientSearchParams(id));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			patient = "false";
		}		
		logger.info("logout method returned json object: "+new Gson().toJson(patient));
		return new Gson().toJson(patient);

	}	
	
	@GET
	@Path("/all/{session}")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAllPatient(@PathParam("session")String session){
		
		String patientList = "false";
		
		try {
			connector = ConnectorFactory.getConnector(SessionStore.getBackendSid(session));
			patientList = connector.execute(session, connector.getPatientSearchMethod(), connector.getPatientSearchParams());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			patientList = "false";
		}		
		logger.info("logout method returned json object: "+new Gson().toJson(patientList));
		return new Gson().toJson(patientList);

	}	

}
			
