package eu.neq.mais.connector.gnuhealth;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 18.06.12
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
public class GnuMetaData {

    GnuMetaData(int[] ids) {
        this.resource = "gnuhealth.patient," + ids[0];
    }

    boolean company = false;
    String resource;
    // Object _timestamp = new Object() ;
    int[] groups = {1, 3, 4, 2};
    String language = "en_US";
    //LocaleMetaData locale = new LocaleMetaData();
    Object timezone = null;
    //int employee =  1;
    String language_direction = "ltr";
}
