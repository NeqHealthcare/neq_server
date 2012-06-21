package eu.neq.mais.technicalservice.storage;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class FollowingUser implements DbTable {

	
	String followed_user_id;
    String user_id;

    public static String TABLE_NAME = "FOLLOWING_USER_TABLE";
    public static String FIELD_USER_ID = "user_id";
    public static String FIELD_FOLLOWED_USER_ID = "followed_user_id";
    public static String INDEX_USER_ID = "user_id_index_FollowingUser";
    public static String INDEX_FOLLOWED_USER_ID = "followed_user_id_index_FollowingUser";

    public FollowingUser() {
    }

    public FollowingUser(ISqlJetCursor cursor) {
        this();
        this.read(cursor);
    }

    public void read(ISqlJetCursor cursor) {
        try {
            this.user_id = cursor.getString(FIELD_USER_ID);
            this.followed_user_id = cursor.getString(FIELD_FOLLOWED_USER_ID);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
    }

    public void initialize(SqlJetDb db) {
        try {
            if (!db.getSchema().getTableNames().contains(FollowingUser.TABLE_NAME)) {
                db.beginTransaction(SqlJetTransactionMode.WRITE);
                //db.dropTable(TABLE_NAME);
                db.createTable("CREATE TABLE " + TABLE_NAME + " (user_id VARCHAR, followed_user_id VARCHAR)");
                db.createIndex("CREATE INDEX " + INDEX_FOLLOWED_USER_ID + " ON " + TABLE_NAME + "(" + FIELD_FOLLOWED_USER_ID + ")");
                db.createIndex("CREATE INDEX " + INDEX_USER_ID + " ON " + TABLE_NAME + "(" + FIELD_USER_ID + ")");
                db.commit();
            }
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

    public String getFollowed_user_id() {
        return followed_user_id;
    }

    public void setFollowed_user_id(String request_id) {
        this.followed_user_id = request_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
	
}
