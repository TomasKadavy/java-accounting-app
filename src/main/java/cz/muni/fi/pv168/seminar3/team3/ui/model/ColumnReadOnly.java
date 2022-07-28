package cz.muni.fi.pv168.seminar3.team3.ui.model;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import java.util.function.Function;


/**
 * Class for representing Column in a table that is read only
 *
 * @param <E> Type of object that is shown in a table
 * @param <T> Type of value in the column
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 * */
class ColumnReadOnly<E, T> extends Column<E, T> {

    private static final I18N I18N = new I18N(Column.class);

    ColumnReadOnly(String columnName, Class<T> columnClass, Function<E, T> valueGetter) {
        super(columnName, columnClass, valueGetter, null);
    }

    @Override
    void setValue(Object value, E entity) {
        throw new UnsupportedOperationException(I18N.getString("setOnReadOnly") + columnName);
    }

    @Override
    boolean isEditable() {
        return false;
    }


}