package eu.neq.mais.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;


// POJO, no interface no extends

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML, JSON and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /patient
@Path("/{backendUri: [a-zA-Z][a-zA-Z_0-9]}/{backendSid: [a-zA-Z][a-zA-Z_0-9]}/patient/id/{id: [0-9]*}")
public class PatientServlet {
	
	private Connector connector;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@PathParam("backendUri") String backendUri,@PathParam("backendSid") String backendSid){
		
		try {
			connector = ConnectorFactory.getConnector(backendUri, backendSid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String session = connector.login("admin", "iswi223>>");
		
		return session;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnPatient(@PathParam("backendUri") String backendUri,@PathParam("backendSid") String backendSid,@PathParam("id") Integer id){
		
		try {
			connector = ConnectorFactory.getConnector(backendUri, backendSid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] params = new String[id];

		String patient = connector.exec("common.login.bla",params,"100");
		
		return patient;
	}

}
			
