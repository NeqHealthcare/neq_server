package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.Physician;
import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;

/**
 * 
 * @author seba
 *
 */
public class PhysicianGnu implements Physician {
	
	String id;
	
	@MapToGnu("name.internal_user")
	String name_internal_user;
	
	String name;
	String rec_name;
	

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInternal_user() {
		return name_internal_user;
	}
	public void setInternal_user(String internal_user) {
		this.name_internal_user = internal_user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRec_name() {
		return rec_name;
	}
	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}
	
}
