package eu.neq.mais.request;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.Diagnose;
import eu.neq.mais.domain.LabTestResult;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;

@Path("/labtest/")
public class LabTestHandler {
	
	protected static Logger logger = Logger.getLogger("eu.neq.mais.request");
	
	private Connector connector;

	@GET
	@Path("/one/detail")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnLabTestDetails(@Context HttpServletResponse servlerResponse,
			@QueryParam("session") String session, @QueryParam("labTestId") String labTestId) {
		
		String response = new DTOWrapper().wrapError("Error while retrieving detailed info for lab test: "+labTestId);
		
		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*"); 
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With"); 
        servlerResponse.addHeader("Access-Control-Max-Age", "60"); 
        
		try {
			connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
			LabTestResult labTest = connector.returnLabTestResultsDetails(labTestId);
			response = new DTOWrapper().wrap(labTest);
		} catch (Exception e) {
			e.printStackTrace();
			
		} catch (NoSessionInSessionStoreException e) {
			response = new DTOWrapper().wrapError(e.toString());
		}
		logger.info("return diagnose method returned json object: " + response);

		servlerResponse.setContentType(response);
		return servlerResponse.getContentType();
	}
	
	@GET
	@Path("/one")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnLabTestResult(@Context HttpServletResponse servlerResponse,
			@QueryParam("session") String session, @QueryParam("patientId") String patientId) {
		
		String response = new DTOWrapper().wrapError("Error while retrieving lab result for patient:"+patientId);
		
		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*"); 
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With"); 
        servlerResponse.addHeader("Access-Control-Max-Age", "60"); 
        
		try {
			connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
			List<?> labTests = connector.returnLabTestResultsForPatient(patientId);
			response = new DTOWrapper().wrap(labTests);
		} catch (Exception e) {
			e.printStackTrace();
			
		} catch (NoSessionInSessionStoreException e) {
			response = new DTOWrapper().wrapError(e.toString());
		}
		logger.info("return diagnose method returned json object: " + response);

		servlerResponse.setContentType(response);
		return servlerResponse.getContentType();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnLabTestResult(@Context HttpServletResponse servlerResponse,
			@QueryParam("session") String session) {
		
		String response = new DTOWrapper().wrapError("Error while retrieving all lab result");
		
		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*"); 
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With"); 
        servlerResponse.addHeader("Access-Control-Max-Age", "60"); 
        
		try {
			connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
			List<?> labTests = connector.returnAllLabTestResults();
			response = new DTOWrapper().wrap(labTests);
		} catch (Exception e) {
			e.printStackTrace();
			
		} catch (NoSessionInSessionStoreException e) {
			response = new DTOWrapper().wrapError(e.toString());
		}
		logger.info("return diagnose method returned json object: " + response);

		servlerResponse.setContentType(response);
		return servlerResponse.getContentType();
	}
	
	@GET
	@Path("/request")
	@Produces(MediaType.APPLICATION_JSON)
	public String requestLabTest(@Context HttpServletResponse servlerResponse,
			@QueryParam("session") String session, @QueryParam("patientId") String patientId) {
				
		String response = new DTOWrapper().wrapError("Error while retrieving lab test requests");
		
		servlerResponse.addHeader("Allow-Control-Allow-Methods", "POST,GET,OPTIONS"); 
        servlerResponse.addHeader("Access-Control-Allow-Credentials", "true"); 
        servlerResponse.addHeader("Access-Control-Allow-Origin", "*"); 
        servlerResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With"); 
        servlerResponse.addHeader("Access-Control-Max-Age", "60"); 
        
		try {
			connector = ConnectorFactory.getConnector(NeqServer.getSessionStore().getBackendSid(session));
			
			List<?> labTestRequests = connector.returnLabTestRequests(patientId);
			response = new DTOWrapper().wrap(labTestRequests);
		} catch (Exception e) {
			e.printStackTrace();
			
		} catch (NoSessionInSessionStoreException e) {
			response = new DTOWrapper().wrapError(e.toString());
		}
		logger.info("return diagnose method returned json object: " + response);

		servlerResponse.setContentType(response);
		return servlerResponse.getContentType();
	}

}
