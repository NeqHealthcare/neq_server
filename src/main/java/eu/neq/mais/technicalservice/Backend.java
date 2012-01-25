package eu.neq.mais.technicalservice;

import eu.neq.mais.connector.Connector;

public class Backend {
	
	private String url;
	private String sid;
	private int jsonport;
	private int xmlport;
	private String connector;
	private String db;
	private String admin_user;
	private String admin_pw;
	
	private Connector connectorImpl;
	
	
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


	public String getAdmin_user() {
		return admin_user;
	}


	public void setAdmin_user(String admin_user) {
		this.admin_user = admin_user;
	}


	public String getAdmin_pw() {
		return admin_pw;
	}


	public void setAdmin_pw(String admin_pw) {
		this.admin_pw = admin_pw;
	}


	public Connector getConnectorImpl() {
		return connectorImpl;
	}


	public void setConnectorImpl(Connector connectorImpl) {
		this.connectorImpl = connectorImpl;
	}

}
