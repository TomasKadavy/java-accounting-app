package cz.muni.fi.pv168.seminar3.team3.ui.dialog;

/**
 * Interface for general dialog factory
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 * @param <E> entity type
 */
public interface EntityDialogFactory<E> {

    /**
     * Creates new dialog window object for showing detail of E entity
     *
     * @param entity selected entity
     * @return EntityDialog object
     */
    EntityDialog<E> newDetailDialog(E entity);

    /**
     * Creates new dialog window object for editing type E entity
     *
     * @param entity selected entity
     * @return EntityDialog object
     */
    EntityDialog<E> newEditDialog(E entity);


    /**
     * Creates new dialog window object for adding type E entity
     *
     * @return EntityDialog object
     */
    EntityDialog<E> newAddDialog();
}
