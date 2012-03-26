package eu.neq.mais.technicalservice.storage;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.SqlJetDb;


public class Login implements DbTable {
	
	String userId;
	long dateOfLogin;
	
	public static String TABLE_NAME = "LOGIN_TABLE";
	public static String FIELD_USER_ID = "user_id";
	public static String FIELD_DATE_OF_LOGIN = "date_of_login";
	public static String INDEX_USER_ID = "user_id_index";
	
	public Login() {}
	public Login(ISqlJetCursor cursor) {
		this();
		this.read(cursor);
	}
	
	public void initialize(SqlJetDb db) {
		try {
			db.beginTransaction(SqlJetTransactionMode.WRITE);
			db.createTable("CREATE TABLE LOGIN_TABLE (user_id VARCHAR, date_of_login INTEGER)");
			db.createIndex("CREATE INDEX user_id_index ON LOGIN_TABLE(user_id)");
			db.commit();
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
	
	}
	
	public void read(ISqlJetCursor cursor) {
		try {
			this.userId = cursor.getString(FIELD_USER_ID);
			this.dateOfLogin = cursor.getInteger(FIELD_DATE_OF_LOGIN);
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getDateOfLogin() {
		return dateOfLogin;
	}

	public void setDateOfLogin(long dateOfLogin) {
		this.dateOfLogin = dateOfLogin;
	}



}
