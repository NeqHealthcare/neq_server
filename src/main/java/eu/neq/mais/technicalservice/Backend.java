package eu.neq.mais.technicalservice;

import java.util.List;

public class Backend {
	
	private String name;
	private long id;
	private String url;
	private int jsonport;
	
	
	private List<String> dblist;
	
	
	public String toString(){
		String backendString =  "id: "+id+" ; name: "+name+" ; url: "+url+" ; jsonport: "+jsonport;
		for(String db : dblist){
			backendString+=" ; dbname: "+db;
		}
		return backendString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getJsonport() {
		return jsonport;
	}

	public void setJsonport(int jsonport) {
		this.jsonport = jsonport;
	}

	public List<String> getDblist() {
		return dblist;
	}

	public void setDblist(List<String> dblist) {
		this.dblist = dblist;
	}

}
