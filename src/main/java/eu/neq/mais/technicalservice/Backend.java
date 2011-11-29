package eu.neq.mais.technicalservice;

public class Backend {
	
	private String url;
	private String sid;
	private int jsonport;
	private int xmlport;
	private String connector;
	private String db;
	
	
	public String toString(){
		String backendString =  "sid: "+sid+" ; url: "+url+" ; jsonport: "+jsonport+" ; xmlport: "+xmlport+" ; dbname: "+db;
		return backendString;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getSid() {
		return sid;
	}


	public void setSid(String sid) {
		this.sid = sid;
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
