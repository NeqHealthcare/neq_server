package eu.neq.mais.technicalservice.storage;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class LabTestRequest implements DbTable {
	

	
	String request_id;
	String user_id;
	
	public static String TABLE_NAME = "REQUEST_TABLE";
	public static String FIELD_USER_ID = "user_id";
	public static String FIELD_REQUEST_ID = "request_id";
	public static String INDEX_USER_ID = "user_id_index_LabTestRequest";
	public static String INDEX_REQUEST_ID = "request_id_index_LabTestRequest";
	
	public LabTestRequest() {}
	
	public LabTestRequest(ISqlJetCursor cursor) {
		this();
		this.read(cursor);
	}
	
	public void read(ISqlJetCursor cursor) {
		try {
			this.user_id = cursor.getString(FIELD_USER_ID);
			this.request_id = cursor.getString(FIELD_REQUEST_ID);
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
	}

	public void initialize(SqlJetDb db) {
		try {
			db.beginTransaction(SqlJetTransactionMode.WRITE);
//			db.dropTable(TABLE_NAME);
//			db.createTable("CREATE TABLE "+TABLE_NAME+" (user_id VARCHAR, request_id VARCHAR)");
//			db.createIndex("CREATE INDEX "+INDEX_REQUEST_ID+" ON "+TABLE_NAME+"("+FIELD_REQUEST_ID+")");
			db.createIndex("CREATE INDEX "+INDEX_USER_ID+" ON "+TABLE_NAME+"("+FIELD_USER_ID+")");
			db.commit();
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DbHandler dbh = new DbHandler();
		
		LabTestRequest r = new LabTestRequest();
		r.initialize(dbh.getDb());
		
		
		
		dbh.close();
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
}
