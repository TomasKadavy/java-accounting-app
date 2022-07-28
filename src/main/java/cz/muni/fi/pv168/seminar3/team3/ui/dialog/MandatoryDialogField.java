package cz.muni.fi.pv168.seminar3.team3.ui.dialog;


/**
 * Interface for dialog fields, that are mandatory to fill
 *
 * @author VS
 * @since milestone-2
 */
public interface MandatoryDialogField {

    /**
     * Checks whether field is filled (does not need to be filled out correctly)
     *
     * @return true if field is filled out
     */
    boolean isFilled();

    /**
     * Adds change listener for enabling the OK button once all
     * mandatory fields are filled out
     *
     */
    void addOKChangeListener(Runnable function);

}
