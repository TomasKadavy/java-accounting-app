package cz.muni.fi.pv168.seminar3.team3.ui.operation;

import javax.swing.event.ChangeListener;

/**
 * General operation interface
 *
 * @author Vojtech Sykora
 * @since milestone-1
 */
public interface Operation {

    /**
     * Checks whether operation should be enabled or not
     *
     * @return true if operation can be executed,
     *          false otherwise
     */
    boolean isEnabled();

    /**
     * Get short description of operation
     *
     * @return operations description
     */
    String getDescription();

    /**
     * Adds change listener for this operation
     *
     * @param changeListener event listener
     */
    void addOperationEnabledListener(ChangeListener changeListener);

    /**
     * Removes change listener for this operation
     *
     * @param changeListener event listener
     */
    void removeOperationEnabledListener(ChangeListener changeListener);
}
