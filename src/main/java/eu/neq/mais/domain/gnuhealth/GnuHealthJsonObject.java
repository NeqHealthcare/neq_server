package eu.neq.mais.domain.gnuhealth;

import java.util.HashMap;

import com.google.gson.Gson;

public class GnuHealthJsonObject {
	
	Object[] params;
	String method;
	int id;
	
	public GnuHealthJsonObject(String session, String method, int id) {
		this.params = new Object[]{
				1, session, new String[]{}, 0, 1000, null, getDomain()
				};
		this.method = method;
		this.id = id;
	}
	
	private HashMap<String, Object> getDomain() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		
		hm.put("groups", new int[]{1,3,4,2});
		hm.put("language", "en_US");
		hm.put("timezone", null);
		hm.put("company", 1);
		hm.put("language_direction", "ltr");
		
		HashMap<String, Object> locale = new HashMap<String, Object>();
		locale.put("date", "%m/%d/%y");
		locale.put("thousands_sep", ",");
		locale.put("grouping", new String[]{});
		locale.put("decimal_point", ".");
		
		
		hm.put("locale", locale);
		
		return hm;
		
	}
	
	
	
	public String getJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
