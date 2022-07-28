package cz.muni.fi.pv168.seminar3.team3.ui.model;

import javax.swing.table.TableModel;

/**
 * Table model for specific entity type
 *
 * @author Tomáš Kadavý
 * @since milestone-1
 * @param <E>
 */
public interface EntityTableModel<E> extends TableModel {

    E getEntity(int rowIndex);
}
