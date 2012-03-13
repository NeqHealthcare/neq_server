package eu.neq.mais.technicalservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import eu.neq.mais.NeqServer;

/**
 * Die Klasse mapped vorläufig alle Sessions zu der passenden backendSid. Da die
 * Session vom Mobile Client sowieso bei jedem Request mitgesendet wird, können
 * wir die URL so sauber halten (keine zusätzliche sid in der url)
 * 
 * --> das ganze wird später möglicherweise zusammen mit allen anderen Infos in
 * eine Datenbank gepackt...
 * 
 * @author
 * 
 */
public class SessionStore {

	private Map<String, Object[]> sessionToSid = null;

	public Map<String, Object[]> getInstance() {
		if (sessionToSid == null) {
			sessionToSid = new HashMap<String, Object[]>();
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
	public void put(String session, String backendSid, Integer userId) {
		getInstance().put(session, new Object[] { backendSid, userId });
	}

	/**
	 * remove a key/value pair from the session store
	 * 
	 * @param session
	 *            - session that should be removed
	 */
	public void removeKeyValuePair(String session) {
		getInstance().remove(session);
	}

	/**
	 * returns the backendSid that fits the session
	 * 
	 * @throws NoSessionInSessionStoreException
	 * @throws Exception
	 */
	public String getBackendSid(String session)
			throws NoSessionInSessionStoreException {
		session = session.replaceAll(" ", "+");
		session = session.replace("\\\\", "\\");

		if (!getInstance().containsKey(session)) {
			throw new NoSessionInSessionStoreException(session);
		} else {
			return (String) getInstance().get(session)[0];
		}
	}

	/**
	 * returns the userId that fits the session
	 * 
	 * @throws NoSessionInSessionStoreException
	 */
	public Integer getUserId(String session)
			throws NoSessionInSessionStoreException {
		session = session.replaceAll(" ", "+");
		session = session.replace("\\\\", "\\");

		if (!getInstance().containsKey(session)) {
			throw new NoSessionInSessionStoreException(session);
		} else {
			return (Integer) getInstance().get(session)[1];
		}
	}

	public class NoSessionInSessionStoreException extends Throwable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		String error = "";

		public NoSessionInSessionStoreException() {

		}

		NoSessionInSessionStoreException(String e) {
			this.error = e;
		}

		public String toString() {
			return "NoSessionInSessionStoreException: " + this.error;
		}
	}
}
