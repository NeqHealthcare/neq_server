package eu.neq.mais.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.SessionStore;


@Path("/patient/")
public class PatientHandler {
	
	private Connector connector;

	@GET
	@Path("/{id: [0-9]*}/{session}")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnPatient(@PathParam("session") String session,@PathParam("id") Integer id){
		
		try {
			connector = ConnectorFactory.getConnector(SessionStore.getBackendSid(session));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] params = new String[id];

		String patient = connector.execute("lala", "common.login.bla",params);
		
		return patient;
	}	

}
			
