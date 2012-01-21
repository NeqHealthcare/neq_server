package eu.neq.mais.connector.gnuhealth;

import java.util.ArrayList;

import com.google.gson.Gson;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.connector.ConnectorFactory;

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
			System.out.println(">> " + res);

			// FIND id FOR SESSION USER

			int[] id = new int[]{1,2,3};
			
			params = new Object[] { 7, session, id,
					new String[] { "name", "login", "signature" },
					"REPLACE_CONTEXT" };
			res = con.execute("model.res.user.read", params);
			System.out.println(res);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
