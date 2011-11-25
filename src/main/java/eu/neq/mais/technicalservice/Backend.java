package eu.neq.mais.technicalservice;

import java.util.List;

public class Backend {
	
	private String uri;
	private long sid;
	private String url;
	private int jsonport;
	private int xmlport;
	private String connector;
	
	
	private List<String> dblist;
	
	
	public String toString(){
		String backendString =  "sid: "+sid+" ; uri: "+uri+" ; url: "+url+" ; jsonport: "+jsonport+" ; xmlport: "+xmlport;
		for(String db : dblist){
			backendString+=" ; dbname: "+db;
		}
		return backendString;
	}


	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}


	public long getSid() {
		return sid;
	}


	public void setSid(long sid) {
		this.sid = sid;
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


	public int getXmlport() {
		return xmlport;
	}


	public void setXmlport(int xmlport) {
		this.xmlport = xmlport;
	}


	public List<String> getDblist() {
		return dblist;
	}


	public void setDblist(List<String> dblist) {
		this.dblist = dblist;
	}


	public String getConnector() {
		return connector;
	}


	public void setConnector(String connector) {
		this.connector = connector;
	}

}
