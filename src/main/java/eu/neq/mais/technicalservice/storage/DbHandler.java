package eu.neq.mais.technicalservice.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;


public class DbHandler {
	
	public static String LOCATION ="./resources/";
	public static String DB_NAME = "MAIS_INTERNAL_DB";
	private SqlJetDb db;
	
	public DbHandler() {
		File f = new File(LOCATION + DB_NAME);
		try {
			this.db = SqlJetDb.open(f, true);
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		try {
			this.db.close();
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
	}
	
	public Login getLatestLogin(final String user_id) {
		Login result = null;
		
		try {
			return (Login) db.runReadTransaction(new ISqlJetTransaction() {
				
				public Object run(SqlJetDb db) throws SqlJetException {
					ISqlJetTable table = db.getTable(Login.TABLE_NAME);
					ISqlJetCursor cursor = table.lookup(Login.INDEX_USER_ID, user_id);
					
					cursor.last();
					return new Login(cursor);
				}
			});
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean removeLabTestRequest(final String request_id) {
		try {
			db.runWriteTransaction(new ISqlJetTransaction() {
				
				public Object run(SqlJetDb db) throws SqlJetException {
					ISqlJetTable table = db.getTable(LabTestRequest.TABLE_NAME);
					ISqlJetCursor cursor = table.lookup(LabTestRequest.INDEX_REQUEST_ID, request_id);
					cursor.delete();

					return true;
				}
			});
		} catch (SqlJetException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean saveLabTestRequests(final String user_id, final String request_id) {
		
		try {
			db.runWriteTransaction(new ISqlJetTransaction() {
				
				public Object run(SqlJetDb db) throws SqlJetException {
					ISqlJetTable table = db.getTable(LabTestRequest.TABLE_NAME);
					table.insert(user_id, request_id);
					
					return true;
				}
			});
		} catch (SqlJetException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<LabTestRequest> getLabTestRequests(final String user_id) {
		final List<LabTestRequest> result = new ArrayList<LabTestRequest>();
		
		try {
			return (List<LabTestRequest>) db.runReadTransaction(new ISqlJetTransaction() {
				
				public Object run(SqlJetDb db) throws SqlJetException {
					ISqlJetTable table = db.getTable(LabTestRequest.TABLE_NAME);
					ISqlJetCursor cursor = table.lookup(LabTestRequest.INDEX_USER_ID, user_id);
					
					do {
						LabTestRequest tmp = new LabTestRequest();
						tmp.read(cursor);
						result.add(tmp);
						
					} while (cursor.next());
					
					return result;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void saveLogin(final String user_id) {
		try {
			db.runWriteTransaction(new ISqlJetTransaction() {
				
				public Object run(SqlJetDb db) throws SqlJetException {
					ISqlJetTable table = db.getTable(Login.TABLE_NAME);
					table.insert(user_id, System.currentTimeMillis());
					return true;
				}
			});
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DbHandler dbh = new DbHandler();
		System.out.println(dbh.getLatestLogin("1").getDateOfLogin());
	}

	public SqlJetDb getDb() {
		return db;
	}

	public void setDb(SqlJetDb db) {
		this.db = db;
	}
	
	
	
	

	
}
