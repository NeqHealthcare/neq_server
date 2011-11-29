package eu.neq.mais.technicalservice;

import java.util.HashMap;
import java.util.Map;


/**
 * Die Klasse mapped vorläufig alle Sessions zu der passenden backendSid. Da die Session vom Mobile Client
 * sowieso bei jedem Request mitgesendet wird, können wir die URL so sauber halten (keine zusätzliche sid in 
 * der url)
 * 
 * --> das ganze wird später möglicherweise zusammen mit allen anderen Infos in eine Datenbank gepackt...
 * 
 * @author 
 *
 */
public abstract class SessionStore {

	private static Map<String,String> sessionToSid = null;
	
	private static Map<String,String> getInstance(){
		if(sessionToSid == null){
			sessionToSid = new HashMap<String,String>();
		}
		
		return sessionToSid;
	}
	
	/**
	 * add a session as key and a backendSid as value to the sessionstore
	 * 
	 * @param session
	 * @param backendSid
	 */
	public static void put (String session, String backendSid){	
		getInstance().put(session, backendSid);	
	}
	
	/**
	 * returns the backendSid that fits the session
	 */
	public static String getBackendSid(String session){
		return getInstance().get(session);
	}
	
}
