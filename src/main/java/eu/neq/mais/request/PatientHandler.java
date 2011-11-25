package eu.neq.mais.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;


@Path("/{backendUri}/{backendSid}/patient/")
public class PatientHandler {
	
	private Connector connector;

	@GET
	@Path("/{id: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnPatient(@PathParam("backendUri") String backendUri,@PathParam("backendSid") String backendSid,@PathParam("id") Integer id){
		
		try {
			connector = ConnectorFactory.getConnector(backendUri, backendSid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] params = new String[id];

		String patient = connector.exec("common.login.bla",params,"100");
		
		return patient;
	}
	
	@GET
	@Path("/muh")
	@Produces(MediaType.APPLICATION_JSON)
	public String muh(){
		return "muh";
	}
	

}
			
