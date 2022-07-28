package cz.muni.fi.pv168.seminar3.team3.ui.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Abstract class for creating tables
 *
 * @param <E> Type of object in table
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public abstract class AbstractEntityTableModel<E> extends AbstractTableModel implements EntityTableModel<E> {

    private final List<Column<E, ?>> columns;

    protected AbstractEntityTableModel(List<Column<E, ?>> columns) {
        this.columns = columns;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var entity = getEntity(rowIndex);
        return getColumn(columnIndex).getValue(entity);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return getColumn(columnIndex).getColumnName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getColumn(columnIndex).getColumnClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return getColumn(columnIndex).isEditable();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        var entity = getEntity(rowIndex);
        getColumn(columnIndex).setValue(value, entity);
    }

    private Column<E, ?> getColumn(int columnIndex) {
        return columns.get(columnIndex);
    }
}
