package eu.neq.mais.connector;

import java.util.logging.Level;
import java.util.logging.Logger;

import eu.neq.mais.connector.gnuhealth.GNUHealthConnectorImpl;
import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.Settings;

/**
 * Connector interface to standardize the connector implemetations.
 * One connector builds one connection to one back-end HIS, like GNU Health or SAP Healthcare.
 * @author Sebastian Schütz, Jan Gansen
 *
 */
public abstract class Connector {
	
	private Backend backend;
	protected static Logger logger = Logger.getLogger("eu.neq.mais.connector");
	private static Connector instance = null;
	
	public Connector() {
		logger.setLevel(Level.ALL); // DEBUG PURPOSES
		logger.addHandler(FileHandler.getLogFileHandler(Settings.LOG_FILE_CONNECTORS));
	}
	
	/**
	 * Loging into the Backend
	 * @param username back-end user login name
	 * @param password corresponding password
	 * @return Session ID:
	 * Login successful: session
	 * Login unsuccessful: false as string
	 */
	public abstract String login(String username, String password, String backendSid);
	
	/**
	 * Requesting logout from the system
	 * @param username users login name
	 * @param Session current session 
	 * @return true if successful, false if not
	 */
	public abstract String logout(String username, String Session);
	
	
	/**
	 * Get a set of patients with limited information richness for displaying major data in a list.
	 * @return a list of patients with limited information richness.
	 */
	public abstract String returnAllPatientsForUIList();
	
	/**
	 * Get a set of patients with regards to their allocated primary care doctor.
	 * @param userSession the session of the primary care doctor
	 * @return subset of all patients
	 */
	public abstract String returnPersonalPatientsForUIList(String userSession);
	
	/**
	 * Get information about a diagnosis
	 * @param diagnoseID diagnosis identifier
	 * @return diagnosis details
	 */
	public abstract String returnDiagnose(String diagnoseID);
	
	/**
	 * Search for a specific patient
	 * @param param Name or ID
	 * @return list of relevant patients (can be empty if none found)
	 */
	public abstract String searchForAPatient(String param);
	
	/**
	 * Get information about a specific medication
	 */
	public abstract String returnMedicationsForPatient(String patientID);
	
	/**
	 * Gets a user's name or profile picture
	 * @param userSession the user's session (identifier)
	 * @param name true if lookup for a name (not interdependent with the param picture)
	 * @param picture true if lookup for a picture 
	 * @return a json-string containing a user's name (if name:true) and a picture (if picture:true)
	 */
	public abstract String returnPersonalInformation(String userSession, boolean name, boolean picture);
	
	/**
	 * Getting all relevant data for a user's dashboard:
	 * -all patients
	 * -all according diagnosis
	 * @param session
	 * @param id
	 * @return JSON-String
	 */
	public abstract String returnDashBoardData(String session, String id);
	
	/**
	 * Sets a Connector's back-end. This is where the target for the Connector's interaction is defined.
	 * A back-end can be e.g. a running GNU Health instance or similar systems and must be defined in the back-end config.
	 * @param backend Back-end
	 */
	public void setBackend(Backend backend){
		this.backend = backend;
	}
	
	/**
	 * Returns the assigned back-end of a connector.
	 * @return back-end
	 */
	public Backend getBackend(){
		return this.backend;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		Connector.logger = logger;
	}

	/**
	 * Returns the current connectors instance. If there is none, a new is being created.
	 * @return current instance
	 */
	public static Connector getInstance() {

		if (instance == null) {
			instance = new GNUHealthConnectorImpl();
		}
		return instance;
	}
	
	
	
	
}
