package cz.muni.fi.pv168.seminar3.team3.ui.model;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Abstract class for representing Column in a table
 *
 * @param <E> Type of object that is shown in a table
 * @param <T> Type of value in the column
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
abstract class Column<E, T> {

    protected final String columnName;
    protected final Class<T> columnClass;
    private final Function<E, T> valueGetter;
    protected final BiConsumer<E, T> valueSetter;

    protected Column(String columnName, Class<T> columnClass, Function<E, T> valueGetter, BiConsumer<E, T> valueSetter) {
        this.columnName = Objects.requireNonNull(columnName, "columnName");
        this.columnClass = Objects.requireNonNull(columnClass, "columnClass");
        this.valueGetter = Objects.requireNonNull(valueGetter, "valueGetter");
        this.valueSetter = valueSetter;
    }

    static <E, T> Column<E, T> readOnly(String columnName, Class<T> columnClass, Function<E, T> valueGetter) {
        return new ColumnReadOnly<>(columnName, columnClass, valueGetter);
    }

    Object getValue(E entity) {
        return valueGetter.apply(entity);
    }

    abstract void setValue(Object value, E entity);

    abstract boolean isEditable();

    String getColumnName() {
        return columnName;
    }

    Class<?> getColumnClass() {
        return columnClass;
    }


}
