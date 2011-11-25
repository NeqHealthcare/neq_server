package eu.neq.mais.technicalservice;

import java.util.List;

public class Backend {
	
	private String uri;
	private long sid;
	private String url;
	private int jsonport;
	private int xmlport;
	private String connector;
	private String db;
	
	
	public String toString(){
		String backendString =  "sid: "+sid+" ; uri: "+uri+" ; url: "+url+" ; jsonport: "+jsonport+" ; xmlport: "+xmlport+" ; dbname: "+db;
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


	public String getDb() {
		return db;
	}


	public void setDb(String db) {
		this.db = db;
	}


	public String getConnector() {
		return connector;
	}


	public void setConnector(String connector) {
		this.connector = connector;
	}

}
