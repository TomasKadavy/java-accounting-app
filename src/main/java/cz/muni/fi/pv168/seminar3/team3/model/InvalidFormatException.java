package cz.muni.fi.pv168.seminar3.team3.model;

/**
 * Exception class for invalid user given inputs
 *
 * @author Vojtěch Sýkora
 * @since milestone-2
 *
 */
public class InvalidFormatException extends RuntimeException {

    public InvalidFormatException(String message) {
        super(message);
    }
}
