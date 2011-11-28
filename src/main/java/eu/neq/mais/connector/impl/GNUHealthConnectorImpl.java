package eu.neq.mais.connector.impl;

//The Client sessions package
import com.google.gson.Gson;
import com.googlecode.jj1.JsonRpcException;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.stringtree.json.ExceptionErrorListener;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONValidatingReader;
import org.stringtree.json.JSONValidatingWriter;
import org.stringtree.json.JSONWriter;



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
			session = session_split[1];
			
			logger.info("prepared session param: "+session);
			
			
			// GET PREFERENCES (DOMAIN)
			// *NEED PREFERENCES TO FULLFIL PARAMETERS*
			 String pref = con.getPreferences(session);
		
			
			 
			 
			 // PREPARE PARAMS
			// ModelStorage.search(cursor, user, domain[, offset[, limit[, order[, context[, count]]]]])
		    Object[] params = new Object[]{1, session, new String[]{}};
		    
		  
		    // VALIDATING
//		    String content = null;
//			int gid = 1;
//			Map<String, Object> values = new HashMap<String, Object>();
//			values.put("method", GnuMethods.GET_PREFERENCES_METHOD);
//			values.put("params", params);
//			values.put("id", "" + gid++);
//			
//			try {
//				JSONWriter writer = new JSONValidatingWriter(new ExceptionErrorListener());
//				content = writer.write(values);
//			} catch (NullPointerException e) {
//				logger.warning("FAIL");
//				throw new JsonRpcException("cannot encode object to json", e);
//			}
//			
//			System.out.print("search json:"+content.toString());
			
			
//			String res = con.exec(GnuMethods.PATIENT_SEARCH_METHOD, params, null);
//			logger.info("res: "+res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
	}
	
	/**
	 * Die Methode nutzt nun nen TCP-Dump json string um search infos zurückzubekommen.
	 * Ich bin mir noch nicht sicher, welche rolle der domain (context) spielt.
	 */
	public String getPreferences(String session) {
//		Object[] params = new Object[]{1, session, 0, 1000};
//		
//		String content = null;
//		int gid = 100;
//		Map<String, Object> values = new HashMap<String, Object>();
//		values.put("method", "s.system.listMethods");
//		values.put("params", params);
//		values.put("id", "" + gid++);
//		
//		try {
//			JSONWriter writer = new JSONValidatingWriter(new ExceptionErrorListener());
//			content = writer.write(values);
//		} catch (NullPointerException e) {
//			logger.warning("FAIL");
//			throw new JsonRpcException("cannot encode object to json", e);
//		}
//		
//		System.out.print(content.toString());
		
		URLConnection connection;
		try {
			connection = new URL(getBackEndUrl().toString()).openConnection();
			connection.setRequestProperty("method", "POST");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			String jsonfile = "{\"params\": [1, \""+session+"\", [], 0, 1000, null, {\"groups\": [1, 3, 4, 2], \"language\": \"en_US\", \"locale\": {\"date\": \"%m/%d/%Y\", \"thousands_sep\": \",\", \"grouping\": [], \"decimal_point\": \".\"}, \"timezone\": null, \"company\": 1, \"language_direction\": \"ltr\"}], \"id\": 52, \"method\": \"model.gnuhealth.patient.search_read\"}";
//			String jsonfile = "{\"params\": [1, \""+session+"\", [], 0, 1000, {\"groups\": [1, 3, 4, 2]], \"id\": 52, \"method\": \"model.gnuhealth.patient.search\"}";
			logger.info("JSON SEARCH REQUEST: "+jsonfile);
			OutputStream out = connection.getOutputStream();
			out.write(jsonfile.getBytes());
			out.close();

			connection.connect();

			InputStream in = connection.getInputStream();
			BufferedReader i = new BufferedReader(new InputStreamReader(in, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = i.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

			in.close();
			
			System.out.print("RESULT: "+sb);
			Map<String, Object> result = null;
			JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
			result = (Map<String, Object>) reader.read(sb.toString());
			
			
			
			
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		return null;
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
		
		
		String content = null;
		int gid = 1;
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("method", GnuMethods.LOGIN_METHOD);
		values.put("params", params);
		values.put("id", "" + gid++);		
		try {
			JSONWriter writer = new JSONValidatingWriter(new ExceptionErrorListener());
			content = writer.write(values);
		} catch (NullPointerException e) {
			throw new JsonRpcException("cannot encode object to json", e);
		}
		
		System.out.println("json: "+content.toString());
		
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
