package cz.muni.fi.pv168.seminar3.team3.ui.operation;

import cz.muni.fi.pv168.seminar3.team3.ui.dialog.EntityDialogFactory;
import cz.muni.fi.pv168.seminar3.team3.ui.model.EditableModel;

import javax.swing.ListSelectionModel;
import javax.swing.JComponent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public abstract class EditSupport<E> {

    private final EditableModel<E> editableModel;
    private final EntityDialogFactory<E> dialogFactory;
    private final ListSelectionModel listSelectionModel;

    public EditSupport(EditableModel<E> editableModel, EntityDialogFactory<E> dialogFactory, ListSelectionModel listSelectionModel) {
        this.editableModel = editableModel;
        this.dialogFactory = dialogFactory;
        this.listSelectionModel = listSelectionModel;
    }

    public EditableModel<E> getEditableModel() {
        return editableModel;
    }

    public EntityDialogFactory<E> getDialogFactory() {
        return dialogFactory;
    }

    public abstract List<Integer> getSelectedRows();

    public int getSelectedRowCount() {
        return listSelectionModel.getSelectedItemsCount();
    }

    public void addListSelectionListener(ListSelectionListener listSelectionListener) {
        listSelectionModel.addListSelectionListener(listSelectionListener);
    }

    public abstract JComponent getParentComponent();
}
