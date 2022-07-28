package cz.muni.fi.pv168.seminar3.team3.ui.operation;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import javax.swing.JOptionPane;
import javax.swing.JButton;

/**
 * Edit operation implementation
 *
 * @author Vojtěch Sýkora
 * @param <E> entity class
 */
public class EditOperationImpl<E> extends AbstractOperation implements EditOperation {

    private final EditSupport<E> editSupport;

    private static final I18N I18N = new I18N(DetailOperation.class);

    public EditOperationImpl(EditSupport<E> editSupport, String description) {
        super(description);
        this.editSupport = editSupport;
        this.editSupport.addListSelectionListener(e -> fireActionEnabledChange());
    }

    @Override
    public boolean isEnabled() {
        return editSupport.getSelectedRowCount() == 1;
    }

    @Override
    public void edit() {
        var selectedRows = editSupport.getSelectedRows();
        if (selectedRows.size() != 1) {
            throw new IllegalStateException(I18N.getString("errorRowCount") + selectedRows.size());
        }
        var entity = editSupport.getEditableModel().getEntity(selectedRows.get(0));
        editSupport.getDialogFactory().newEditDialog(entity)
                .show(editSupport.getParentComponent(), JOptionPane.OK_CANCEL_OPTION, new JButton[]{new JButton("Ok"), new JButton("Cancel")})
                .ifPresent(editSupport.getEditableModel()::updateRow);
    }
}
