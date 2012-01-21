package eu.neq.mais.connector.gnuhealth;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;
import eu.neq.mais.domain.gnuhealth.PatientGnu;
import eu.neq.mais.domain.gnuhealth.UserGnu;

public class TGetUserIDFromSession {

	private static Connector instance = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Connector con = ConnectorFactory.getConnector("gnuhealth1");

			// LOGIN
			String session = con.login("seba", "iswi223<<");

			// FIND ALL USERS
			Object[] params = new Object[] { 7, session, new String[] {}, 0,
					1000, null, "REPLACE_CONTEXT" };
			String res = con.execute("model.res.user.search", params);

			// FIND id FOR SESSION USER

			int[] id = new int[]{1,2,3};
			
			params = new Object[] { 7, session, id,
					new String[] { "name", "login" },
					"REPLACE_CONTEXT" };
			
			// Ecevute search
			res = con.execute("model.res.user.read", params);
			System.out.println("res: "+res);
			
			// cleanse json transmission overhead (transaction id, etc..)
			String cleansed = res.substring(res.indexOf("["),res.indexOf("]")+1);
			
			// convert to list
			Type listType = new TypeToken<List<UserGnu>>(){}.getType();
			List<UserGnu> userList = new Gson().fromJson(cleansed, listType);
			
			for (UserGnu u : userList) {
				System.out.println(u.getId() + ":" + u.getLogin());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
