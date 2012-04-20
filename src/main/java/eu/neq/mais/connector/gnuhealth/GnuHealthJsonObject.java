package eu.neq.mais.connector.gnuhealth;

import java.util.HashMap;

import com.google.gson.Gson;

/**
 * 
 * 
 * @author Sebastian Schütz
 *
 */
public class GnuHealthJsonObject{
	
	Object[] params;
	int id;
	String method;
	
	public GnuHealthJsonObject(String method, Object[] params, int id) {
		this.params = params;
		this.id = id;
		this.method = method;
	}
	
	private HashMap<String, Object> getDomain() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		
		hm.put("timezone", null); // doesnt convert properly into a JSON object *oh noes*
		
		hm.put("company", 1);
		hm.put("language_direction", "ltr");
		
		HashMap<String, Object> locale = new HashMap<String, Object>();
		locale.put("date", "%m/%d/%y");
		locale.put("thousands_sep", ",");
		locale.put("grouping", new String[]{});
		locale.put("decimal_point", ".");

		hm.put("locale", locale);
		
		hm.put("language", "en_US");
		hm.put("groups", new int[]{1,3,4,2});
		
		System.out.println("timezone: "+hm.get(("timezone").toString()));
		
		return hm;
		
	}
	
	public String getJson() {
		Gson gson = new Gson();
		String almost = gson.toJson(this);
		
		String context = "{\"timestamp\": {}, \"groups\": [], \"language\": \"en_US\", \"locale\": {\"date\": \"%m/%d/%Y\", \"thousands_sep\": \",\", \"grouping\": [], \"decimal_point\": \".\"}, \"timezone\": null, \"company\": 1, \"language_direction\": \"ltr\"}";
		
//		String context ="{\"_timestamp\": {\"gnuhealth.patient.medication,12\": \"1331200985.01991\", \"gnuhealth.patient.disease,-2\": null, \"gnuhealth.patient.medication,11\": \"1331200984.99994\", \"gnuhealth.vaccination,8\": \"1331205934.87094\", \"gnuhealth.patient,14\": \"1331205934.86282\", \"gnuhealth.patient.disease,23\": \"1331041078.36183\"}, \"language\": \"en_US\", \"groups\": [1], \"employee\": 1, \"timezone\": null, \"company\": 1, \"language_direction\": \"ltr\"}";
//		String context ="{\"timestamp\": {\"gnuhealth.patient.disease,-2\": null, \"gnuhealth.patient,20\": \"1334702429.57009\"}, \"language\": \"en_US\", \"groups\": [1], \"employee\": 1, \"timezone\": null, \"company\": 1, \"language_direction\": \"ltr\"}";	
//		String context ="{\"timestamp\": {}, \"language\": \"en_US\", \"groups\": [1], \"employee\": 1, \"timezone\": null, \"company\": 1, \"language_direction\": \"ltr\"}";
		
//		String context = "{\"_timestamp\": {\"gnuhealth.patient.medication,12\": \"1331200985.01991\", \"gnuhealth.patient.medication,11\": \"1331200984.99994\", \"gnuhealth.vaccination,8\": \"1331205934.87094\", \"gnuhealth.patient,14\": \"1334705342.23084\", \"gnuhealth.patient.disease,23\": \"1334705342.23775\", \"gnuhealth.patient.disease,-3\": null}, \"language\": \"en_US\", \"groups\": [1], \"employee\": 1, \"timezone\": null, \"company\": 1, \"language_direction\": \"ltr\"}";
		
		
		almost = almost.replace("\"REPLACE_CONTEXT\"", context);
		almost = almost.replace("//", "/");
		almost = almost.replace("\\\\", "\\");
//		System.out.println("result from gnuhealthjsonobject getJson method: "+almost);
		return almost;
	}


}
