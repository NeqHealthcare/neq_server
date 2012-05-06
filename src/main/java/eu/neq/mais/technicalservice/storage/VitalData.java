package eu.neq.mais.technicalservice.storage;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 29.04.12
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */
public class VitalData implements DbTable {


    String request_id;
    String user_id;
    String date;
    Double bmi, temprature, pulse, fluid_balance;


    public static String TABLE_NAME = "VITALDATA_TABLE";
    public static String INDEX_VITALDATA_ITEM_ID = "INDEX_VITALDATA_ITEM_ID";
    public static String FIELD_USER_ID = "user_id";
    public static String FIELD_DATE = "date";
    public static String FIELD_BMI = "bmi";
    public static String FIELD_TEMPRATURE = "temprature";
    public static String FIELD_PULSE = "pulse";
    public static String FIELD_FLUID_BALANCE = "fluid_balance";
    public static String INDEX_USER_ID = "INDEX_USER_ID";

    public VitalData() {
    }

    public VitalData(ISqlJetCursor cursor) {
        this();
        this.read(cursor);
    }

    public void read(ISqlJetCursor cursor) {
        try {
            this.user_id = cursor.getString(FIELD_USER_ID);
            this.date = cursor.getString(FIELD_DATE);
            this.bmi = cursor.getFloat(FIELD_BMI);
            this.temprature = cursor.getFloat(FIELD_TEMPRATURE);
            this.pulse = cursor.getFloat(FIELD_PULSE);
            this.fluid_balance = cursor.getFloat(FIELD_FLUID_BALANCE);

        } catch (SqlJetException e) {
            e.printStackTrace();
        }
    }

    public void initialize(SqlJetDb db) {
        try {

            if (!db.getSchema().getTableNames().contains(VitalData.TABLE_NAME)) {
                db.beginTransaction(SqlJetTransactionMode.WRITE);
                //db.dropTable(TABLE_NAME);
                db.createTable("CREATE TABLE " + TABLE_NAME + " (user_id VARCHAR, " + FIELD_DATE + " INTEGER, " + FIELD_BMI + " DOUBLE, " + FIELD_TEMPRATURE + " DOUBLE, " + FIELD_PULSE + " DOUBLE, " + FIELD_FLUID_BALANCE + " DOUBLE)");
                //bmi DOUBLE, temprature DOUBLE, pulse DOUBLE, fluid_balance DOUBLE
                db.createIndex("CREATE INDEX " + INDEX_VITALDATA_ITEM_ID + " ON " + TABLE_NAME + "(" + FIELD_DATE + ")");
                db.createIndex("CREATE INDEX " + INDEX_USER_ID + " ON " + TABLE_NAME + "(" + FIELD_USER_ID + ")");

                db.commit();

                System.out.println("Creating VitalData Table");
            }
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /* DbHandler dbh = new DbHandler();

        LabTestRequest r = new LabTestRequest();
        r.initialize(dbh.getDb());

        dbh.close();*/
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Double getTemprature() {
        return temprature;
    }

    public void setTemprature(Double temprature) {
        this.temprature = temprature;
    }

    public Double getPulse() {
        return pulse;
    }

    public void setPulse(Double pulse) {
        this.pulse = pulse;
    }

    public Double getFluid_balance() {
        return fluid_balance;
    }

    public void setFluid_balance(Double fluid_balance) {
        this.fluid_balance = fluid_balance;
    }
}
