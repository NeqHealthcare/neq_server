package eu.neq.mais.connector;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.neq.mais.connector.gnuhealth.GNUHealthConnectorImpl;
import eu.neq.mais.domain.Diagnose;
import eu.neq.mais.domain.LabTestResult;
import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;
import eu.neq.mais.technicalservice.Settings;

/**
 * Connector interface to standardize the connector implemetations. One
 * connector builds one connection to one back-end HIS, like GNU Health or SAP
 * Healthcare.
 * 
 * @author Sebastian Schütz, Jan Gansen
 * 
 */
public abstract class Connector {

	private Backend backend;
	protected static Logger logger = Logger.getLogger("eu.neq.mais.connector");
	private static Connector instance = null;

	public Connector() {
		logger.setLevel(Level.ALL); // DEBUG PURPOSES
		logger.addHandler(FileHandler
				.getLogFileHandler(Settings.LOG_FILE_CONNECTORS));
	}

	/**
	 * Loging into the Backend
	 * 
	 * @param username
	 *            back-end user login name
	 * @param password
	 *            corresponding password
	 * @return Session ID: Login successful: session Login unsuccessful: false
	 *         as string
	 */
	public abstract String login(String username, String password,
			String backendSid);

	/**
	 * Requesting logout from the system
	 * 
	 * @param username
	 *            users login name
	 * @param Session
	 *            current session
	 * @return true if successful, false if not
	 */
	public abstract String logout(String username, String Session);

	/**
	 * Get a set of patients with limited information richness for displaying
	 * major data in a list.
	 * 
	 * @return a list of patients with limited information richness.
	 */
	public abstract List<?> returnAllPatientsForUIList();

	/**
	 * Get a set of patients with regards to their allocated primary care
	 * doctor.
	 * 
	 * @param userSession
	 *            the session of the primary care doctor
	 * @return subset of all patients
	 * @throws NoSessionInSessionStoreException
	 */
	public abstract List<?> returnPersonalPatientsForUIList(String userSession)
			throws NoSessionInSessionStoreException;

	/**
	 * Get information about a diagnosis
	 * 
	 * @param diagnoseID
	 *            diagnosis identifier
	 * @return diagnosis details
	 */
	public abstract Diagnose returnDiagnose(String diagnoseID);

	/**
	 * Search for a specific patient
	 * 
	 * @param param
	 *            Name or ID
	 * @return list of relevant patients (can be empty if none found)
	 */
	public abstract List<?> searchForAPatient(String param);

	/**
	 * Get information about a specific medication
	 */
	public abstract List<?> returnMedicationsForPatient(String patientID);

	/**
	 * Get informatiom about a specific vaccinations for a patient
	 */
	public abstract List<?> returnVaccinationsForPatient(String patientID);

	/**
	 * Gets a user's name or profile picture
	 * 
	 * @param userSession
	 *            the user's session (identifier)
	 * @param name
	 *            true if lookup for a name (not interdependent with the param
	 *            picture)
	 * @param picture
	 *            true if lookup for a picture
	 * @return a json-string containing a user's name (if name:true) and a
	 *         picture (if picture:true)
	 * @throws NoSessionInSessionStoreException
	 */
	public abstract String returnPersonalInformation(String userSession,
			boolean name, boolean picture)
			throws NoSessionInSessionStoreException;

	/**
	 * Getting all relevant data for a user's dashboard: -all patients -all
	 * according diagnosis
	 * 
	 * @param session
	 * @param id
	 * @return JSON-String
	 */
	public abstract List<?> returnDashBoardData(String session, String id);

	/**
	 * Returns all Lab Test Results
	 * 
	 * @return list of all lab test results
	 */
	public abstract List<?> returnAllLabTestResults();

	/**
	 * Returns all lab test results for a specific patient
	 * 
	 * @param patientId
	 * @return all lab test results for a specific patient
	 */
	public abstract List<?> returnLabTestResultsForPatient(String patientId);

	/**
	 * 
	 * @param labTestId
	 * @return
	 */
	public abstract LabTestResult returnLabTestResultsDetails(String labTestId);

	/**
	 * 
	 * @return
	 */
	public abstract List<?> returnAllLabTestRequests();

	/**
	 * Returns all lab test requests for a specific patient
	 * 
	 * @param patientId
	 * @return all lab test requests for a specific patient
	 */
	public abstract List<?> returnLabTestRequests(String patientId);

	/**
	 * Returns all lab test types
	 * 
	 * @return
	 */
	public abstract List<?> returnLabTestTypes();

	/**
	 * Creates a new lab test request
	 * 
	 * @param date
	 * @param doctor_id
	 * @param name
	 * @param patient_id
	 * @return
	 */
	public abstract List<?> createLabTestRequest(String date, String doctor_id,
			String name, String patient_id);

	/**
	 * Sets a Connector's back-end. This is where the target for the Connector's
	 * interaction is defined. A back-end can be e.g. a running GNU Health
	 * instance or similar systems and must be defined in the back-end config.
	 * 
	 * @param backend
	 *            Back-end
	 */
	public void setBackend(Backend backend) {
		this.backend = backend;
	}

	/**
	 * Returns the assigned back-end of a connector.
	 * 
	 * @return back-end
	 */
	public Backend getBackend() {
		return this.backend;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		Connector.logger = logger;
	}

	/**
	 * Returns the current connectors instance. If there is none, a new is being
	 * created.
	 * 
	 * @return current instance
	 */
	public static Connector getInstance() {

		if (instance == null) {
			instance = new GNUHealthConnectorImpl();
		}
		return instance;
	}

}
