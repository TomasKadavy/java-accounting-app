package cz.muni.fi.pv168.seminar3.team3.model;

/**
 * Interface for representing objects, that should be validated before usage
 *
 * @author VS
 * @since milestone-2
 *
 */
public interface Validable {

    /**
     * Validate object
     *
     */
    void validate() throws InvalidFormatException;
}
