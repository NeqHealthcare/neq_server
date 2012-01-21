package eu.neq.mais.connector.gnuhealth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.jj1.ServiceProxy;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.Patient;
import eu.neq.mais.domain.gnuhealth.DiagnoseGnu;
import eu.neq.mais.domain.gnuhealth.PatientGnu;
import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.SessionStore;
import eu.neq.mais.technicalservice.Settings;


/**
 * 
 * 
 * @author Jan Gansen, Sebastian Schütz, Denny Stohr
 *
 */
public class GNUHealthConnectorImpl extends Connector {

	private static Connector instance = null;
	private static int gnid = 55;
		
	
	public static void main(String[] args) {
		try {
			Connector con = ConnectorFactory.getConnector("gnuhealth1");
			
			// LOGIN
			String session = con.login("admin", "iswi223<<");	
			
//			// Search Patients
//		    Object[] params = new Object[]{1, session, new String[]{}, 0, 1000, null, "REPLACE_CONTEXT"}; 
//		    String res = con.execute(session, con.getPatientSearchMethod(), params);
//			logger.info("res: "+res);
//			
//			// Read Patients
//			Object[] params2 = con.getReturnAllPatientsParams(session);	    
//			String res2 = con.execute(session, con.getPatientReadMethod(), params2);
//			logger.info("res2: "+res2);
//
//			
//			// Read Patients
//			Object[] params3 = con.getReturnPatientParams(session,"1");			    
//			String res3 = con.execute(session, con.getPatientReadMethod(), params3);
//			logger.info("res3: "+res3);
			
			// returnAllPatientsForUIList
			String patientListForUI = con.returnAllPatientsForUIList(session);
			System.out.println(patientListForUI.toString());
			
			// Logout
			String res4 = con.logout("admin", session);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
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
	
	
	

	public String execute(String method, Object[] params) {

		logger.info("CALL EXECUTE: "+method);
		
		/**
		 * Get GnuHealthCompatible Json Request file
		 */
		GnuHealthJsonObject dom = new GnuHealthJsonObject(method, params, gnid++);
		
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
			System.out.println(jsonfile); // HERE HERE HERE
			OutputStream out = connection.getOutputStream();
			out.write(jsonfile.getBytes());
			out.close();

			connection.connect();

			InputStream in = connection.getInputStream();
			BufferedReader i = new BufferedReader(new InputStreamReader(in, "ascii")); //ascii seems to be the correct encoding
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


	@Override
	public String returnAllPatientsForUIList(String session) {
		String patientListString = "false";
		patientListString = execute(getPatientReadMethod(), getReturnAllPatientsParams(session));
		List<PatientGnu> patientList = new ArrayList<PatientGnu>();
		
		Type listType = new TypeToken<List<PatientGnu>>(){}.getType();
		patientListString = patientListString.substring(patientListString.indexOf("["), patientListString.lastIndexOf("]")+1);
		patientList = new Gson().fromJson(patientListString, listType);
		
//		Type type = new TypeToken<DiagnoseGnu>(){}.getType();
		for(PatientGnu patient : patientList){
			List<DiagnoseGnu> diagnoseList = new ArrayList<DiagnoseGnu>();
			for(String diseaseID : patient.getDiseases()){
				String diseaseString = execute(getDiagnoseReadMethod(),getReturnDiagnoseParams(session, diseaseID));
				diseaseString = diseaseString.substring(diseaseString.indexOf("[")+1, diseaseString.lastIndexOf("]"));
				diagnoseList.add((DiagnoseGnu) new Gson().fromJson(diseaseString,DiagnoseGnu.class));
			}
			patient.setDiagnoseList(diagnoseList);
		}
		
//		diagnoseList.add(execute(session,getDiagnoseReadMethod(),getReturnDiagnoseParams(session, id));
		return new Gson().toJson(patientList);
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
	public  String getPreferencesMethod(){
		return "model.res.user.get_preferences";
	}
	@Override
	public String getDiagnoseReadMethod() {
		return "model.gnuhealth.patient.disease.read";
	}

	
	
	/*-----  BACKEND METHOD PARAMS  ----*/
	
	@Override
	public  Object[] getReturnAllPatientsParams(String session){
		
		return new Object[]{1, session, getAllPatientIds(session), 
				new String[]{"rec_name","age","diseases","sex","primary_care_doctor"}, 
				"REPLACE_CONTEXT"};
	}
	@Override
	public Object[] getReturnPatientParams(String session,String id){
		return new Object[]{1, session, new int[]{Integer.parseInt(id)}, 
				new String[]{"rec_name","age","diseases","sex","primary_care_doctor"}, 
				"REPLACE_CONTEXT"};
	}
	
	@Override
	public Object[] getReturnDiagnoseParams(String session,String id){
		return new Object[]{1, session, new int[]{Integer.parseInt(id)}, 
				new String[]{"status",
		        "pregnancy_warning",
		        "is_active",
		        "short_comment",
		        "diagnosed_date",
		        "healed_date",
		        "pathology",
		        "disease_severity",
		        "is_infectious",
		        "is_allergy",
		        "pathology.rec_name",}, 
				"REPLACE_CONTEXT"};
	}
	
	
	private int[] getAllPatientIds(String session){
		
		int[] idList;
		
		// LOGIN
		//String session = login("admin", "iswi223<<");	
		
		// Search Patients
	    Object[] params = new Object[]{1, session, new String[]{}, 0, 1000, null, "REPLACE_CONTEXT"};
	    
	    String result = execute(getPatientSearchMethod(), params);
	    //{"id": 55, "result": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]}
	    result  = result.substring(result.indexOf("[")+1,result.lastIndexOf("]"));
	    
	    String[] idListString = result.split(", ");
	    idList = new int[idListString.length];
	    
	    for(int i = 0 ; i<idListString.length; i++){
	    	idList[i] = Integer.parseInt(idListString[i]);
	    }
	    return idList;
	}
	
	
	
	
	public String getUserSearchMethod() {
		return "model.res.user.search";
	}
	
	public int[] getAllUserIds(String session) {
		int[] idList;
		
		// Search Patients
	    Object[] params = new Object[]{1, session, new String[]{}, 0, 1000, null, "REPLACE_CONTEXT"};
	    
	    String result = execute(getUserSearchMethod(), params);
	    result  = result.substring(result.indexOf("[")+1,result.lastIndexOf("]"));
	    
	    String[] idListString = result.split(", ");
	    idList = new int[idListString.length];
	    
	    for(int i = 0 ; i<idListString.length; i++){
	    	idList[i] = Integer.parseInt(idListString[i]);
	    }
	    return idList;
	}
	
	public int getUserID(String username, String session) {
		
		int[] ids = getAllUserIds(session);
		
		Object[] params = new Object[]{7, session, ids, new String[]{"name", "login", "signature"}, "REPLACE_CONTEXT"};		    
		String res = execute("model.res.user.read", params);
		
		
		System.out.println(">> "+res);
		
		return 0;
		
	}


}
