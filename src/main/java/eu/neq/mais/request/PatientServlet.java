package eu.neq.mais.request;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eu.neq.mais.domain.gnuhealth.PatientGnu;

// POJO, no interface no extends

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML, JSON and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /patient
@Path("/patient/id/{id: [0-9]*}")
public class PatientServlet {
	

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnUserAsHTML(@PathParam("id") Integer id) {
		
		return "<h2> patient doesn't exist</h2>";
	}

}
			
