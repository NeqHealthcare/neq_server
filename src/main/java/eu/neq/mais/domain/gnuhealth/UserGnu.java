package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.User;

/**
 * 
 * @author seba
 *
 */
public class UserGnu implements User {

	String name;
	String id;
	String login;
	String rec_name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getRec_name() {
		return rec_name;
	}
	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}
}
