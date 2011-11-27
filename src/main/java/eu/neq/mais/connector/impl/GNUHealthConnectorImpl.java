package eu.neq.mais.connector.impl;

//The Client sessions package
import com.google.gson.Gson;
import com.googlecode.jj1.ServiceProxy;
import com.thetransactioncompany.jsonrpc2.client.*;

//The Base package for representing JSON-RPC 2.0 messages
import com.thetransactioncompany.jsonrpc2.*;

import eu.neq.mais.Main;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.gnuhealth.GnuMethods;
import eu.neq.mais.technicalservice.Backend;


//For creating URLs
import java.net.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;



public class GNUHealthConnectorImpl extends Connector {

	private static Connector instance = null;
		
	
	public static void main(String[] args) {
		try {
			Connector con = ConnectorFactory.getConnector("gnu", "1");
			
			// LOGIN
			String session = con.login("admin", "iswi223<<");
			
			
			// PREPARE SESSION PARAM
			char s = '"';
			String session_split[] = session.split(String.valueOf(s));
			session = s+session_split[1]+s;
			
			logger.info("prepared session param: "+session);
			
			
			// GET PREFERENCES
			// *NEED PREFERENCES TO FULLFIL PARAMETERS*
			String preferences = con.getPreferences(session);
			
		
			// PREPARE PARAMS
			// ModelStorage.search(cursor, user, domain[, offset[, limit[, order[, context[, count]]]]])
//		    Object[] params = new Object[]{1, session, new String[]{}, 0, 1000, False, };
			
			
//			String res = con.exec(GnuMethods.PATIENT_SEARCH_METHOD, params, null);
//			logger.info("res: "+res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
	}
	
	public String getPreferences(String session) {
		ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
		
		Object[] params = new Object[]{1, session, true, new String[]{}};
		
		String x = "{\"params\": [1, "+session+", [], 0, 1000, null, {\"groups\": [1, 3, 4, 2], \"language\": \"en_US\", \"locale\": {\"date\": \"%m/%d/%Y\", \"thousands_sep\": \",\", \"grouping\": [], \"decimal_point\": \".\"}, \"timezone\": null, \"company\": 1, \"language_direction\": \"ltr\"}], \"id\": 52, \"method\": \"model.gnuhealth.patient.search\"}";
		String result = new Gson().toJson(proxy.execute(x));
		
		//String result = new Gson().toJson(proxy.call(GnuMethods.GET_PREFERENCES_METHOD, params));
		logger.info("PREFERENCES: "+result);
		return result;
	}
	
	
	public static Connector getInstance(){
		
		if(instance == null){
			instance = new GNUHealthConnectorImpl();
		}
		return instance;
	}



	public String logout(String username, String session) {		
		logger.info("Recieved logout request from: "+username+" (Session: "+session+")");
		ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
		String[] params =  new String[]{username,session};
		String result = new Gson().toJson(proxy.call(GnuMethods.LOGOUT_METHOD, params));
		logger.info("Logout result: "+result);
		return result;
	}
	
	
	private URL getBackEndUrl() {
		try {
			return new URL("http://"+this.getBackend().getUrl()+":"+this.getBackend().getJsonport()+"/"+this.getBackend().getDb());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
			return null;
		
	}


	public String login(String username, String password) {
		logger.info("login - connect to: "+getBackEndUrl().toString() + "with: "+username+":"+password);

		String[] params =  new String[]{username,password};
		ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
		String result = new Gson().toJson(proxy.call(GnuMethods.LOGIN_METHOD, params));
		
		logger.info("result: "+result);
		
		return result;		
	}
	
	
	

	public String exec(String method, Object[] params, String id) {
					
		logger.info("EXECUTE REQUEST: "+method);
		
		ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
		
		String result = new Gson().toJson(proxy.call(GnuMethods.PATIENT_SEARCH_METHOD, params));
		
		logger.info("EXECUTE RESULT: "+result);
		return result; 
	}

}
