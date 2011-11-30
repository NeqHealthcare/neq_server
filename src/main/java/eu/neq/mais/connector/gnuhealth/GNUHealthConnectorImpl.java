package eu.neq.mais.connector.gnuhealth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;
import com.googlecode.jj1.ServiceProxy;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.technicalservice.SessionStore;



public class GNUHealthConnectorImpl extends Connector {

	private static Connector instance = null;
	private static int gnid = 55;
		
	
	public static void main(String[] args) {
		try {
			Connector con = ConnectorFactory.getConnector("gnuhealth1");
			
			// LOGIN
			String session = con.login("admin", "iswi223<<");	
			
			// Search Patients
		    Object[] params = new Object[]{1, session, new String[]{}, 0, 1000, null, "REPLACE_CONTEXT"};
		    
		    String res = con.execute(session, con.getPatientSearchMethod(), params);
			logger.info("res: "+res);
			
			// Read Patients
			Object[] params2 = con.getReturnAllPatientsParams(session);
			    
			String res2 = con.execute(session, con.getPatientReadMethod(), params2);
			logger.info("res2: "+res2);

			
			// Read Patients
			Object[] params3 = con.getReturnPatientParams(session,"1");
			    
			String res3 = con.execute(session, con.getPatientReadMethod(), params3);
			logger.info("res3: "+res3);
			// Logout
			String res4 = con.logout("admin", session);
						
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
		String result = new Gson().toJson(proxy.call(this.getLogoutMethod(), params));
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


	/**
	 *Login successful: session
	 * Login unsuccessful: false as string
	 */
	public String login(String username, String password) {
		logger.info("login - connect to: "+getBackEndUrl().toString() + "with: "+username+":"+password);

		String[] params =  new String[]{username,password};
		
		ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
		String result = new Gson().toJson(proxy.call(this.getLoginMethod(), params));
		
		logger.info("result: "+result);	
		
		//checks if login was successfull
		if((result.length()>5)){
			char s = '"';
			String session_split[] = result.split(String.valueOf(s));
			result = session_split[1];
		}else{
			result = "false";
		}
		logger.info("retrieved session: "+result);	
		return result;		
	}
	
	
	

	public String execute(String session, String method, Object[] params) {

		logger.info("CALL EXECUTE: "+method);
		
		/**
		 * Get GnuHealthCompatible Json Request file
		 */
		GnuHealthJsonObject dom = new GnuHealthJsonObject(session, method, params, gnid++);
		
		/**
		 * Send json file to GNUHealth and recieve response
		 */
		URLConnection connection;
		String result = null;
		
		try {
			connection = new URL(getBackEndUrl().toString()).openConnection();
			connection.setRequestProperty("method", "POST");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			
//			String jsonfile = "{\"params\": [1, \""+session+"\", [], 0, 1000, null, {\"groups\": [1, 3, 4, 2], \"language\": \"en_US\", \"locale\": {\"date\": \"%m/%d/%Y\", \"thousands_sep\": \",\", \"grouping\": [], \"decimal_point\": \".\"}, \"timezone\": null, \"company\": 1, \"language_direction\": \"ltr\"}], \"id\": 52, \"method\": \"model.gnuhealth.patient.search\"}";
			String jsonfile = dom.getJson();
			
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
			
			result = sb.toString();
			//JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
			//result = (Map<String, Object>) reader.read(sb.toString());		
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		
		
		return result; 
	}

	
	
	
	
	
	/*-----  BACKEND METHODS  ----*/
	@Override
	public String getLoginMethod(){
		return "common.db.login";
	}
	@Override
	public String getLogoutMethod(){
		return "common.db.logout";
	}
	@Override
	public String getPatientSearchMethod(){
		return "model.gnuhealth.patient.search";
	}
	@Override
	public String getPatientReadMethod(){
		return "model.gnuhealth.patient.read";
	}
	@Override
	public  String getReferencesMethod(){
		return "model.res.user.get_preferences";
	}
	
	
	/*-----  BACKEND METHOD PARAMS  ----*/
	
	@Override
	public  Object[] getReturnAllPatientsParams(String session){
		
		return new Object[]{1, session, getAllPatientIds(), 
				new String[]{"rec_name","age","diseases","sex","primary_care_doctor"}, 
				"REPLACE_CONTEXT"};
	}
	@Override
	public Object[] getReturnPatientParams(String session,String id){
		return new Object[]{1, session, new int[]{Integer.parseInt(id)}, 
				new String[]{"rec_name","age","diseases","sex","primary_care_doctor"}, 
				"REPLACE_CONTEXT"};
	}
	
	private int[] getAllPatientIds(){
		
		int[] idList;
		
		// LOGIN
		String session = login("admin", "iswi223<<");	
		
		// Search Patients
	    Object[] params = new Object[]{1, session, new String[]{}, 0, 1000, null, "REPLACE_CONTEXT"};
	    
	    String result = execute(session, getPatientSearchMethod(), params);
	    //{"id": 55, "result": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]}
	    result  = result.substring(result.indexOf("[")+1,result.lastIndexOf("]"));
	    
	    String[] idListString = result.split(", ");
	    idList = new int[idListString.length];
	    
	    for(int i = 0 ; i<idListString.length; i++){
	    	idList[i] = Integer.parseInt(idListString[i]);
	    }
	    return idList;
	}
}
