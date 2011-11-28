package eu.neq.mais.domain.gnuhealth;

import java.util.HashMap;

import com.google.gson.Gson;

public class GnuHealthJsonObject {
	
	Object[] params;
	int id;
	String method;
	
	public GnuHealthJsonObject(String session, String method, int id) {
		this.params = new Object[]{
				1, session, new String[]{}, 0, 1000, null, "REPLACE_CONTEXT"
				};
		this.id = id;
		this.method = method;
	}
	
	private HashMap<String, Object> getDomain() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		
		hm.put("timezone", "replaceme");
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
		
		String context = "{\"groups\": [1, 3, 4, 2], \"language\": \"en_US\", \"locale\": {\"date\": \"%m/%d/%Y\", \"thousands_sep\": \",\", \"grouping\": [], \"decimal_point\": \".\"}, \"timezone\": null, \"company\": 1, \"language_direction\": \"ltr\"}";

		almost = almost.replace("\"REPLACE_CONTEXT\"", context);
		almost = almost.replace("//", "/");
		almost = almost.replace("\\\\", "\\");

		return almost;
	}

}
