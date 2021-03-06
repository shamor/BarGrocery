package Persistance;

/**
 * Created by sam on 3/16/2015.
 * * Get the singleton {@link IDatabase} implementation.
 * @return the singleton {@link IDatabase} implementation
 */
public class Database {
    private static final IDatabase theInstance = new RealDatabase();

    public static IDatabase getInstance() {
        return theInstance;
    }

}
