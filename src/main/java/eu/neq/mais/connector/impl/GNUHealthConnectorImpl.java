package eu.neq.mais.connector.impl;

//The Client sessions package
import com.google.gson.Gson;
import com.googlecode.jj1.ServiceProxy;
import com.thetransactioncompany.jsonrpc2.client.*;

//The Base package for representing JSON-RPC 2.0 messages
import com.thetransactioncompany.jsonrpc2.*;

import eu.neq.mais.connector.Connector;
import eu.neq.mais.domain.gnuhealth.GnuMethods;
import eu.neq.mais.technicalservice.Backend;


//For creating URLs
import java.net.*;
import java.util.HashMap;
import java.util.Map;



public class GNUHealthConnectorImpl extends Connector {

	private static Connector instance = null;

	
	public static Connector getInstance(){
		
		if(instance == null){
			instance = new GNUHealthConnectorImpl();
		}
		return instance;
	}



	public void logout(String username, String session) {
		ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
		String[] params =  new String[]{username,session};
		proxy.call(GnuMethods.LOGOUT_METHOD, params);		
	}
	
	
	private URL getBackEndUrl() {
		try {
			return new URL("http://"+this.getBackend().getUrl()+":"+this.getBackend().getJsonport()+"/"+this.getBackend().getDb());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
			return null;
		
	}


	public String login(String username, String password) {
		
		System.out.println("connect to: "+getBackEndUrl().toString());
		
		String[] params =  new String[]{username,password};
		
		ServiceProxy proxy = new ServiceProxy(getBackEndUrl().toString());
		System.out.println("send login request");
		String result = new Gson().toJson(proxy.call(GnuMethods.LOGIN_METHOD, params));
		System.out.println("result: "+result);
		
		return result;		
	}
	
	
	

	public String db_exec(String method, String[] params, String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
