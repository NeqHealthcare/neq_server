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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.jj1.ServiceProxy;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.Patient;
import eu.neq.mais.domain.gnuhealth.DiagnoseGnu;
import eu.neq.mais.domain.gnuhealth.PatientGnu;
import eu.neq.mais.domain.gnuhealth.PhysicianGnu;
import eu.neq.mais.domain.gnuhealth.UserGnu;
import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.SessionStore;
import eu.neq.mais.technicalservice.Settings;

/**
 * Connector implementation for a GNU Health Hospital Information System. All
 * methods provided by this class deal with accessing information of the
 * GNUHealth system only. The underlying technology is JSON-RPC.
 * 
 * @author Jan Gansen, Sebastian Schütz, Denny Stohr
 * 
 */
public class GNUHealthConnectorImpl extends Connector {

	private static int gnid = 55;
	private static String adminSession = null;

	/**
	 * Main method is for a test run of several functions of the connector and
	 * should not be used! For testing purposes only.
	 * 
	 * @param args
	 *            - no influence
	 */
	public static void main(String[] args) {

		try {
			Connector con = ConnectorFactory.getConnector("gnuhealth1");

			String login_name = "jgansen";
			String password = "iswi223<<";

			// LOGIN
			String user_session = con.login(login_name, password, "gnuhealth1");

			// // Search Patients
			// Object[] params = new Object[]{1, session, new String[]{}, 0,
			// 1000, null, "REPLACE_CONTEXT"};
			// String res = con.execute(session, con.getPatientSearchMethod(),
			// params);
			// logger.info("res: "+res);
			//
			// // Read Patients
			// Object[] params2 = con.getReturnAllPatientsParams(session);
			// String res2 = con.execute(session, con.getPatientReadMethod(),
			// params2);
			// logger.info("res2: "+res2);
			//
			//
			// // Read Patients
			// Object[] params3 = con.getReturnPatientParams(session,"1");
			// String res3 = con.execute(session, con.getPatientReadMethod(),
			// params3);
			// logger.info("res3: "+res3);

			// Find Patient List for UI
			String patientListForUI = con.returnAllPatientsForUIList();
			System.out.println(patientListForUI.toString());

			// return all ids
			// int idfound = SessionStore.getUserId(user_session);
			// int pid = ((GNUHealthConnectorImpl)con).getPhysicianId(idfound);
			// System.out.println("\n\n");
			// System.out.println("SEARCH FOR USER_ID AND PARTY_ID");
			// System.out.println("----------------------------------------------------");
			// System.out.println("[" + login_name +"] User.id:" + idfound +
			// ", Parties.id (getPhysicianId): "
			// + pid + " (system intern record id = equal to physician id)");
			// System.out.println("\n\n");
			// System.out.println("PERSONAL PATIENTS FOR "+login_name+" ("+user_session+")");
			// System.out.println("----------------------------------------------------");
			// System.out.println(con.returnPersonalPatientsForUIList(user_session));
			// System.out.println("\n\n");

			// return personal information of user:
			// System.out.println("--------PERSONAL INFORMATION OF USER: "+((GNUHealthConnectorImpl)con).returnPersonalInformation(user_session,true,true));

			// searching for a patient
			// String param = "sop";
			// System.out.println(con.searchForAPatient(session, param));

			// Logout
			String res4 = con.logout(login_name, user_session);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @see eu.neq.mais.connector.Connector#logout(java.lang.String,
	 * java.lang.String)
	 */
	public String logout(String username, String session) {
		ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
		String[] params = new String[] { username, session };
		String result = new Gson().toJson(proxy.call(this.getLogoutMethod(),
				params));
		logger.info("! --- logout -> " + username + ": " + result);
		return result;
	}

	/*
	 * Returns the http-style URL for the back-end assigned to this connector.
	 * 
	 * @return URL
	 */
	private URL getBackEndUrl() {
		try {
			return new URL("http://" + this.getBackend().getUrl() + ":"
					+ this.getBackend().getJsonport() + "/"
					+ this.getBackend().getDb());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @see eu.neq.mais.connector.Connector#login(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public String login(String username, String password, String backendSid) {
		String[] params = new String[] { username, password };

		ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
		String result = new Gson().toJson(proxy.call(this.getLoginMethod(),
				params));

		// checks if login was successfull
		if ((result.length() > 5)) {
			char s = '"';
			String session_split[] = result.split(String.valueOf(s));
			result = session_split[1];

			Integer userId = getUserId(username);
			SessionStore.put(result, backendSid, userId);
		} else {
			result = "false";
		}
		logger.info("! --- login -> connect to: " + getBackEndUrl().toString()
				+ "with: " + username + ":" + password + " ----> RESULT: "
				+ result);
		return result;
	}

	/*
	 * Helping method for executing commands in the back-end system using
	 * JSON-RPC
	 * 
	 * @param method
	 *            the method which is supposed to be called
	 * @param params
	 *            it's parameters
	 * @return result of the method invocation
	 */
	private String execute(String method, Object[] params) {

		/*
		 * Get GnuHealthCompatible Json Request file
		 */
		GnuHealthJsonObject dom = new GnuHealthJsonObject(method, params,
				gnid++);

		/*
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

			String jsonfile = dom.getJson();

			OutputStream out = connection.getOutputStream();
			out.write(jsonfile.getBytes());
			out.close();

			connection.connect();

			InputStream in = connection.getInputStream();
			BufferedReader i = new BufferedReader(new InputStreamReader(in,
					"ascii")); // ascii seems to be the correct encoding
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = i.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

			in.close();

			result = sb.toString();
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * 
	 * @see eu.neq.mais.connector.Connector#returnAllPatientsForUIList()
	 */
	@Override
	public String returnAllPatientsForUIList() {
		String session = getAdminSession();

		String patientListString = "false";
		patientListString = execute(getPatientReadMethod(),
				getReturnPatientsParams(session));

		Type listType = new TypeToken<List<PatientGnu>>() {
		}.getType();
		patientListString = patientListString.substring(
				patientListString.indexOf("["),
				patientListString.lastIndexOf("]") + 1);
		patientListString = patientListString.replaceAll(
				"primary_care_doctor.rec_name", "primary_care_doctor_rec_name");
		patientListString = patientListString.replaceAll(
				"primary_care_doctor.name", "primary_care_doctor_name");
		List<PatientGnu> patientList = new Gson().fromJson(patientListString,
				listType);
		patientList = addLatestDiagnoseToPatient(patientList);
		return new Gson().toJson(patientList);
	}

	/**
	 * @see
	 * eu.neq.mais.connector.Connector#returnPersonalPatientsForUIList(java.
	 * lang.String)
	 */
	@Override
	public String returnPersonalPatientsForUIList(String session) {
		String patientListString = "false";

		patientListString = execute(getPatientReadMethod(),
				getReturnPatientsParams(session));

		Type listType = new TypeToken<List<PatientGnu>>() {
		}.getType();
		patientListString = patientListString.substring(
				patientListString.indexOf("["),
				patientListString.lastIndexOf("]") + 1);
		patientListString = patientListString.replaceAll(
				"primary_care_doctor.rec_name", "primary_care_doctor_rec_name");
		patientListString = patientListString.replaceAll(
				"primary_care_doctor.name", "primary_care_doctor_name");
		List<PatientGnu> patientList = new Gson().fromJson(patientListString,
				listType);

		patientList = addLatestDiagnoseToPatient(patientList);

		int party_id = getPhysicianId(SessionStore.getUserId(session));
		ArrayList<PatientGnu> relevantList = new ArrayList<PatientGnu>();
		for (PatientGnu p : patientList) {
			if (Integer.valueOf(p.getPrimary_care_doctor_id()) == party_id)
				relevantList.add(p);
		}
		return new Gson().toJson(relevantList);
	}

	/**
	 * 
	 * @see eu.neq.mais.connector.Connector#searchForAPatient(java.lang.String)
	 */
	@Override
	public String searchForAPatient(String param) {
		return new Gson().toJson(generatePatientListObjectById(param));
	}

	/*
	 * Helping method for gathering all relevant patients with a specific name or id
	 * @param param search term as a name:String or id:Integer
	 * @return list of relevant results
	 */
	private ArrayList<PatientGnu> generatePatientListObjectById(String param) {
		String session = getAdminSession();

		String patientListString = "false";
		patientListString = execute(getPatientReadMethod(),
				getReturnPatientsParams(session));

		Type listType = new TypeToken<List<PatientGnu>>() {
		}.getType();
		patientListString = patientListString.substring(
				patientListString.indexOf("["),
				patientListString.lastIndexOf("]") + 1);
		patientListString = patientListString.replaceAll(
				"primary_care_doctor.rec_name", "primary_care_doctor_rec_name");
		patientListString = patientListString.replaceAll(
				"primary_care_doctor.name", "primary_care_doctor_name");
		List<PatientGnu> patientList = new Gson().fromJson(patientListString,
				listType);

		patientList = addLatestDiagnoseToPatient(patientList);

		ArrayList<PatientGnu> relevantList = new ArrayList<PatientGnu>();

		try {
			// If a search for an int is executed, param must be of type int and
			// therefore parseable by Integer
			int id = Integer.parseInt(param);

			for (PatientGnu p : patientList) {
				if (Integer.valueOf(p.getId()).equals(id))
					relevantList.add(p);
			}

		} catch (NumberFormatException e) {
			// If this block is reached, param is not an int
			// -> a search looking for a name has been executed

			for (PatientGnu p : patientList) {
				if (p.getRec_name().toLowerCase().contains(param.toLowerCase()))
					relevantList.add(p);
			}

		}

		return relevantList;

	}

	/*
	 * Helping method adding the latest diagnosis to patient objects.
	 * @param patientList patients who should be assigned to their latest diagnoses
	 * @return List of patients w/ diagnoses.
	 */
	private List<PatientGnu> addLatestDiagnoseToPatient(
			List<PatientGnu> patientList) {
		for (PatientGnu patient : patientList) {

			DiagnoseGnu latestDiagnose = null;
			if (patient.getDiagnoseIds() != null) {
				for (String diseaseID : patient.getDiagnoseIds()) {
					String diagnoseString = execute(getDiagnoseReadMethod(),
							getReturnDiagnoseParams(diseaseID));
					diagnoseString = diagnoseString.substring(
							diagnoseString.indexOf("[") + 1,
							diagnoseString.lastIndexOf("]"));
					diagnoseString = diagnoseString.replaceAll(
							"pathology.rec_name", "pathology_rec_name");
					diagnoseString = diagnoseString.replaceAll(
							"doctor.rec_name", "doctor_rec_name");
					diagnoseString = diagnoseString.replaceAll(
							"cs_code.rec_name", "cs_code_rec_name");
					Type type = new TypeToken<DiagnoseGnu>() {
					}.getType();

					DiagnoseGnu tempDiagnose = ((DiagnoseGnu) new Gson()
							.fromJson(diagnoseString, type));
					if (latestDiagnose != null) {
						latestDiagnose = latestDiagnose
								.returnLatest(tempDiagnose);

					} else {
						latestDiagnose = tempDiagnose;
					}
				}
			}
			if (latestDiagnose != null) {
				patient.setLatestDiagnoseRecName(latestDiagnose
						.getPathology_rec_name());
			}
		}

		return patientList;
	}

	/**
	 * 
	 * @see eu.neq.mais.connector.Connector#returnDashBoardData(java.lang.String, java.lang.String)
	 */
	@Override
	public String returnDashBoardData(String session, String id) {

		List<PatientGnu> patientList = this.generatePatientListObjectById(id);
		List<DiagnoseGnu> diagnoseList = new ArrayList<DiagnoseGnu>();
		for (PatientGnu patient : patientList) {

			DiagnoseGnu allDiagnosis = null;
			if (patient.getDiagnoseIds() != null) {
				for (String diseaseID : patient.getDiagnoseIds()) {
					String diagnoseString = execute(getDiagnoseReadMethod(),
							getReturnDiagnoseParams(diseaseID));
					diagnoseString = diagnoseString.substring(
							diagnoseString.indexOf("[") + 1,
							diagnoseString.lastIndexOf("]"));
					diagnoseString = diagnoseString.replaceAll(
							"pathology.rec_name", "pathology_rec_name");
					diagnoseString = diagnoseString.replaceAll(
							"doctor.rec_name", "doctor_rec_name");
					diagnoseList.add(((DiagnoseGnu) new Gson().fromJson(
							diagnoseString, DiagnoseGnu.class)));

				}
			}
		}

		if (diagnoseList.isEmpty()) {
			return "false";
		} else {
			return new Gson().toJson(diagnoseList);

		}

	}

	@Override
	/**
	 * @see eu.neq.mais.connector.Connector#returnDiagnose(java.lang.String)
	 */
	public String returnDiagnose(String diagnoseID) {
		String diagnose = this.execute(this.getDiagnoseReadMethod(),
				this.getReturnDiagnoseParams(diagnoseID));
		diagnose = diagnose.substring(diagnose.indexOf("["),
				diagnose.lastIndexOf("]") + 1);
		return diagnose;
	}

	@Override
	/**
	 * @see eu.neq.mais.connector.Connector#returnPersonalInformation(java.lang.String, boolean, boolean)
	 */
	public String returnPersonalInformation(String userSession, boolean name,
			boolean picture) {
		HashMap<String, String> personalInfo = new HashMap<String, String>();
		if (name) {
			personalInfo.put("name",
					getUserRecName(SessionStore.getUserId(userSession)
							.toString()));
		}
		if (picture) {
			personalInfo.put("picture", "http://i43.tinypic.com/29lzamh.png");
		}
		return new Gson().toJson(personalInfo);
	}
	
	/*
	 * Helping method to find all registered patients.
	 * @return Integer array of all patient IDs.
	 */
	private int[] getAllPatientIds() {
		String session = getAdminSession();
		int[] idList;

		// Search Patients
		Object[] params = new Object[] { 1, session, new String[] {}, 0, 1000,
				null, "REPLACE_CONTEXT" };

		String result = execute(getPatientSearchMethod(), params);
		// {"id": 55, "result": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]}
		result = result.substring(result.indexOf("[") + 1,
				result.lastIndexOf("]"));

		String[] idListString = result.split(", ");
		idList = new int[idListString.length];

		for (int i = 0; i < idListString.length; i++) {
			idList[i] = Integer.parseInt(idListString[i]);
		}
		return idList;
	}

	/*
	 * Helping method to get all user IDs
	 * @return Integer array of all user IDs.
	 */
	private int[] getAllUserIds() {
		String session = getAdminSession();
		int[] idList;

		// Search Patients
		Object[] params = new Object[] { 1, session, new String[] {}, 0, 1000,
				null, "REPLACE_CONTEXT" };

		String result = execute(getUserSearchMethod(), params);
		result = result.substring(result.indexOf("[") + 1,
				result.lastIndexOf("]"));

		String[] idListString = result.split(", ");
		idList = new int[idListString.length];

		for (int i = 0; i < idListString.length; i++) {
			idList[i] = Integer.parseInt(idListString[i]);
		}
		return idList;
	}

	/*
	 * Helping method, returning a ID corresponding to a user-name.
	 * @param username
	 * @return ID
	 */
	private int getUserId(String username) {
		String session = getAdminSession();

		// Getting all User Ids
		int[] ids = getAllUserIds();

		// Searching for all Ids and fields: name, login
		Object[] params = new Object[] { 1, session, ids,
				new String[] { "name", "login" }, "REPLACE_CONTEXT" };

		// Execute search
		String res = execute("model.res.user.read", params);

		// cleanse json transmission overhead (transaction id, etc..)
		String cleansed = res.substring(res.indexOf("["), res.indexOf("]") + 1);

		// convert to list
		Type listType = new TypeToken<List<UserGnu>>() {
		}.getType();
		List<UserGnu> userList = new Gson().fromJson(cleansed, listType);

		// SEARCH FOR ID
		for (UserGnu u : userList) {
			if (u.getLogin().equals(username))
				return Integer.valueOf(u.getId());
		}

		return -1;

	}

	/*
	 * Helping method returning the cecord name of a user.
	 * @param id user's ID
	 * @return Record name
	 */
	private String getUserRecName(String id) {
		String session = getAdminSession();
		// Getting all User Ids
		int[] ids = getAllUserIds();

		// Searching for all Ids and fields: name, login
		Object[] params = new Object[] { 1, session, ids,
				new String[] { "name", "login", "rec_name" }, "REPLACE_CONTEXT" };

		// Execute search
		String res = execute("model.res.user.read", params);

		// cleanse json transmission overhead (transaction id, etc..)
		String cleansed = res.substring(res.indexOf("["), res.indexOf("]") + 1);

		// convert to list
		Type listType = new TypeToken<List<UserGnu>>() {
		}.getType();
		List<UserGnu> userList = new Gson().fromJson(cleansed, listType);

		// SEARCH FOR ID
		for (UserGnu u : userList) {
			if (u.getId().equals(id))
				return u.getRec_name();
		}

		return "no name found";

	}

	/*
	 * Helping method returning the ID's of all parties of the GNUHealth back-end.
	 * @return Array of IDs.
	 */
	private int[] getAllPartyIds() {
		String session = getAdminSession();

		int[] idList;

		// Search Patients
		Object[] params = new Object[] { 1, session, new String[] {}, 0, 1000,
				null, "REPLACE_CONTEXT" };

		String result = execute("model.party.party.search", params);
		result = result.substring(result.indexOf("[") + 1,
				result.lastIndexOf("]"));

		String[] idListString = result.split(", ");
		idList = new int[idListString.length];

		for (int i = 0; i < idListString.length; i++) {
			idList[i] = Integer.parseInt(idListString[i]);
		}
		return idList;
	}

	/*
	 * Helping method returning the corresponding physician ID of a user.
	 * @param user_id
	 * @return physician id
	 */
	private int getPhysicianId(int user_id) {
		String session = getAdminSession();
		int[] allphys = getAllPartyIds();

		Object[] params = new Object[] { 1, session, allphys,
				new String[] { "id", "internal_user" }, "REPLACE_CONTEXT" };

		// Execute search
		String res = execute("model.party.party.read", params);

		// cleanse json transmission overhead (transaction id, etc..)
		String cleansed = res.substring(res.indexOf("["), res.indexOf("]") + 1);

		// convert to list
		Type listType = new TypeToken<List<PhysicianGnu>>() {
		}.getType();
		List<PhysicianGnu> userList = new Gson().fromJson(cleansed, listType);

		// SEARCH FOR ID
		for (PhysicianGnu u : userList) {
			if (u.getInternal_user() != null) {
				if (Integer.valueOf(u.getInternal_user()) == user_id)
					return Integer.valueOf(u.getId());
			}
		}
		return -1;
	}

	/*
	 * Helping method necessary to execute certain actions which need admin-level permissions. 
	 * Therefore, this method makes sure that the connector logs into the back-end only once 
	 * and returns the connector's admin session.
	 * @return admin session necessary for execution of actions.
	 */
	private String getAdminSession() {
		if (adminSession == null) {
			Backend info = this.getBackend();
			logger.info("! --- SYSTEM RETRIEVING ADMINISTRATOR AUTHENTIFICATION AS:");
			logger.info("! --- \"" + info.getAdmin_user() + "\" on "
					+ getBackEndUrl().toString() + "with: "
					+ info.getAdmin_user() + ":" + info.getAdmin_pw());

			String[] params = new String[] { info.getAdmin_user(),
					info.getAdmin_pw() };

			ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
			String result = new Gson().toJson(proxy.call(this.getLoginMethod(),
					params));

			if ((result.length() > 5)) {
				char s = '"';
				String session_split[] = result.split(String.valueOf(s));
				result = session_split[1];
			} else {
				result = "false";
			}
			adminSession = result;
			logger.info("! --- SYSTEM SESSION: " + adminSession);
		}

		return adminSession;

	}

	// "69y 5m 4d" ---> "2003-01-07"
	// does not work
	private static String calculateDateOfBirth(String gnuHealthAge) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);

		Integer year = new Integer(gnuHealthAge.substring(0,
				gnuHealthAge.indexOf("y")));
		Integer month = new Integer(gnuHealthAge.substring(
				gnuHealthAge.indexOf(" ") + 1, gnuHealthAge.indexOf("m")));
		Integer day = new Integer(gnuHealthAge.substring(
				gnuHealthAge.lastIndexOf(" ") + 1, gnuHealthAge.indexOf("d")));

		GregorianCalendar age = new GregorianCalendar(year, month - 1, day);
		System.out.println("age: " + df.format(age.getTime()));
		GregorianCalendar dateToday = new GregorianCalendar();
		System.out.println("today: " + df.format(dateToday.getTime()));

		GregorianCalendar birthday = new GregorianCalendar();
		birthday.setTimeInMillis(dateToday.getTimeInMillis()
				- age.getTimeInMillis());

		return df.format(birthday.getTime());
	}
	
	

	/*-----  BACKEND METHODS  ----*/
	/*............................*/
	

	private String getLoginMethod() {
		return "common.db.login";
	}

	private String getLogoutMethod() {
		return "common.db.logout";
	}

	private String getPatientSearchMethod() {
		return "model.gnuhealth.patient.search";
	}

	private String getPatientReadMethod() {
		return "model.gnuhealth.patient.read";
	}

	private String getPreferencesMethod() {
		return "model.res.user.get_preferences";
	}

	private String getDiagnoseReadMethod() {
		return "model.gnuhealth.patient.disease.read";
	}

	private String getUserSearchMethod() {
		return "model.res.user.search";
	}

	private String getUserReadMethod() {
		return "model.res.user.read";
	}

	private String getPhysicianSearchMethod() {
		return "model.gnuhealth.physician.search";
	}

	/*-----  BACKEND METHOD PARAMS  ----*/

	private Object[] getReturnPatientsParams(String session) {

		return new Object[] {
				1,
				getAdminSession(),
				getAllPatientIds(),
				new String[] { "rec_name", "age", "diseases", "sex",
						"primary_care_doctor.name",
						"primary_care_doctor.rec_name" }, "REPLACE_CONTEXT" };
	}

	private Object[] getReturnPatientParams(String session, String id) {
		return new Object[] {
				1,
				session,
				new int[] { Integer.parseInt(id) },
				new String[] { "rec_name", "age", "diseases", "sex",
						"primary_care_doctor.rec_name" }, "REPLACE_CONTEXT" };
	}

	private Object[] getReturnDiagnoseParams(String id) {
		return new Object[] {
				1,
				getAdminSession(),
				new int[] { Integer.parseInt(id) },
				new String[] { "status", "pregnancy_warning", "is_active",
						"short_comment", "diagnosed_date", "healed_date",
						"pathology", "disease_severity", "is_infectious",
						"is_allergy", "pathology.rec_name",
						"date_start_treatment", "doctor", "age",
						"weeks_of_pregnancy", "is_on_treatment",
						"treatment_description", "extra_info",
						"date_stop_treatment", "pcs_code", "allergy_type",
						"doctor.rec_name", "pcs_code.rec_name" },
				"REPLACE_CONTEXT" };
	}

	
}
