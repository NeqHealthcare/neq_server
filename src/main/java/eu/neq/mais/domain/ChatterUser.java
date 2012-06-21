package eu.neq.mais.domain;

import eu.neq.mais.domain.gnuhealth.annotations.MapToGnu;

public class ChatterUser {

	String id;
	String rec_name;
	String image_url;
	boolean isFollowed = false;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getRec_name() {
		return rec_name;
	}
	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}

	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String toString(){
		return "id: "+id+"  rec_name: "+rec_name+"  image_url: "+image_url+"   isFollowed: "+isFollowed;
	}

}
