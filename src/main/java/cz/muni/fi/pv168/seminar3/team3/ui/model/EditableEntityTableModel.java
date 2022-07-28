package cz.muni.fi.pv168.seminar3.team3.ui.model;


import cz.muni.fi.pv168.seminar3.team3.data.DataAccessObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents editable table models
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 * @param <E> entity
 */
public class EditableEntityTableModel<E> extends AbstractEntityTableModel<E> implements EditableModel<E> {

    private final List<E> rows;
    private final DataAccessObject<E> dataAccessObject;

    public EditableEntityTableModel(List<Column<E, ?>> columns, DataAccessObject<E> dataAccessObject) {
        super(columns);
        this.rows = new ArrayList<>(dataAccessObject.read());
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public void deleteRow(int rowIndex) {
        dataAccessObject.delete(rows.get(rowIndex));
        rows.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    @Override
    public void addRow(E entity) {
        int newRowIndex = rows.size();
        dataAccessObject.create(entity);
        rows.add(entity);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    @Override
    public void updateRow(E entity) {
        dataAccessObject.update(entity);
        int rowIndex = rows.indexOf(entity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    @Override
    public E getEntity(int rowIndex) {
        return rows.get(rowIndex);
    }
}
