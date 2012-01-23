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

	private static Map<String,Object[]> sessionToSid = null;
	
	private static Map<String,Object[]> getInstance(){
		if(sessionToSid == null){
			sessionToSid = new HashMap<String,Object[]>();
		}
		
		return sessionToSid;
	}
	
	/**
	 * add a session as key and a backendSid as value to the sessionstore
	 * 
	 * @param session
	 * @param backendSid
	 * @param userId 
	 */
	public static void put (String session, String backendSid, Integer userId){	
		getInstance().put(session, new Object[]{backendSid,userId});	
	}
	
	/**
	 * remove a key/value pair from the session store
	 * 
	 * @param session - session that should be removed
	 */
	public static void removeKeyValuePair(String session){
		getInstance().remove(session);
	}
	/**
	 * returns the backendSid that fits the session
	 */
	public static String getBackendSid(String session){
		return (String) getInstance().get(session)[0];
	}

	/**
	 * returns the userId that fits the session
	 */
	public static Integer getUserId(String session){
		return (Integer) getInstance().get(session)[1];
	}
}
