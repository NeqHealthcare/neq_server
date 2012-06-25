package eu.neq.mais.domain;

import java.util.ArrayList;
import java.util.List;

public class ChatterPost {
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getCreatorName() {
		return creator_name;
	}
	public void setCreatorName(String creatorname) {
		this.creator_name = creatorname;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public List<ChatterPost> getChild_posts() {
		return child_posts;
	}
	public void setChild_posts(List<ChatterPost> child_posts) {
		this.child_posts = child_posts;
	}
	
	public void addChild (ChatterPost child){
		this.child_posts.add(child);
	}
	
	Long id;
    String message;
    Long timestamp;
    String creator_name;
    String image_url;
    List<ChatterPost> child_posts = new ArrayList<ChatterPost>();

    public ChatterPost (Long id, String message, Long timestamp,String creator_name, String image_url){
    	this.id = id;
    	this.message = message;
    	this.timestamp = timestamp;
    	this.creator_name = creator_name;
    	this.image_url = image_url;
    }
    
    public String toString(){
    	String returnS = "id: "+id+" message: "+message+" timestamp: "+timestamp+" creator_name: "+creator_name+" image_url: "+image_url;
    	for(ChatterPost child : child_posts){
    		returnS = System.getProperty(System.getProperty("line.separator"))+"  -------- child "+child.toString();
    	}
    	return returnS;
    }


}
