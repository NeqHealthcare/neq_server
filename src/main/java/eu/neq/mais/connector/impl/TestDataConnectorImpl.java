package eu.neq.mais.connector.impl;

import java.util.ArrayList;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.technicalservice.Backend;

/**
 * Dummy class delivering some test data by simulating a "connection" to a backend.
 * Only useful for frontend/REST service testing.
 * @author seba
 *
 */
public class TestDataConnectorImpl extends Connector {

	private static Connector instance = null;
	
	
	public static Connector getInstance(){
		
		if(instance == null){
			instance = new TestDataConnectorImpl();
		}
		return instance;
	}
	
	public String login(String username, String password) {
		double ran = Math.random();
		System.out.println("Fake Session ID: "+ran);
		return String.valueOf(ran);
	}

	public void logout() {
		// TODO Auto-generated method stub

	}

	public String exec(String method, String[] params, String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
