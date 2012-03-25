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
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.jj1.ServiceProxy;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.Diagnose;
import eu.neq.mais.domain.LabTestResult;
import eu.neq.mais.domain.Patient;
import eu.neq.mais.domain.gnuhealth.DiagnoseGnu;
import eu.neq.mais.domain.gnuhealth.DomainParserGnu;
import eu.neq.mais.domain.gnuhealth.LabTestCriteriaGnu;
import eu.neq.mais.domain.gnuhealth.LabTestRequestCreationMessage;
import eu.neq.mais.domain.gnuhealth.LabTestRequestGnu;
import eu.neq.mais.domain.gnuhealth.LabTestResultGnu;
import eu.neq.mais.domain.gnuhealth.LabTestTypeGnu;
import eu.neq.mais.domain.gnuhealth.MedicationGnu;
import eu.neq.mais.domain.gnuhealth.PatientGnu;
import eu.neq.mais.domain.gnuhealth.PhysicianGnu;
import eu.neq.mais.domain.gnuhealth.TimeGnu;
import eu.neq.mais.domain.gnuhealth.UserGnu;
import eu.neq.mais.domain.gnuhealth.VaccinationGnu;
import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.SessionStore;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;
import eu.neq.mais.technicalservice.storage.DbHandler;
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
			Connector con = ConnectorFactory.getConnector("gnuhealth2");

			String login_name = "jgansen";
			String password = "iswi223<<";

			// LOGIN
			String user_session = con.login(login_name, password, "gnuhealth2");
			// lab test results
//			List<?> r = con.returnAllLabTestResults();
//			for (Object x : r) {
//				System.out.println(new DTOWrapper().wrap(x));
//			}
			// lab test requests
//			List<?> p = con.returnLabTestRequests("14");
//			System.out.println(new DTOWrapper().wrap(p));
			// lab test types
//			List<?> q = con.returnLabTestTypes();
//			System.out.println(new DTOWrapper().wrap(q));		
			//create lab test request
			//List<?> r = con.createLabTestRequest("556465486486", "1", "3", "9");
			//System.out.println(new DTOWrapper().wrap(r));
			
			// System.out.println("1:returnLabTestResultsForPatient("13")));"
			// System.out.println("2: " + con.returnAllLabTestResults());
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
			// String res3 = con.execute(session, con.getPatientReadMethod(),
			// params3);
			// logger.info("res3: "+res3);

			// // Find personal Patient List for UI
			// con.returnPersonalPatientsForUIList(user_session);
			// System.out.println(con.returnDashBoardData(user_session, "9"));

			// FIND MEDIACTIONS
			// System.out.println("--------- medications ----------");
			// String r = con.returnMedicationsForPatient("9");
			// System.out.println(r);

			// FIND VACCINATIONS
			// System.out.println("--------- vaccinations ----------");
			// String vacc = con.returnVaccinationsForPatient("7");
			// System.out.println(vacc);

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
			// String param = "9";
			// System.out.println(con.searchForAPatient(param));

			// Logout
			String res4 = con.logout(login_name, user_session);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    /*
    does not yet include the "real" success / failure test
     */
	@Override
	public List<?> createLabTestRequest(String date, String doctor_id,
			String name, String patient_id) {

		String labTestCreationSuccessMessage = execute(getLabTestRequestCreateMethod(),
				getLabTestRequestCreationParams(date, doctor_id, name, patient_id));
		
		Type listType = new TypeToken<List<LabTestTypeGnu>>() {
		}.getType();
		List<LabTestRequestCreationMessage> result = new ArrayList<LabTestRequestCreationMessage>();
		result.add(new LabTestRequestCreationMessage(labTestCreationSuccessMessage.substring(labTestCreationSuccessMessage.lastIndexOf(":")+1, labTestCreationSuccessMessage.lastIndexOf("}"))));

		return result;
		
	}
	
	@Override
	public List<?> returnLabTestTypes() {
		int[] labTestTypeIds = getAllLabTestTypeIds();
		
		String labTestTypeResultString = execute(getLabTestTypeReadMethod(),
				getLabTestTypeParams(labTestTypeIds));
		
		Type listType = new TypeToken<List<LabTestTypeGnu>>() {
		}.getType();
		List<LabTestTypeGnu> result = DomainParserGnu.fromJson(
				labTestTypeResultString, listType, LabTestTypeGnu.class);

		
		return result;
	}
	
	@Override
	public List<?> returnAllLabTestRequests() {
		int[] labTestRequestIds = getAllLabTestRequestIds();
		
		String labTestRequestsResultString = execute(getLabTestRequestReadMethod(),
				getLabTestRequestParams(labTestRequestIds));
		
		Type listType = new TypeToken<List<LabTestRequestGnu>>() {
		}.getType();
		List<LabTestRequestGnu> result = DomainParserGnu.fromJson(
				labTestRequestsResultString, listType, LabTestRequestGnu.class);
		
		return result;
	}

	public List<?> returnLabTestRequests(String patientId) {
		List<LabTestRequestGnu> result = (List<LabTestRequestGnu>) returnAllLabTestRequests();
		
		List<LabTestRequestGnu> resultForSpecificPatient = new ArrayList<LabTestRequestGnu>();
		
		for (LabTestRequestGnu ltrg : result) {
			ltrg.prepareDateFormat();
			if(ltrg.getPatientId().equals(patientId)){
				resultForSpecificPatient.add(ltrg);
			}
		}
		
		return resultForSpecificPatient;
		
	}
	
	public LabTestResult returnLabTestResultsDetails(String labTestId) {
		
		int[] id = new int[] { Integer.parseInt(labTestId) };
		String labTestsResult = execute(getLabTestReadMethod(), getLabTestsDetailParams(id));
		
		LabTestResultGnu result = DomainParserGnu.fromJson(labTestsResult, LabTestResultGnu.class);
		result.prepareDateFormat();
		
		String criteriaResultString = execute(getLabTestCriteriaReadMethod(), getLabTestCriteriaParams(result.getCritearea()));
		Type listType = new TypeToken<List<LabTestCriteriaGnu>>() {
		}.getType();
		List<LabTestCriteriaGnu> labTestCriteria = DomainParserGnu.fromJson(criteriaResultString, listType, LabTestCriteriaGnu.class);
		
		result.setCritearea(null);
		result.setCriteria(labTestCriteria.toArray());
		return result;
	}

	public List<?> returnAllLabTestResults() {
		int[] labTestResultsIds = getAllLabTestIds();

		String labTestsResultString = execute(getLabTestReadMethod(),
				getLabTestsParams(labTestResultsIds));

		Type listType = new TypeToken<List<LabTestResultGnu>>() {
		}.getType();
		List<LabTestResultGnu> result = DomainParserGnu.fromJson(
				labTestsResultString, listType, LabTestResultGnu.class);
		
		for (LabTestResultGnu ltrg : result) ltrg.prepareDateFormat();
		
		return result;
	}

	public List<?> returnLabTestResultsForPatient(String patientId) {
		List<LabTestResultGnu> allLabTests = (List<LabTestResultGnu>) returnAllLabTestResults();

		ArrayList<LabTestResultGnu> relevantList = new ArrayList<LabTestResultGnu>();

		for (LabTestResultGnu res : allLabTests) {
			if (res.getPatient().equals(patientId)) {
				relevantList.add(res);
			}
		}

		return relevantList;
	}

	/**
	 * 
	 * @see eu.neq.mais.connector.Connector#logout(java.lang.String,
	 *      java.lang.String)
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
	 *      java.lang.String, java.lang.String)
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
			DbHandler dbH = new DbHandler();
			dbH.saveLogin(String.valueOf(userId));
			dbH.close();
			NeqServer.getSessionStore().put(result, backendSid, userId);
		} else {
			// result = "false";
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
	 * @param method the method which is supposed to be called
	 * 
	 * @param params it's parameters
	 * 
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

		if (result.contains("NotLogged")) {
			resetAdminSession();
			params[1] = getAdminSession();
			result = execute(method, params);
		}

		return result;
	}

	/**
	 * 
	 * @see eu.neq.mais.connector.Connector#returnAllPatientsForUIList()
	 */
	@Override
	public List returnAllPatientsForUIList() {
		String patientListString = "false";
		patientListString = execute(getPatientReadMethod(),
				getReturnPatientsParams());

		Type listType = new TypeToken<List<PatientGnu>>() {
		}.getType();
		List<PatientGnu> patientList = DomainParserGnu.fromJson(
				patientListString, listType, PatientGnu.class);
		patientList = addLatestDiagnoseToPatient(patientList);

		for (PatientGnu p : patientList)
			p.prepareDateFormat();

		return patientList;
	}

	/**
	 * @throws NoSessionInSessionStoreException
	 * @see eu.neq.mais.connector.Connector#returnPersonalPatientsForUIList(java.lang.String)
	 */
	@Override
	public List returnPersonalPatientsForUIList(String session)
			throws NoSessionInSessionStoreException {
		String patientListString = "false";

		patientListString = execute(getPatientReadMethod(),
				getReturnPatientsParams());

		Type listType = new TypeToken<List<PatientGnu>>() {
		}.getType();
		List<PatientGnu> patientList = DomainParserGnu.fromJson(
				patientListString, listType, PatientGnu.class);
		patientList = addLatestDiagnoseToPatient(patientList);

		int party_id = getPhysicianId(NeqServer.getSessionStore().getUserId(
				session));

		ArrayList<PatientGnu> relevantList = new ArrayList<PatientGnu>();
		for (PatientGnu p : patientList) {
			if (Integer.valueOf(p.getPrimary_care_doctor_id()) == party_id) {
				relevantList.add(p);
				p.prepareDateFormat();
			}
		}
		return relevantList;
	}

	/**
	 * 
	 * @see eu.neq.mais.connector.Connector#searchForAPatient(java.lang.String)
	 */
	@Override
	public List searchForAPatient(String param) {
		return generatePatientListObjectById(param);
	}

	/*
	 * Helping method for gathering all relevant patients with a specific name
	 * or id
	 * 
	 * @param param search term as a name:String or id:Integer
	 * 
	 * @return list of relevant results
	 */
	private List generatePatientListObjectById(String param) {
		if (param == null)
			return null;

		String patientListString = "false";
		patientListString = execute(getPatientReadMethod(),
				getReturnPatientsParams());

		Type listType = new TypeToken<List<PatientGnu>>() {
		}.getType();
		List<PatientGnu> patientList = DomainParserGnu.fromJson(
				patientListString, listType, PatientGnu.class);

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

		for (PatientGnu p : relevantList) {
			p.prepareDateFormat();
		}

		return relevantList;

	}

	/*
	 * Helping method adding the latest diagnosis to patient objects.
	 * 
	 * @param patientList patients who should be assigned to their latest
	 * diagnoses
	 * 
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

					DiagnoseGnu tempDiagnose = DomainParserGnu.fromJson(
							diagnoseString, DiagnoseGnu.class);
					if (latestDiagnose != null) {
						latestDiagnose = latestDiagnose
								.returnLatest(tempDiagnose);
					} else {
						latestDiagnose = tempDiagnose;
					}
				}
			}
			if (latestDiagnose != null) {
				latestDiagnose.prepareDateFormat();
				patient.setLatestDiagnoseRecName(latestDiagnose
						.getPathology_rec_name());
			}
		}

		return patientList;
	}

	/**
	 * 
	 * @see eu.neq.mais.connector.Connector#returnDashBoardData(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List returnDashBoardData(String session, String id) {

		List<PatientGnu> patientList = this.generatePatientListObjectById(id);
		if (patientList == null) {
			ArrayList<String> tmp = new ArrayList<String>();
			return tmp;
		}

		List<DiagnoseGnu> diagnoseList = new ArrayList<DiagnoseGnu>();
		for (PatientGnu patient : patientList) {
			patient.prepareDateFormat();
			if (patient.getDiagnoseIds() != null) {
				for (String diseaseID : patient.getDiagnoseIds()) {
					String diagnoseString = execute(getDiagnoseReadMethod(),
							getReturnDiagnoseParams(diseaseID));
					DiagnoseGnu tmp = DomainParserGnu.fromJson(diagnoseString,
							DiagnoseGnu.class);
					tmp.prepareDateFormat();
					diagnoseList.add(tmp);

				}
			}
		}

		if (diagnoseList.isEmpty()) {
			ArrayList<String> tmp = new ArrayList<String>();
			return tmp;
		} else {
			return diagnoseList;

		}

	}

	@Override
	/**
	 * @see eu.neq.mais.connector.Connector#returnDiagnose(java.lang.String)
	 */
	public Diagnose returnDiagnose(String diagnoseID) {
		String diagnose = this.execute(this.getDiagnoseReadMethod(),
				this.getReturnDiagnoseParams(diagnoseID));

		DiagnoseGnu tmp = DomainParserGnu.fromJson(diagnose, DiagnoseGnu.class);
		tmp.prepareDateFormat();

		return tmp;
	}

	@Override
	/**
	 * @see eu.neq.mais.connector.Connector#returnPersonalInformation(java.lang.String, boolean, boolean)
	 */
	public String returnPersonalInformation(String userSession, boolean name,
			boolean picture) throws NoSessionInSessionStoreException {
		HashMap<String, String> personalInfo = new HashMap<String, String>();
		if (name) {
			try {
				personalInfo.put("name", getUserRecName(NeqServer
						.getSessionStore().getUserId(userSession).toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (picture) {
			personalInfo.put("picture", "http://i43.tinypic.com/29lzamh.png");
		}
		return new Gson().toJson(personalInfo);
	}

	@Override
	public List returnVaccinationsForPatient(String patientId) {
		List<VaccinationGnu> result;
		try {
			Object[] patientParam = new Object[] { 1, getAdminSession(),
					new int[] { Integer.parseInt(patientId) },
					new String[] { "vaccinations" }, "REPLACE_CONTEXT" };
			String patientVaccinationsString = execute(getPatientReadMethod(),
					patientParam);

			String[] patientVaccinations;

			patientVaccinations = (patientVaccinationsString.substring(
					patientVaccinationsString.lastIndexOf("[") + 1,
					patientVaccinationsString.lastIndexOf("]}]}"))).split(", ");

			result = new ArrayList<VaccinationGnu>();
			for (String vaccId : patientVaccinations) {
				try {
					VaccinationGnu vaccGnu = DomainParserGnu.fromJson(
							returnVaccination(vaccId), VaccinationGnu.class);
					vaccGnu.prepareDateFormat();
					result.add(vaccGnu);
				} catch (NumberFormatException esx) {
					return new ArrayList<VaccinationGnu>();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	private String returnVaccination(String vaccId) {
		String result = execute(getVaccinationReadMethod(),
				getVaccinationParams(vaccId));

		return result;
	}

	public List returnMedicationsForPatient(String patientID) {
		Object[] patientParam = new Object[] { 1, getAdminSession(),
				new int[] { Integer.parseInt(patientID) },
				new String[] { "medications" }, "REPLACE_CONTEXT" };
		String patient = execute(getPatientReadMethod(), patientParam);
		patient = patient.substring(patient.indexOf("[") + 1,
				patient.indexOf("]}"));

		MedicationHelper medHelper = new Gson().fromJson(patient,
				MedicationHelper.class);
		List<String> medicationIds = medHelper.getMedications();

		List<MedicationGnu> result = new ArrayList<MedicationGnu>();
		for (String medId : medicationIds) {
			MedicationGnu medObj = DomainParserGnu.fromJson(
					returnMedication(medId), MedicationGnu.class);
			medObj.prepareDateFormat();
			result.add(medObj);
		}

		return result;
	}

	private String returnMedication(String medicationID) {
		String result = execute(getMedicationReadMethod(),
				getMedicationParams(medicationID));

		return result;
	}

	/*
	 * Helping method to find all registered patients.
	 * 
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
	 * 
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
	 * 
	 * @param username
	 * 
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
	 * 
	 * @param id user's ID
	 * 
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
	 * Helping method returning the ID's of all parties of the GNUHealth
	 * back-end.
	 * 
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
	 * Helping method returning the ID's of all Lab tests of the GNUHealth
	 * back-end.
	 * 
	 * @return Array of IDs.
	 */
	private int[] getAllLabTestIds() {
		String session = getAdminSession();

		int[] idList;

		// Search Patients
		Object[] params = new Object[] { 1, session, new String[] {}, 0, 1000,
				null, "REPLACE_CONTEXT" };

		String result = execute(getLabTestSearchMethod(), params);
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
	 * Helping method returning the ID's of all lab test requests of the GNUHealth
	 * back-end.
	 * 
	 * @return Array of IDs.
	 */
	private int[] getAllLabTestRequestIds() {
		String session = getAdminSession();

		int[] idList;

		// Search Patients
		Object[] params = new Object[] { 1, session, new String[] {}, 0, 1000,
				null, "REPLACE_CONTEXT" };

		String result = execute(getLabTestRequestSearchMethod(), params);
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
	 * Helping method returning the ID's of all lab test types of the GNUHealth
	 * back-end.
	 * 
	 * @return Array of IDs.
	 */
	private int[] getAllLabTestTypeIds() {
		String session = getAdminSession();

		int[] idList;

		// Search Patients
		Object[] params = new Object[] { 1, session, new String[] {}, 0, 1000,
				null, "REPLACE_CONTEXT" };

		String result = execute(getLabTestTypeSearchMethod(), params);
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
	 * 
	 * @param user_id
	 * 
	 * @return physician id
	 */
	private int getPhysicianId(int user_id) {
		String session = getAdminSession();
		int[] allphys = getAllPartyIds();

		Object[] params = new Object[] { 1, session, allphys,
				new String[] { "id", "internal_user" }, "REPLACE_CONTEXT" };

		// Execute search
		String res = execute("model.party.party.read", params);

		// convert to list
		Type listType = new TypeToken<List<PhysicianGnu>>() {
		}.getType();
		List<PhysicianGnu> userList = DomainParserGnu.fromJson(res, listType,
				PhysicianGnu.class);

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
	 * Helping method necessary to execute certain actions which need
	 * admin-level permissions. Therefore, this method makes sure that the
	 * connector logs into the back-end only once and returns the connector's
	 * admin session.
	 * 
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

	private void resetAdminSession() {
		adminSession = null;
	}

	/*-----  BACKEND METHODS  ----*/
	/* ............................ */

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

	private String getVaccinationReadMethod() {
		return "model.gnuhealth.vaccination.read";
	}

	private String getMedicationSearchMethod() {
		return "model.gnuhealth.patient.medication.search";
	}

	private String getMedicationReadMethod() {
		return "model.gnuhealth.patient.medication.read";
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
	
	private String getLabTestRequestSearchMethod(){
		return "model.gnuhealth.patient.lab.test.search";
	}
	
	private String getLabTestRequestReadMethod(){
		return "model.gnuhealth.patient.lab.test.read";
	}

	private String getLabTestSearchMethod() {
		return "model.gnuhealth.lab.search";
	}

	private String getLabTestReadMethod() {
		return "model.gnuhealth.lab.read";
	}
	
	private String getLabTestCriteriaReadMethod() {
		return "model.gnuhealth.lab.test.critearea.read";
	}
	
	private String getLabTestTypeSearchMethod(){
		return "model.gnuhealth.lab.test_type.search";
	}
	
	private String getLabTestTypeReadMethod(){
		return "model.gnuhealth.lab.test_type.read";
	}
	
	private String getLabTestRequestCreateMethod(){
		return "model.gnuhealth.patient.lab.test.create";
	}

	/*-----  BACKEND METHOD PARAMS  ----*/
	
	private Object[] getLabTestTypeParams(int[] ids){
		return new Object[] {
				1,
				getAdminSession(),
				ids,
				new String[] { "id", "code", "name"
				},
				"REPLACE_CONTEXT" };
	}

	private Object[] getLabTestsParams(int[] ids) {
		return new Object[] {
				1,
				getAdminSession(),
				ids,
				new String[] { "date_analysis", "test", "patient", "name",
						"test.rec_name", "patient.rec_name"
				},
				"REPLACE_CONTEXT" };
	}
	
	private Object[] getLabTestRequestParams(int[] ids){
		return new Object[] {
				1,
				getAdminSession(),
				ids,
				new String[] { 
					"date","patient_id", "state", "doctor_id.rec_name", "name.rec_name"
				},
				"REPLACE_CONTEXT" };
	}
	
	private Object[] getLabTestRequestCreationParams(String date, String doctor_id, String name, String patient_id){
		Map<Object,Object> paramMap = new HashMap<Object,Object>();
		paramMap.put("date", new TimeGnu(new Long(date)));
		paramMap.put("doctor_id", doctor_id);
		paramMap.put("name", name);
		paramMap.put("patient_id", patient_id);
		return new Object[] {
				1,
				getAdminSession(),
				paramMap,
				"REPLACE_CONTEXT" };
	}
	
	private Object[] getLabTestsDetailParams(int[] ids) {
		return new Object[] {
				1,
				getAdminSession(),
				ids,
				new String[] { "date_analysis", "test", "patient", "name",
						"test.rec_name", "patient.rec_name", "date_requested",
						"requestor", "results", "pathologist", "critearea", "diagnosis"
				},
				"REPLACE_CONTEXT" };
	}
	
	private Object[] getLabTestCriteriaParams(int[] ids) {
		return new Object[] {
				1,
				getAdminSession(),
				ids,
				new String[] {
					"name", "result_text", "remarks", "upper_limit", "lower_limit", "result", "excluded", "units", "warning", "units.rec_name"
				},
				"REPLACE_CONTEXT"
		};
	}

	private Object[] getMedicationParams(String id) {

		return new Object[] {
				1,
				getAdminSession(),
				new int[] { Integer.parseInt(id) },
				new String[] { "course_completed", "discontinued", "dose",
						"route", "duration_period", "frequency_unit",
						"dose_unit", "frequency", "indication", "notes",
						"is_active", "admin_times", "common_dosage",
						"discontinued_reason", "duration", "form.rec_name",
						"doctor.rec_name", "route.rec_name",
						"dose_unit.rec_name", "indication.rec_name",
						"common_dosage.rec_name", "medicament.rec_name",
						"start_treatment", "end_treatment" }, "REPLACE_CONTEXT" };
	}

	private Object[] getVaccinationParams(String id) {
		return new Object[] {
				1,
				getAdminSession(),
				new int[] { Integer.parseInt(id) },
				new String[] { "dose", "vaccine.rec_name", "observations",
						"vaccine_lot", "institution.rec_name", "date",
						"next_dose_date" }, "REPLACE_CONTEXT" };
	}

	private Object[] getReturnPatientsParams() {

		return new Object[] {
				1,
				getAdminSession(),
				getAllPatientIds(),
				new String[] { "rec_name", "age", "diseases", "sex",
						"primary_care_doctor.name",
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

	/*
	 * Helper class for parsing a json string into an object
	 * 
	 * @author seba
	 */
	private class MedicationHelper {
		List<String> medications;

		public List<String> getMedications() {
			return medications;
		}

		public void setMedications(List<String> medications) {
			this.medications = medications;
		}
	}





}
