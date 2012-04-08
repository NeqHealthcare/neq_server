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
	String physician_id;
	String image_url;
	
	
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
	public String getPhysician_id() {
		return physician_id;
	}
	public void setPhysician_id(String physician_id) {
		this.physician_id = physician_id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
}
