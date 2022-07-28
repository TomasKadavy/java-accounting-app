package cz.muni.fi.pv168.seminar3.team3.ui.model;

/**
 * Interface for basic editable model
 *
 * @author Vojtěch Sýkora
 * @since milestone-1
 * @param <E> entity
 */
public interface EditableModel<E> {

    void deleteRow(int rowIndex);

    void addRow(E entity);

    void updateRow(E entity);

    E getEntity(int rowIndex);
}
