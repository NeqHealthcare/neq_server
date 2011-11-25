package eu.neq.mais.request;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eu.neq.mais.domain.Patient;

// POJO, no interface no extends

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML, JSON and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /patient
@Path("/patient/id/{id: [0-9]*}")
public class PatientServlet {
	
	private ArrayList<Patient> patientList = new ArrayList<Patient>();  
	
	public PatientServlet(){
		patientList.add(new Patient(1,"Mike J.","20.02.1987","male"));
		patientList.add(new Patient(2,"Susanne M.","22.02.1983","female"));
		patientList.add(new Patient(3,"Frank A.","10.02.1977","male"));
		patientList.add(new Patient(4,"Julie B.","20.06.1971","female"));
		patientList.add(new Patient(5,"Mike J.","20.02.1987","male"));
		patientList.add(new Patient(6,"Susanne M.","22.02.1983","female"));
		patientList.add(new Patient(7,"Frank A.","10.02.1977","male"));
		patientList.add(new Patient(8,"Julie B.","20.06.1971","female"));
		patientList.add(new Patient(9,"Mike J.","20.02.1987","male"));
		patientList.add(new Patient(10,"Susanne M.","22.02.1983","female"));
		patientList.add(new Patient(11,"Frank A.","10.02.1977","male"));
		patientList.add(new Patient(12,"Julie B.","20.06.1971","female"));
	}


	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnUserAsHTML(@PathParam("id") Integer id) {
		
		for(Patient patient : patientList){
		
			if(patient.getId().equals(id)){
				return "<h2> patient information retrieved through a get request</h2>"+"<br />"+ patient.toString();
			}
		}
		return "<h2> patient doesn't exist</h2>";
	}

}
			
