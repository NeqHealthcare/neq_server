package eu.neq.mais.technicalservice.storage;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class ChatterPost implements DbTable {

	
	Long id;
    String message;
    Long timestamp;
    Long parent_id;
    String creator_id;
    

    public static String TABLE_NAME = "CHATTER_POST_TABLE";
    public static String FIELD_ID = "id";
    public static String FIELD_MESSAGE = "message";
    public static String FIELD_TIMESTAMP = "timestamp";
    public static String FIELD_PARENT_ID = "parent_id";
    public static String FIELD_CREATOR_ID = "creator_id";
       
    public static String INDEX_ID = "sqlite_autoindex_CHATTER_POST_TABLE_1";
    public static String INDEX_PARENT_ID = "parent_id_index_ChatterPost";
    public static String INDEX_CREATOR_ID = "creator_id_index_ChatterPost";
    

    public ChatterPost() {
    }

    public ChatterPost(ISqlJetCursor cursor) {
        this();
        this.read(cursor);
    }

    public void read(ISqlJetCursor cursor) {
        try {
        	this.id = cursor.getInteger(FIELD_ID);
            this.message = cursor.getString(FIELD_MESSAGE);
            this.timestamp = cursor.getInteger(FIELD_TIMESTAMP);
            this.parent_id = cursor.getInteger(FIELD_PARENT_ID);
            this.creator_id = cursor.getString(FIELD_CREATOR_ID);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
    }

    public void initialize(SqlJetDb db) {
        try {
            if (!db.getSchema().getTableNames().contains(TABLE_NAME)) {
                db.beginTransaction(SqlJetTransactionMode.WRITE);
                //db.dropTable(TABLE_NAME);
                db.createTable("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT,message VARCHAR,timestamp INTEGER,parent_id INTEGER, creator_id VARCHAR)");
                db.createIndex("CREATE INDEX " + INDEX_PARENT_ID + " ON " + TABLE_NAME + "(" + FIELD_PARENT_ID + ")");
                db.createIndex("CREATE INDEX " + INDEX_CREATOR_ID + " ON " + TABLE_NAME + "(" + FIELD_CREATOR_ID + ")");
                db.commit();
            }
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DbHandler dbh = new DbHandler();

        ChatterPost r = new ChatterPost();
        r.initialize(dbh.getDb());


        dbh.close();
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

}
