package eu.neq.mais.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.neq.mais.NeqServer;

public class ConnectionHandlerTest {

	private String URL = "http://localhost:8080/connection/login";
	private String loginRequestParameters = "username=admin&password=iswi223<<&backendSid=gnuhealth1";
	private String fakeLoginRequestParameters ="username=admn&password=iswi3<<&backendSid=gnuhealth1";
	private String logoutURL ="http://localhost:8080/connection/logout";
	private String logoutRequestParameters = "username=admin&session=";
	private NeqServer server;
	
	@Before
	public void setUp() throws Exception {
		server = NeqServer.getInstance();
		new Thread(server). start ();
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}

	@Test
	public void testLoginAndLogout() {

		//fake login
		String retrievedSesson = HTTPRequestPoster.sendGetRequest(URL, fakeLoginRequestParameters);
		System.out.println("fake login response: "+retrievedSesson);
		assertTrue(retrievedSesson.equals("false"));
		
		//real login
		retrievedSesson = HTTPRequestPoster.sendGetRequest(URL, loginRequestParameters);
		System.out.println("real login response: "+retrievedSesson);
		assertTrue(!retrievedSesson.equals("false"));
		

		//fake logout
		String logoutResponse = HTTPRequestPoster.sendGetRequest(logoutURL, logoutRequestParameters+"sldköfje9");
		System.out.println("fake logout response: "+logoutResponse);
		assertTrue(logoutResponse.equals("false"));
		
		//real logout
		logoutResponse = HTTPRequestPoster.sendGetRequest(logoutURL, logoutRequestParameters+retrievedSesson);
		System.out.println("real logout response: "+logoutResponse);
		assertTrue(logoutResponse.equals("true"));
		
	}

}
