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
					
		String context = "{\"_timestamp\": {}, \"groups\": [], \"language\": \"en_US\", \"locale\": {\"date\": \"%m/%d/%Y\", \"thousands_sep\": \",\", \"grouping\": [], \"decimal_point\": \".\"}, \"timezone\": null, \"company\": 1,\"employee\": 1, \"language_direction\": \"ltr\"}";
		
		almost = almost.replace("\"REPLACE_CONTEXT\"", context);
		almost = almost.replace("//", "/");
		almost = almost.replace("\\\\", "\\");

//		System.out.println("almost end: "+almost);
//		System.out.println("----");
		return almost;
	}


}
