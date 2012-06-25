package eu.neq.mais.technicalservice.storage;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 25.06.12
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class DBFacade {
    private static DbHandler dbInstance;

    public synchronized static DbHandler getInstance() {
        if (dbInstance == null) {
            dbInstance = new DbHandler();
        }
        return dbInstance;
    }
}
