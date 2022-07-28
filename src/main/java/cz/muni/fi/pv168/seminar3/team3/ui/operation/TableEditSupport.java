package cz.muni.fi.pv168.seminar3.team3.ui.operation;

import cz.muni.fi.pv168.seminar3.team3.ui.dialog.EntityDialogFactory;
import cz.muni.fi.pv168.seminar3.team3.ui.model.EditableModel;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TableEditSupport<E> extends EditSupport<E> {

    private final JTable table;

    public TableEditSupport(JTable table, EditableModel<E> editableModel, EntityDialogFactory<E> dialogFactory) {
        super(editableModel, dialogFactory, table.getSelectionModel());
        this.table = table;
    }

    @Override
    public List<Integer> getSelectedRows() {
        if (table.isEditing()) {
            table.getCellEditor().cancelCellEditing();
        }
        return Arrays.stream(table.getSelectedRows())
                .map(table::convertRowIndexToModel)
                .boxed()
                .collect(Collectors.toList());

    }

    @Override
    public JComponent getParentComponent() {
        return table;
    }
}
