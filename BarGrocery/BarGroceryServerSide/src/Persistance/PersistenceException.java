package Persistance;

/**
 * Created by sam on 3/16/2015.
 */
public class PersistenceException extends RuntimeException  {
    private static final long serialVersionUID = 1L;

    public PersistenceException(String msg) {
        super(msg);
    }

    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
