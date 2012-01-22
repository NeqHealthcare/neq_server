package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.Physician;

/**
 * 
 * @author seba
 *
 */
public class PhysicianGnu implements Physician {
	
	String id;
	String internal_user;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInternal_user() {
		return internal_user;
	}
	public void setInternal_user(String internal_user) {
		this.internal_user = internal_user;
	}
	
}
