package eu.neq.mais.connector;

import eu.neq.mais.connector.gnuhealth.GNUHealthConnectorImpl;
import eu.neq.mais.domain.Diagnose;
import eu.neq.mais.domain.LabTestResult;
import eu.neq.mais.domain.User;
import eu.neq.mais.domain.gnuhealth.DocumentGnu;
import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;
import eu.neq.mais.technicalservice.Settings;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Connector interface to standardize the connector implemetations. One
 * connector builds one connection to one back-end HIS, like GNU Health or SAP
 * Healthcare.
 *
 * @author Sebastian Schütz, Jan Gansen
 */
public abstract class Connector {

    private Backend backend;
    protected static Logger logger = Logger.getLogger("eu.neq.mais.connector");
    private static Connector instance = null;
    protected static String adminSession = null;
    
    public abstract void init();

    public Connector() {
        logger.setLevel(Level.ALL); // DEBUG PURPOSES
        logger.addHandler(FileHandler
                .getLogFileHandler(Settings.LOG_FILE_CONNECTORS));
    }

    /**
     * Loging into the Backend
     *
     * @param username back-end user login name
     * @param password corresponding password
     * @return Session ID: Login successful: session Login unsuccessful: false
     *         as string
     */
    public abstract String login(String username, String password,
                                 String backendSid);

    /**
     * Requesting logout from the system
     *
     * @param username users login name
     * @param Session  current session
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
     * @param userSession the session of the primary care doctor
     * @return subset of all patients
     * @throws NoSessionInSessionStoreException
     *
     */
    public abstract List<?> returnPersonalPatientsForUIList(String userSession)
            throws NoSessionInSessionStoreException;

    /**
     * Get information about a diagnosis
     *
     * @param diagnoseID diagnosis identifier
     * @return diagnosis details
     */
    public abstract Diagnose returnDiagnose(String diagnoseID);

    /**
     * Search for a specific patient
     *
     * @param param Name or ID
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
     * @param userSession the user's session (identifier)
     * @param name        true if lookup for a name (not interdependent with the param
     *                    picture)
     * @param picture     true if lookup for a picture
     * @return a json-string containing a user's name (if name:true) and a
     *         picture (if picture:true)
     * @throws NoSessionInSessionStoreException
     *
     */
    public abstract User returnPersonalInformation(String user_id)
            throws NoSessionInSessionStoreException;

    /**
     * Getting all relevant data for a user's dashboard: -all patients -all
     * according diagnosis
     *
     * @param session
     * @param id
     * @return JSON-String
     */
    public abstract List<?> returnDiagnosesForPatient(String session, String id);

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
     * @param labTestId
     * @return
     */
    public abstract LabTestResult returnLabTestResultsDetails(String labTestId);

    /**
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
     * Check
     */
    public abstract List<?> checkForTestedLabRequests(String doctor_id);
    
    /**
     * 
     * @param doctor_id
     * @return
     */
    public abstract List<?> returnNewestLabTestResults(String doctor_id);

    /**
     * Returns all lab test types
     *
     * @return
     */
    public abstract List<?> returnLabTestTypes();
    
    /**
     * returns all disease types that were inserted into the backend 
     */
    public abstract List<?> returnDiseases();
    
    /**
     * Creates a diagnose
     * 
     */
    public abstract List<?> createDiagnose(Map<Object,Object> params);

    /**
     * return procedures that are used to treat patients with a specific disease
     * 
     * @return
     */
    public abstract List<?> returnProcedures();

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
     * Returns the latest appointments for a specific user. The count parameter specifies the maximum number of appointments returned
     * 
     * @param count
     * @param userId
     * @return
     */
	public abstract List<?> returnAppointments(Integer count, Integer userId);
    
    /**
     * return all news feed topics that are contained in the /resources/newsFeeds.json with id,topic and url
     * @return
     */
	public abstract List<?> returnNewsTopics();
	
	
	/**
	 * returns the news feed that matches the specified id
	 * 
	 * @param id
	 * @return news feed
	 */
	public abstract List<?> returnNewsFeed(Integer id, Integer count);
	
	
	
	
	
	
	
    /**
     * Sets a Connector's back-end. This is where the target for the Connector's
     * interaction is defined. A back-end can be e.g. a running GNU Health
     * instance or similar systems and must be defined in the back-end config.
     *
     * @param backend Back-end
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

    public abstract List<?> returnDocumentList(String id);

    /**
     * returns the requested document
     * 
     * @param documentID
     * @return document
     */
    public abstract List<DocumentGnu> returnDocumentData(String documentID);

    
    /**
     * Returns all chatter users except the one requesting the chatter users
     * 
     * @param userId
     * @return
     */
	public abstract List<?> returnChatterUsers(Integer userId);

	/**
	 * This method sets the is_followed status of the potentially followed_user to true or false with regards to the user who wants to follow/unfollow 
	 * 
	 * @param user
	 * @param followed_user
	 * @param is_followed
	 * @return
	 */
	public abstract List<?> updateChatterUser(Integer user, Integer followed_user,
			boolean is_followed);

	/**
	 * This method saves a chatter post with the following attributes
	 * 
	 * @param userId - the user that created the post
	 * @param message - the message
	 * @param parentId - (optional) the id of the message to which this post is a reply
	 * @return
	 */
	public abstract List<?> saveChatterPost(Integer userId, String message, Long parentId);

	/**
	 * This method returns messages from all the people followed by the user
	 * 
	 * @param userId
	 * @return
	 */
	public abstract List<?> returnChatterPosts(Integer userId);
	
	/**
	 * This method returns the number of patients a user has 
	 * 
	 * @param userId
	 * @return
	 */
	public abstract List<?> returnNumberOfPatients(Integer userId);
	
	




}
