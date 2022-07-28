package cz.muni.fi.pv168.seminar3.team3.data;

/**
 * Class for representing exceptions when something goes wrong
 * with the underlying data source
 *
 * @author Tomas Kadavy
 * @since Milestone-2
 */
public class DataAccessException extends RuntimeException{
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
