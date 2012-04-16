package eu.neq.mais.connector.gnuhealth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.jj1.ServiceProxy;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import eu.neq.mais.NeqServer;
import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.Article;
import eu.neq.mais.domain.Diagnose;
import eu.neq.mais.domain.LabTestRequest;
import eu.neq.mais.domain.LabTestResult;
import eu.neq.mais.domain.User;
import eu.neq.mais.domain.gnuhealth.*;
import eu.neq.mais.technicalservice.Backend;
import eu.neq.mais.technicalservice.DTOWrapper;
import eu.neq.mais.technicalservice.FileHandler;
import eu.neq.mais.technicalservice.NewsFeed;
import eu.neq.mais.technicalservice.SessionStore.NoSessionInSessionStoreException;
import eu.neq.mais.technicalservice.storage.DbHandler;

import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.*;

import static java.lang.Integer.*;

/**
 * Connector implementation for a GNU Health Hospital Information System. All
 * methods provided by this class deal with accessing information of the
 * GNUHealth system only. The underlying technology is JSON-RPC.
 *
 * @author Jan Gansen, Sebastian Schütz, Denny Stohr
 */
public class GNUHealthConnectorImpl extends Connector {

    private static int gnid = 55;
    private static String adminSession = null;


    /**
     * Main method is for a test run of several functions of the connector and
     * should not be used! For testing purposes only.
     *
     * @param args - no influence
     */
    public static void main(String[] args) {
    	  try {
              Connector con = ConnectorFactory.getConnector("gnuhealth2");

              String login_name = "jgansen";
              String password = "iswi223<<";

              // LOGIN
              String user_session = con.login(login_name, password, "gnuhealth2");
              List<?> res = con.checkForTestedLabRequests("1");
              for (Object r : res) System.out.println("1:"+ r);
              
              System.out.println("-----");
       
//              res = con.returnNewestLabTestResults("1");
//              for (Object r : res) System.out.println("1:"+ ((LabTestResultGnu) r).getId());
//              
              // return appointments 
              
//             try {
//				res = con.returnAppointments(2, NeqServer.getSessionStore().getUserId(user_session));
//			} catch (NoSessionInSessionStoreException e) {
//				e.printStackTrace();
//			}
//             for (Object r : res) System.out.println("1:"+ ((AppointmentGnu) r).toString());
//             System.out.println(new DTOWrapper().wrap(res)); 
             
             
             // return news topics
//             res = con.returnNewsTopics();
//             System.out.println(new DTOWrapper().wrap(res)); 
//             
//             // return news feed
//             
//             res = con.returnNewsFeed(1, 5);
//             for (Object r : res) System.out.println("title: "+((Article)r).getTitle()+" date: "+new Date(((Article)r).getPubDate()));
//             System.out.println(new DTOWrapper().wrap(res));
             
              // diagnose creation methods         
//              res = con.returnDiseases();
//              String response = new DTOWrapper().wrap(res);
//              System.out.println("r: "+response);
//              System.out.println("-----");
//              res = con.returnProcedures();
//              response = new DTOWrapper().wrap(res);
//              System.out.println("r: "+response);
//              
          	  Map<Object,Object> params = new HashMap<Object, Object>();
              
          	params.put("status","c"); //e.g. c
          	params.put("is_allergy",true); //e.g. true
          	params.put("doctor",1); //e.g. 1
          	params.put("pregnancy_warning",true); //e.g. true
          	params.put("age",15); //e.g. 15
          	params.put("weeks_of_pregnancy",10); //e.g. 10
          	params.put("date_start_treatment","489534758"); //e.g. 489534758098
          	params.put("short_comment","text"); //e.g. text
          	params.put("is_on_treatment",true); //e.g. true
          	params.put("is_active",true); //e.g. true
          	params.put("diagnosed_date","489534758"); //e.g. 489534758098
          	params.put("treatment_description","text text"); //e.g. text
          	params.put("healed_date","489534758"); //e.g. 489534758098
          	params.put("date_stop_treatment","489534758"); //e.g. 489534758098
          	params.put("pcs_code",5); //e.g. 5
          	params.put("pathology",11); //e.g. 11
          	params.put("allergy_type","fa"); //e.g. fa
          	params.put("disease_severity","3_sv"); //e.g. 3_sv
          	params.put("is_infectious",true); // e.g. true
          	params.put("extra_info","extra_info"); // e.g. extra info text
            params.put("patient_id", 14); // wird noch nicht vom frontend zurückgegeben!  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
          	params.put("disease_id", 23); // wird noch nicht vom frontend zurückgegeben! XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
            
          	res = con.createDiagnose(params);
            for (Object r : res) System.out.println(":"+ ((DiagnoseCreationMessageGnu) r).toString());  
              
        //   con.createLabTestRequest("656465486486", "1", "3", "9");

//              List<?> res = con.checkForTestedLabRequests("1");
//              for (Object r : res) System.out.println("1:"+ r);
//              
//              System.out.println("-----");
  //
//              res = con.checkForTestedLabRequests("1");
//              for (Object r : res) { 
//              	System.out.println("1:"+ r);
//              	DbHandler dbh = new DbHandler();
//              	boolean ok = dbh.removeLabTestRequest(String.valueOf(r));
//              	dbh.close();
//              	System.out.println("worked: "+ok);
//              }
  //
//              System.out.println("-----");
//              
//              res = con.checkForTestedLabRequests("1");
//              for (Object r : res) System.out.println("1:"+ r);


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
//               System.out.println("--------PERSONAL INFORMATION OF USER: "+((GNUHealthConnectorImpl)con).returnPersonalInformation(USER_ID));

              // searching for a patient
              // String param = "9";
              // System.out.println(con.searchForAPatient(param));

              // Logout
              String res4 = con.logout(login_name, user_session);

          } catch (Exception e) {
              e.printStackTrace();
          }

      }
    
    
	@Override
	public List<?> returnNewsTopics() {
		Map<Integer,NewsFeed> newsMap;
		try {
			newsMap = FileHandler.getNewsFeeds();
			return new ArrayList<>(newsMap.values());
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return new ArrayList<NewsFeed>();
	}


	@Override
	public List<?> returnNewsFeed(Integer id, Integer count) {
	
		try {
			List<Article> articles = new ArrayList<Article>();
			URL feedURL = new URL(FileHandler.getNewsFeeds().get(id).getUrl());
			
		    SyndFeedInput input = new SyndFeedInput();
		    SyndFeed sf = input.build(new XmlReader(feedURL));

		    List entries = sf.getEntries();
		    Iterator it = entries.iterator();
		    while (it.hasNext()) {
			    SyndEntry entry = (SyndEntry)it.next();
			    Article tempA = new Article(entry.getTitle(),entry.getLink(),entry.getDescription().getValue(),entry.getPublishedDate());
		    	articles.add(tempA);
		    }
		    
		    Collections.sort(articles);
		    Collections.reverse(articles);
	        List<Article> finalResult = articles;
	        try{
	        	finalResult = articles.subList(0, count);
	        } catch(Exception e){
	        }        
	        return finalResult;
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Article>();
	}

    

    
    private int[] getAllAppointmentIds() {
        String session = getAdminSession();

        int[] idList;

        Object[] params = new Object[]{1, session, new String[]{}, 0, 1000,
                null, "REPLACE_CONTEXT"};

        String result = execute(getAppointmentSearchMethod(), params);
        result = result.substring(result.indexOf("[") + 1,
                result.lastIndexOf("]"));

        String[] idListString = result.split(", ");
        idList = new int[idListString.length];

        for (int i = 0; i < idListString.length; i++) {
            idList[i] = parseInt(idListString[i]);
        }
        return idList;
    }
   
    
	@Override
	public List<?> returnAppointments(Integer count, Integer userId) {

		Integer doctorId = getPhysicianId(Integer.valueOf(userId));
		
        int[] appointmentIds = getAllAppointmentIds();

        String appointmentsString = execute(getAppointmentReadMethod(),
                getAppointmentParams(appointmentIds));

        Type listType = new TypeToken<List<AppointmentGnu>>() {
        }.getType();
        List<AppointmentGnu> result = DomainParserGnu.fromJson(
        		appointmentsString, listType, AppointmentGnu.class);
               
        List<AppointmentGnu> doctorAppointments = new ArrayList<AppointmentGnu>();
          
        for(AppointmentGnu a : result){
        	if(a.getDoctorId().equals(doctorId)){
        		a.prepareDateFormat();
//        		System.out.println("appointment date: "+new Date((Long)a.getDate()));
//        		System.out.println("now date: "+new Date());
        		
        		
        		if((((Long)a.getDate())+3600000) >= new Date().getTime()){
            		doctorAppointments.add(a);
        		}

        	}
        }
        Collections.sort(doctorAppointments);
        List<AppointmentGnu> finalResult = doctorAppointments;
        try{
        	finalResult = doctorAppointments.subList(0, count);
        } catch(Exception e){
        }
        
        return finalResult;
		
	}
    
    
	@Override
	public List<?> createDiagnose(Map<Object, Object> params) {


        String diagnoseCreationMessage = execute(
        		getPatientWriteMethod(),
        		getDiagnoseCreationParams(params));

        List<DiagnoseCreationMessageGnu> result = new ArrayList<DiagnoseCreationMessageGnu>();
        String successId = diagnoseCreationMessage.substring(
        		diagnoseCreationMessage.lastIndexOf(":") + 1,
        		diagnoseCreationMessage.lastIndexOf("}"));
        result.add(new DiagnoseCreationMessageGnu(successId));

		
		return result;
	}


    public List<?> returnDocumentList(String patientID) {
        //Logger.info("test")  ;
        int[] id = new int[1];   //method for all ids
        id[0] = (int) Integer.parseInt(patientID);

        String documentListString = execute(getDocumentListMethod(),
                getDocumentListParams(id));

        Type idListToken = new TypeToken<List<Integer>>() {
        }.getType();

        List<Integer> idList = DomainParserGnu.fromJson(
                documentListString, idListToken, Integer.class);

        int[] idListArray = new int[idList.size()];
        for (int e : idList) {
            idListArray[idList.indexOf(e)] = e;
        }

        String documentMetaDataString = execute(getDocumentReadMethod(),
                getDocumentMetaDataParams(idListArray, false));

        Type listType = new TypeToken<List<DocumentGnu>>() {
        }.getType();
        List<DocumentGnu> result = DomainParserGnu.fromJson(
                documentMetaDataString, listType, DocumentGnu.class);

        //for (DocumentGnu doc : result) doc.prepareDateFormat();

        return result;
    }

    public List<DocumentGnu> returnDocumentData(String documentId) {
        int[] id;   //method for all ids
        id = new int[]{parseInt(documentId)};
        String documentListString = execute(getDocumentReadMethod(),
                getDocumentReadParams(id));

        Type listType = new TypeToken<List<DocumentGnu>>() {
        }.getType();
        List<DocumentGnu> result = DomainParserGnu.fromJson(
                documentListString, listType, DocumentGnu.class);

        logger.info(documentListString);
        return result;
    }

    @Override
    public List<?> checkForTestedLabRequests(String doctor_id) {
        DbHandler dbh = new DbHandler();
        List<eu.neq.mais.technicalservice.storage.LabTestRequest> openRequests = dbh.getLabTestRequests(String
                .valueOf(doctor_id));

        List<Integer> recentlyTestedRequestsIds = new ArrayList<Integer>();

        if (openRequests.isEmpty()) {
            dbh.close();
            return recentlyTestedRequestsIds;
        }

        List<?> labTests = returnAllLabTestRequests();

        for (eu.neq.mais.technicalservice.storage.LabTestRequest labTestRequest : openRequests) {
            for (Object uncastLabTest : labTests) {
                LabTestRequestGnu labTestRequestGnu = (LabTestRequestGnu) uncastLabTest;

                if (valueOf(
                        labTestRequest.getRequest_id().replaceAll(" ", ""))
                        .equals(labTestRequestGnu.getId())) {
                    if (labTestRequestGnu.getState().equals("tested")) {
                        //dbh.removeLabTestRequest(labTestRequest.getRequest_id()); OUTSOURCED TO OWN FUNCTION;
                        recentlyTestedRequestsIds.add(new Integer(labTestRequest
                                .getRequest_id().replaceAll(" ", "")));
                    }
                }

            }
        }

        dbh.close();
        return recentlyTestedRequestsIds;
    }

    /*
      * does not yet include the "real" success / failure test
      */
    @Override
    public List<?> createLabTestRequest(String date, String doctor_id,
                                        String name, String patient_id) {

        String labTestCreationSuccessMessage = execute(
                getLabTestRequestCreateMethod(),
                getLabTestRequestCreationParams(date, doctor_id, name,
                        patient_id));

        Type listType = new TypeToken<List<LabTestTypeGnu>>() {
        }.getType();
        List<LabTestRequestCreationMessage> result = new ArrayList<LabTestRequestCreationMessage>();
        String successId = labTestCreationSuccessMessage.substring(
                labTestCreationSuccessMessage.lastIndexOf(":") + 1,
                labTestCreationSuccessMessage.lastIndexOf("}"));
        result.add(new LabTestRequestCreationMessage(successId));

        DbHandler dbh = new DbHandler();
        dbh.saveLabTestRequests(doctor_id, successId);

        dbh.close();

        return result;

    }


    private int[] getAllLabTestTypeIds() {
        String session = getAdminSession();

        int[] idList;

        // Search Patients
        Object[] params = new Object[]{1, session, new String[]{}, 0, 1000,
                null, "REPLACE_CONTEXT"};

        String result = execute(getLabTestTypeSearchMethod(), params);
        result = result.substring(result.indexOf("[") + 1,
                result.lastIndexOf("]"));

        String[] idListString = result.split(", ");
        idList = new int[idListString.length];

        for (int i = 0; i < idListString.length; i++) {
            idList[i] = parseInt(idListString[i]);
        }
        return idList;
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

        String labTestRequestsResultString = execute(
                getLabTestRequestReadMethod(),
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
            if (ltrg.getPatientId().equals(patientId)) {
                resultForSpecificPatient.add(ltrg);
            }
        }

        return resultForSpecificPatient;

    }

    /*
      * Helping method returning the ID's of all lab test requests of the
      * GNUHealth back-end.
      *
      * @return Array of IDs.
      */
    private int[] getAllLabTestRequestIds() {
        String session = getAdminSession();

        int[] idList;

        // Search Patients
        Object[] params = new Object[]{1, session, new String[]{}, 0, 1000,
                null, "REPLACE_CONTEXT"};

        String result = execute(getLabTestRequestSearchMethod(), params);
        result = result.substring(result.indexOf("[") + 1,
                result.lastIndexOf("]"));

        String[] idListString = result.split(", ");
        idList = new int[idListString.length];

        for (int i = 0; i < idListString.length; i++) {
            idList[i] = parseInt(idListString[i]);
        }
        return idList;
    }


    public LabTestResult returnLabTestResultsDetails(String labTestId) {

        int[] id = new int[]{parseInt(labTestId)};
        String labTestsResult = execute(getLabTestReadMethod(),
                getLabTestsDetailParams(id));

        LabTestResultGnu result = DomainParserGnu.fromJson(labTestsResult,
                LabTestResultGnu.class);
        result.prepareDateFormat();

        String criteriaResultString = execute(getLabTestCriteriaReadMethod(),
                getLabTestCriteriaParams(result.getCritearea()));
        Type listType = new TypeToken<List<LabTestCriteriaGnu>>() {
        }.getType();
        List<LabTestCriteriaGnu> labTestCriteria = DomainParserGnu.fromJson(
                criteriaResultString, listType, LabTestCriteriaGnu.class);

        result.setCritearea(null);
        result.setCriteria(labTestCriteria.toArray());
        return result;
    }

    public List<?> returnAllLabTestResults() {
        int[] labTestResultsIds = getAllLabTestIds();

        String labTestsResultString = execute(getLabTestReadMethod(),
                getLabTestsResultsParams(labTestResultsIds));

        Type listType = new TypeToken<List<LabTestResultGnu>>() {
        }.getType();
        List<LabTestResultGnu> result = DomainParserGnu.fromJson(
                labTestsResultString, listType, LabTestResultGnu.class);

        for (LabTestResultGnu ltrg : result)
            ltrg.prepareDateFormat();

        return result;
    }

    public List<?> returnNewestLabTestResults(String doctor_id) {
    	List<LabTestResultGnu> result = new ArrayList<LabTestResultGnu>();

        String labTestsResultString = execute(getLabTestReadMethod(),
                getLabTestsResultsParams(getAllLabTestIds()));
      
        Type listType = new TypeToken<List<LabTestResultGnu>>() {
        }.getType();
        
        List<LabTestResultGnu> allLabTestResults = DomainParserGnu.fromJson(
                labTestsResultString, listType, LabTestResultGnu.class);
     
        
        /*
         * Filtering a doctors specific lab tests
         */
        List<?> l = checkForTestedLabRequests(doctor_id);
        List<LabTestResultGnu> doctorsLabTestResults = new ArrayList<LabTestResultGnu>();
        for (LabTestResultGnu lbrg : allLabTestResults) {
        	if (lbrg.getRequestor().equals(doctor_id)) doctorsLabTestResults.add(lbrg);
        }
       
        
        /*
         * Searching the newest ones and matching to LabTestRequests
         */
        for (int i = 0; i < l.size(); i++) {
        	LabTestResultGnu tmp = doctorsLabTestResults.get(doctorsLabTestResults.size()-(i+1));
        	tmp.setRequest_id(String.valueOf(l.get(l.size()-(i+1))));
        	result.add(tmp);
        }       
        

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
     * @see eu.neq.mais.connector.Connector#logout(java.lang.String,
     *      java.lang.String)
     */
    public String logout(String username, String session) {
        ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
        String[] params = new String[]{username, session};
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
     * @see eu.neq.mais.connector.Connector#login(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public String login(String username, String password, String backendSid) {
        String[] params = new String[]{username, password};

        ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());


        String result = new Gson().toJson(proxy.call(this.getLoginMethod(),
                params));

        // checks if login was successfull
        if ((result.length() > 5)) {
            char s = '"';
            String session_split[] = result.split(String.valueOf(s));
            result = session_split[1];

            Integer userId = getUserId(username);

            NeqServer.getSessionStore().put(result, backendSid, userId);

            DbHandler dbh = new DbHandler();
            dbh.saveLogin(String.valueOf(userId));
            dbh.close();
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
//            System.out.println("jsonfile: "+jsonfile);

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
     * @see eu.neq.mais.connector.Connector#returnAllPatientsForUIList()
     */
    @Override
    public List<?> returnAllPatientsForUIList() {
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
     *
     */
    @Override
    public List<?> returnPersonalPatientsForUIList(String session)
            throws NoSessionInSessionStoreException {
        String patientListString = "false";
        
        patientListString = execute(getPatientReadMethod(),
                getReturnPatientsParams());

        Type listType = new TypeToken<List<PatientGnu>>() {
        }.getType();
        List<PatientGnu> patientList = DomainParserGnu.fromJson(
                patientListString, listType, PatientGnu.class);
        patientList = addLatestDiagnoseToPatient(patientList);

        int party_id = getPartyId(NeqServer.getSessionStore().getUserId(
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
     * @see eu.neq.mais.connector.Connector#searchForAPatient(java.lang.String)
     */
    @Override
    public List<?> searchForAPatient(String param) {
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
            int id = parseInt(param);

            for (PatientGnu p : patientList) {
                if (valueOf(p.getId()).equals(id))
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
    public UserGnu returnPersonalInformation(String user_id) throws NoSessionInSessionStoreException {
        UserGnu personalInfo = new UserGnu();

        personalInfo.setName(getUserRecName(user_id));
        personalInfo.setPhysician_id(String.valueOf(getPhysicianId(Integer.valueOf(user_id))));
        personalInfo.setId(user_id);
        personalInfo.setImage_url("http://i43.tinypic.com/29lzamh.png");

        return personalInfo;
    }

    @Override
    public List returnVaccinationsForPatient(String patientId) {
        List<VaccinationGnu> result;
        try {
            Object[] patientParam = new Object[]{1, getAdminSession(),
                    new int[]{parseInt(patientId)},
                    new String[]{"vaccinations"}, "REPLACE_CONTEXT"};
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
        Object[] patientParam = new Object[]{1, getAdminSession(),
                new int[]{parseInt(patientID)},
                new String[]{"medications"}, "REPLACE_CONTEXT"};
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
        Object[] params = new Object[]{1, session, new String[]{}, 0, 1000,
                null, "REPLACE_CONTEXT"};

        String result = execute(getPatientSearchMethod(), params);
        // {"id": 55, "result": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]}
        result = result.substring(result.indexOf("[") + 1,
                result.lastIndexOf("]"));

        String[] idListString = result.split(", ");
        idList = new int[idListString.length];

        for (int i = 0; i < idListString.length; i++) {
            idList[i] = parseInt(idListString[i]);
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
        Object[] params = new Object[]{1, session, new String[]{}, 0, 1000,
                null, "REPLACE_CONTEXT"};

        String result = execute(getUserSearchMethod(), params);
        result = result.substring(result.indexOf("[") + 1,
                result.lastIndexOf("]"));

        String[] idListString = result.split(", ");
        idList = new int[idListString.length];

        for (int i = 0; i < idListString.length; i++) {
            idList[i] = parseInt(idListString[i]);
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
        Object[] params = new Object[]{1, session, ids,
                new String[]{"name", "login"}, "REPLACE_CONTEXT"};

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
                return valueOf(u.getId());
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
        Object[] params = new Object[]{1, session, ids,
                new String[]{"name", "login", "rec_name"}, "REPLACE_CONTEXT"};

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

    
    private int[] getAllPhysicianIds() {
        String session = getAdminSession();

        int[] idList;

        // Search Patients
        Object[] params = new Object[]{1, session, new String[]{}, 0, 1000,
                null, "REPLACE_CONTEXT"};

        String result = execute("model.gnuhealth.physician.search", params);
        result = result.substring(result.indexOf("[") + 1,
                result.lastIndexOf("]"));

        String[] idListString = result.split(", ");
        idList = new int[idListString.length];

        for (int i = 0; i < idListString.length; i++) {
            idList[i] = parseInt(idListString[i]);
        }
        return idList;
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
        Object[] params = new Object[]{1, session, new String[]{}, 0, 1000,
                null, "REPLACE_CONTEXT"};

        String result = execute("model.party.party.search", params);
        result = result.substring(result.indexOf("[") + 1,
                result.lastIndexOf("]"));

        String[] idListString = result.split(", ");
        idList = new int[idListString.length];

        for (int i = 0; i < idListString.length; i++) {
            idList[i] = parseInt(idListString[i]);
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
        Object[] params = new Object[]{1, session, new String[]{}, 0, 1000,
                null, "REPLACE_CONTEXT"};

        String result = execute(getLabTestSearchMethod(), params);
        result = result.substring(result.indexOf("[") + 1,
                result.lastIndexOf("]"));

        String[] idListString = result.split(", ");
        idList = new int[idListString.length];

        for (int i = 0; i < idListString.length; i++) {
            idList[i] = parseInt(idListString[i]);
        }
        return idList;
    }
    
    
    public int getPartyId(int user_id) {
        String session = getAdminSession();
        int[] allphys = getAllPartyIds();

        Object[] params = new Object[]{1, session, allphys,
                new String[]{"id", "internal_user"}, "REPLACE_CONTEXT"};

        // Execute search
        String res = execute("model.party.party.read", params);
        // convert to list
        Type listType = new TypeToken<List<UserGnu>>() {
        }.getType();
        List<UserGnu> userList = DomainParserGnu.fromJson(res, listType,
        		UserGnu.class);


        // SEARCH FOR ID
        for (UserGnu u : userList) {
            if (u.getInternal_user() != null) {
                if (Integer.valueOf(u.getInternal_user()) == user_id)
                    return valueOf(u.getId());
            }
        }
        return -1;
    }

    /*
      * Helping method returning the corresponding physician ID of a user.
      *
      * @param user_id
      *
      * @return physician id
      */
    public int getPhysicianId(int user_id) {
        String session = getAdminSession();
        int[] allphys = getAllPhysicianIds();

        Object[] params = new Object[]{1, session, allphys,
                new String[]{"id", "name", "rec_name", "name.internal_user"}, "REPLACE_CONTEXT"};

        // Execute search
        String res = execute("model.gnuhealth.physician.read", params);

        // convert to list
        Type listType = new TypeToken<List<PhysicianGnu>>() {
        }.getType();
        List<PhysicianGnu> userList = DomainParserGnu.fromJson(res, listType,
                PhysicianGnu.class);

        // SEARCH FOR ID
        for (PhysicianGnu u : userList) {
            if (u.getInternal_user() != null) {
                if (Integer.valueOf(u.getInternal_user()) == user_id)
                    return valueOf(u.getId());
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

            String[] params = new String[]{info.getAdmin_user(),
                    info.getAdmin_pw()};

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

    
    private int[] getAllProcedureIds() {
        String session = getAdminSession();

        int[] idList;

        Object[] params = new Object[]{1, session, new String[]{}, 0, 1000,
                null, "REPLACE_CONTEXT"};

        String result = execute(getProcedureSearchMethod(), params);
        result = result.substring(result.indexOf("[") + 1,
                result.lastIndexOf("]"));

        String[] idListString = result.split(", ");
        idList = new int[idListString.length];

        for (int i = 0; i < idListString.length; i++) {
            idList[i] = parseInt(idListString[i]);
        }
        return idList;
    }

    @Override
    public List<?> returnProcedures() {
        int[] procedureIDs = getAllProcedureIds();

        String resultString = execute(getProcedureReadMethod(),
                getProcedureParams(procedureIDs));

        Type listType = new TypeToken<List<ProcedureGnu>>() {
        }.getType();
        List<ProcedureGnu> result = DomainParserGnu.fromJson(
        		resultString, listType, ProcedureGnu.class);

        return result;
    }

    
    private int[] getAllDiseaseIds() {
        String session = getAdminSession();

        int[] idList;

        Object[] params = new Object[]{1, session, new String[]{}, 0, 1000,
                null, "REPLACE_CONTEXT"};

        String result = execute(getPathologySearchMethod(), params);
        result = result.substring(result.indexOf("[") + 1,
                result.lastIndexOf("]"));

        String[] idListString = result.split(", ");
        idList = new int[idListString.length];

        for (int i = 0; i < idListString.length; i++) {
            idList[i] = parseInt(idListString[i]);
        }
        return idList;
    }

    @Override
    public List<?> returnDiseases() {
        int[] diseaseIDs = getAllDiseaseIds();

        String resultString = execute(getPathologyReadMethod(),
                getPathologyParams(diseaseIDs));

        Type listType = new TypeToken<List<DiseaseGnu>>() {
        }.getType();
        List<DiseaseGnu> result = DomainParserGnu.fromJson(
        		resultString, listType, DiseaseGnu.class);

        return result;
    }

  
    

    /*-----  BACKEND METHODS  ----*/
    /* ............................ */

    private String getDocumentReadMethod() {
        return "model.ir.attachment.read";
    }

    private String getDocumentListMethod() {
        return "model.ir.attachment.search";
    }


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

    private String getPatientWriteMethod(){
    	return "model.gnuhealth.patient.write";
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

    private String getLabTestRequestSearchMethod() {
        return "model.gnuhealth.patient.lab.test.search";
    }

    private String getLabTestRequestReadMethod() {
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

    private String getLabTestTypeSearchMethod() {
        return "model.gnuhealth.lab.test_type.search";
    }

    private String getLabTestTypeReadMethod() {
        return "model.gnuhealth.lab.test_type.read";
    }

    private String getLabTestRequestCreateMethod() {
        return "model.gnuhealth.patient.lab.test.create";
    }


    private String getProcedureReadMethod(){
    	return "model.gnuhealth.procedure.read";
    }
    
    private String getProcedureSearchMethod(){
    	return "model.gnuhealth.procedure.search";
    }

    private String getPathologyReadMethod(){
    	return "model.gnuhealth.pathology.read";
    }
    
    private String getPathologySearchMethod(){
    	return "model.gnuhealth.pathology.search";
    }
    
    private String getAppointmentReadMethod(){
    	return "model.gnuhealth.appointment.read";
    }
    
    private String getAppointmentSearchMethod(){
    	return "model.gnuhealth.appointment.search";
    }
        
    
    
    /*-----  BACKEND METHOD PARAMS  ----*/

    private Object[] getAppointmentParams(int[] ids) {
        return new Object[]{
                1,
                getAdminSession(),
                ids,
                new String[]{"appointment_date", "doctor", "appointment_type","urgency","comments", "speciality.rec_name", "patient.rec_name","patient","consultations.rec_name"
                        },
                "REPLACE_CONTEXT"};
    }
    
    private Object[] getPathologyParams(int[] ids) {
        return new Object[]{
                1,
                getAdminSession(),
                ids,
                new String[]{"code", "name", "category.rec_name"
                        },
                "REPLACE_CONTEXT"};
    }

    private Object[] getProcedureParams(int[] ids) {
        return new Object[]{
                1,
                getAdminSession(),
                ids,
                new String[]{"description", "name"
                        },
                "REPLACE_CONTEXT"};
    }
    

    private Object[] getDocumentMetaDataParams(int[] ids, boolean data) {
        String[] params;
        if (data) params = new String[]{"data", "link", "description", "type"};
        else params = new String[]{"link", "description", "type"};
        return new Object[]{
                1,
                getAdminSession(),
                ids,
                params,
                "REPLACE_CONTEXT"};
    }


    private Object[] getDocumentListParams(int[] ids) {
        return new Object[]{
                1,
                getAdminSession(),
                ids,
                //new String[] {//"resource", "=", "gnuhealth.patient", ids.toString()   //or type since data gives all data

                "REPLACE_CONTEXT"};
    }

    private Object[] getDocumentReadParams(int[] ids) {
        return new Object[]{
                1,
                getAdminSession(),
                ids,
                new String[]{"data", "link", "description", "type" //or type since data gives all data
                },
                "REPLACE_CONTEXT"};
    }


    private Object[] getLabTestTypeParams(int[] ids) {
        return new Object[]{1, getAdminSession(), ids,
                new String[]{"id", "code", "name"}, "REPLACE_CONTEXT"};
    }

    private Object[] getLabTestsResultsParams(int[] ids) {
        return new Object[]{
                1,
                getAdminSession(),
                ids,
                new String[]{"id", "date_analysis", "test", "patient", "name",
                        "test.rec_name", "patient.rec_name", "requestor"},
                "REPLACE_CONTEXT"};
    }

    private Object[] getLabTestRequestParams(int[] ids) {
        return new Object[]{
                1,
                getAdminSession(),
                ids,
                new String[]{"date", "patient_id", "state",
                        "doctor_id.rec_name", "name.rec_name", },
                "REPLACE_CONTEXT"};
    }
    
    private Object[] getDiagnoseCreationParams(Map<Object,Object> paramMap){
    	paramMap.put("date_start_treatment",new TimeGnuShort(Long.parseLong((String) paramMap.get("date_start_treatment")))); 
    	paramMap.put("diagnosed_date",new TimeGnuShort(Long.parseLong((String) paramMap.get("diagnosed_date")))); 
    	paramMap.put("healed_date",new TimeGnuShort(Long.parseLong((String) paramMap.get("healed_date")))); 
    	paramMap.put("date_stop_treatment",new TimeGnuShort(Long.parseLong((String) paramMap.get("date_stop_treatment")))); 
    	Object patientId = paramMap.get("patient_id");
    	Object disease_id = paramMap.get("disease_id");
    	
    	paramMap.remove("patient_id");
    	paramMap.remove("disease_id");
    	
        Object[] createContainer = new Object[2];
        createContainer[0] = "create";
        createContainer[1] = paramMap;
       
        Map<Object, Object> addMap = new HashMap<Object, Object>();
        addMap.put("diseases",new Object[]{new Object[]{"add",new Object[]{disease_id}},createContainer});
        return new Object[]{1, getAdminSession(),new Object[]{patientId}, addMap, "REPLACE_CONTEXT"};
    	
    }

    private Object[] getLabTestRequestCreationParams(String date,
                                                     String doctor_id, String name, String patient_id) {
        Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("date", new TimeGnu(new Long(date)));
        paramMap.put("doctor_id", doctor_id);
        paramMap.put("name", name);
        paramMap.put("patient_id", patient_id);
        return new Object[]{1, getAdminSession(), paramMap, "REPLACE_CONTEXT"};
    }

    private Object[] getLabTestsDetailParams(int[] ids) {
        return new Object[]{
                1,
                getAdminSession(),
                ids,
                new String[]{"date_analysis", "test", "patient", "name",
                        "test.rec_name", "patient.rec_name", "date_requested",
                        "requestor.rec_name", "results", "pathologist.rec_name", "critearea",
                        "diagnosis"}, "REPLACE_CONTEXT"};
    }

    private Object[] getLabTestCriteriaParams(int[] ids) {
        return new Object[]{
                1,
                getAdminSession(),
                ids,
                new String[]{"name", "result_text", "remarks", "upper_limit",
                        "lower_limit", "result", "excluded", "units",
                        "warning", "units.rec_name"}, "REPLACE_CONTEXT"};
    }

    private Object[] getMedicationParams(String id) {

        return new Object[]{
                1,
                getAdminSession(),
                new int[]{parseInt(id)},
                new String[]{"course_completed", "discontinued", "dose",
                        "route", "duration_period", "frequency_unit",
                        "dose_unit", "frequency", "indication", "notes",
                        "is_active", "admin_times", "common_dosage",
                        "discontinued_reason", "duration", "form.rec_name",
                        "doctor.rec_name", "route.rec_name",
                        "dose_unit.rec_name", "indication.rec_name",
                        "common_dosage.rec_name", "medicament.rec_name",
                        "start_treatment", "end_treatment"}, "REPLACE_CONTEXT"};
    }

    private Object[] getVaccinationParams(String id) {
        return new Object[]{
                1,
                getAdminSession(),
                new int[]{parseInt(id)},
                new String[]{"dose", "vaccine.rec_name", "observations",
                        "vaccine_lot", "institution.rec_name", "date",
                        "next_dose_date"}, "REPLACE_CONTEXT"};
    }

    private Object[] getReturnPatientsParams() {

        return new Object[]{
                1,
                getAdminSession(),
                getAllPatientIds(),
                new String[]{"rec_name", "age", "diseases", "sex",
                        "primary_care_doctor.name",
                        "primary_care_doctor.rec_name"}, "REPLACE_CONTEXT"};
    }

    private Object[] getReturnDiagnoseParams(String id) {
        return new Object[]{
                1,
                getAdminSession(),
                new int[]{parseInt(id)},
                new String[]{"status", "pregnancy_warning", "is_active",
                        "short_comment", "diagnosed_date", "healed_date",
                        "pathology", "disease_severity", "is_infectious",
                        "is_allergy", "pathology.rec_name",
                        "date_start_treatment", "doctor", "age",
                        "weeks_of_pregnancy", "is_on_treatment",
                        "treatment_description", "extra_info",
                        "date_stop_treatment", "pcs_code", "allergy_type",
                        "doctor.rec_name", "pcs_code.rec_name"},
                "REPLACE_CONTEXT"};
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
