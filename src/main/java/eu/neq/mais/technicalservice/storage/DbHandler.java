package eu.neq.mais.technicalservice.storage;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static java.lang.Long.parseLong;


public class DbHandler {

    public String LOCATION = "./resources/";
    public String DB_NAME = "MAIS_INTERNAL_DB2";
    private SqlJetDb db;

    private Login login;
    private VitalData vitalData;
    private LabTestRequest labTestRequest;
    private FollowingUser followingUser;
    private ChatterPost chatterPost;


    public DbHandler() {

        try {

            //For high speed in memory
            this.db = new SqlJetDb(SqlJetDb.IN_MEMORY, true);
            this.db.open();


            //For persistent storage
            File f = new File(LOCATION + DB_NAME);
            SqlJetDb temp_db = new SqlJetDb(f, true);
            temp_db.open();

            /* db.beginTransaction(SqlJetTransactionMode.WRITE);
                        try {
                            for (String table: temp_db.getSchema().getTableNames())
                            {
                            db.createTable(table);
                            ISqlJetCursor cursor = temp_db.getTable(table).open();
                                while (!cursor.eof())
                                   cursor.

                            }
                                               }


            */


            /*       for (String i: temp_db.getSchema().getTableNames())
            {
                this.db.alterTable(temp_db.getTable(i).getDataBase().;
            }*/


            //this.db.getFile().
            // SqlJetDb.open(f, true);
            //this.insertVitalData("test");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {


            vitalData = new VitalData();
            vitalData.initialize(this.getDb());
            login = new Login();
            login.initialize(this.getDb());

            labTestRequest = new LabTestRequest();
            labTestRequest.initialize(this.getDb());

            followingUser = new FollowingUser();
            followingUser.initialize(this.getDb());

            chatterPost = new ChatterPost();
            chatterPost.initialize(this.getDb());
        }

    }


    public void close() {

        //todo
        //write method that synchronises the in memory db contents with in persistent db
        //File f = new File(LOCATION + DB_NAME);
        // try {
        //SqlJetDb.(f, true).createTable(this.db.);
        //this.db.close();

        /* } catch (SqlJetException e) {
            e.printStackTrace();
        }*/
    }


    public boolean updateFollowingUser(final String user_id, final String followed_user_id, final Boolean isFollowing) {

        try {
            db.runWriteTransaction(new ISqlJetTransaction() {

                public Object run(SqlJetDb db) throws SqlJetException {
                    if (isFollowing) {

                        ISqlJetTable table = db.getTable(FollowingUser.TABLE_NAME);
                        table.insert(user_id, followed_user_id);
                    } else {
                        ISqlJetTable table = db.getTable(FollowingUser.TABLE_NAME);
                        ISqlJetCursor cursor = table.lookup(FollowingUser.INDEX_USER_ID, user_id);

                        try {
                            if (!cursor.eof()) {
                                do {
                                    if (cursor.getString(FollowingUser.FIELD_FOLLOWED_USER_ID).equals(followed_user_id)) {
                                        cursor.delete();
                                    }
                                } while (cursor.next());
                            }
                        } finally {
                            cursor.close();
                        }

                    }


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
    public List<FollowingUser> getFollowingUsers(final String user_id) {
        final List<FollowingUser> result = new ArrayList<FollowingUser>();

        try {
            return (List<FollowingUser>) db.runReadTransaction(new ISqlJetTransaction() {

                public Object run(SqlJetDb db) throws SqlJetException {
                    ISqlJetTable table = db.getTable(FollowingUser.TABLE_NAME);
                    ISqlJetCursor cursor = table.lookup(FollowingUser.INDEX_FOLLOWED_USER_ID, user_id);
                    try {
                        if (!cursor.eof()) {
                            do {
                                FollowingUser tmp = new FollowingUser();
                                tmp.read(cursor);
                                result.add(tmp);

                            } while (cursor.next());
                        }
                    } finally {
                        cursor.close();
                    }

                    return result;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<FollowingUser> getFollowedUsers(final String user_id) {
        final List<FollowingUser> result = new ArrayList<FollowingUser>();

        try {
            return (List<FollowingUser>) db.runReadTransaction(new ISqlJetTransaction() {

                public Object run(SqlJetDb db) throws SqlJetException {
                    ISqlJetTable table = db.getTable(FollowingUser.TABLE_NAME);
                    ISqlJetCursor cursor = table.lookup(FollowingUser.INDEX_USER_ID, user_id);
                    try {
                        if (!cursor.eof()) {
                            do {
                                FollowingUser tmp = new FollowingUser();
                                tmp.read(cursor);
                                result.add(tmp);

                            } while (cursor.next());
                        }
                    } finally {
                        cursor.close();
                    }

                    return result;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public boolean saveChatterPost(final String message, final Long timestamp, final Long parent_id, final String creator_id)

    {

        try {
            db.runWriteTransaction(new ISqlJetTransaction() {

                public Object run(SqlJetDb db) throws SqlJetException {
                    ISqlJetTable table = db.getTable(ChatterPost.TABLE_NAME);
                    table.insert(message, timestamp, parent_id, creator_id);

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
    public List<ChatterPost> getChatterPosts(final Set<String> userIds) {
        final List<ChatterPost> result = new ArrayList<ChatterPost>();

        try {
            return (List<ChatterPost>) db.runReadTransaction(new ISqlJetTransaction() {

                public Object run(SqlJetDb db) throws SqlJetException {
                    ISqlJetTable table = db.getTable(ChatterPost.TABLE_NAME);
                    for (String userId : userIds) {
                        ISqlJetCursor cursor = table.lookup(ChatterPost.INDEX_CREATOR_ID, userId);

                        try {
                            if (!cursor.eof()) {
                                do {
                                    ChatterPost tmp = new ChatterPost();
                                    tmp.read(cursor);
                                    result.add(tmp);

                                } while (cursor.next());
                            }
                        } finally {
                            cursor.close();
                        }

                    }

                    return result;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public Login getLatestLogin(final String user_id) {
        Login result = null;

        try {
            return (Login) db.runReadTransaction(new ISqlJetTransaction() {
                public Object run(SqlJetDb db) throws SqlJetException {
                    ISqlJetTable table = db.getTable(Login.TABLE_NAME);
                    ISqlJetCursor cursor = table.lookup(Login.INDEX_USER_ID, user_id);
                    Login login = null;
                    try {
                        cursor.last();
                        login = new Login(cursor);
                    } finally {
                        cursor.close();
                    }

                    return login;
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
                    try {
                        cursor.delete();
                    } finally {
                        cursor.close();
                    }
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

                    try {
                        if (!cursor.eof()) {
                            do {
                                LabTestRequest tmp = new LabTestRequest();
                                tmp.read(cursor);
                                result.add(tmp);

                            } while (cursor.next());
                        }
                    } finally {
                        cursor.close();
                    }

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

    }

    public SqlJetDb getDb() {
        return db;
    }

    public void setDb(SqlJetDb db) {
        this.db = db;
    }

    /*
    VitalData methods
     */


    public int getUserItemsCount(final String user_id) {

        try {
            return (Integer) db.runReadTransaction(new ISqlJetTransaction() {

                public Object run(SqlJetDb db) throws SqlJetException {
                    ISqlJetTable table = db.getTable(VitalData.TABLE_NAME);


                    ISqlJetCursor cursor = table.lookup(VitalData.INDEX_USER_ID,
                            user_id);
                    int rows = 0;
                    try {
                        rows = (int) cursor.getRowCount();
                    } finally {
                        cursor.close();
                    }

                    return rows;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public List<VitalData> getVitalData(final String user_id, final Calendar startDate, final Calendar endDate) {
        final List<VitalData> result = new ArrayList<VitalData>();
        final Calendar startDate_DB = Calendar.getInstance();
        //startDate_DB.setTime(startDate.getTime());

        final Calendar date_DB = Calendar.getInstance();
        //endDate_DB.setTime(endDate.getTime());


        try {
            return (List<VitalData>) db.runReadTransaction(new ISqlJetTransaction() {

                public Object run(SqlJetDb db) throws SqlJetException {
                    ISqlJetTable table = db.getTable(VitalData.TABLE_NAME);


                    ISqlJetCursor cursor = table.lookup(VitalData.INDEX_VITALDATA_ITEM_ID);
                    // new Object[]{endDate_DB.getTimeInMillis()},
                    //new Object[]{startDate_DB.getTimeInMillis()}
                    try {
                        do {

                            VitalData tmp = new VitalData();
                            tmp.read(cursor);

                            date_DB.setTimeInMillis(parseLong(tmp.getDate()));
                            //System.out.println(date_DB.getTime());

                            if (tmp.getUser_id().equals(user_id) &&
                                    date_DB.before(endDate) &&
                                    date_DB.after(startDate))
                                result.add(tmp);

                        } while (cursor.next());
                    } finally {
                        cursor.close();
                    }

                    return result;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public void insertVitalData(final String user_id) {

        try {
            final eu.neq.mais.domain.gnuhealth.VitalDataGnu vitalDataGnu = new eu.neq.mais.domain.gnuhealth.VitalDataGnu(user_id);
            final Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -50);
            //System.out.println(calendar.getTime());


            db.runWriteTransaction(new ISqlJetTransaction() {

                public Object run(SqlJetDb db) throws SqlJetException {
                    ISqlJetTable table = db.getTable(VitalData.TABLE_NAME);
                    for (int i = 1; i < 50; i++) {
                        vitalDataGnu.generateVitalData();
                        calendar.add(Calendar.DATE, 1);
                        //System.out.println(calendar.getTime());
                        table.insert(user_id, calendar.getTimeInMillis(),
                                vitalDataGnu.getBmi(),
                                vitalDataGnu.getTemprature(),
                                vitalDataGnu.getBlood_pressure(),
                                vitalDataGnu.getFluid_balace()
                        );
                    }
                    return true;
                }
            });


        } catch (SqlJetException e) {
            e.printStackTrace();
        }


    }


}
