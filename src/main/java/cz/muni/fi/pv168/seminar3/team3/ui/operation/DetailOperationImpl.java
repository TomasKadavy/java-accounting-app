package cz.muni.fi.pv168.seminar3.team3.ui.operation;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import javax.swing.JOptionPane;
import javax.swing.JButton;

/**
 * Detail operation implementation
 *
 * @author Vojtěch Sýkora
 * @param <E> entity class
 */
public class DetailOperationImpl<E> extends AbstractOperation implements DetailOperation {

    private final EditSupport<E> editSupport;

    private static final I18N I18N = new I18N(DetailOperation.class);

    public DetailOperationImpl(EditSupport<E> editSupport, String description) {
        super(description);
        this.editSupport = editSupport;
        this.editSupport.addListSelectionListener(e -> fireActionEnabledChange());
    }

    @Override
    public void show() {
        var selectedRows = editSupport.getSelectedRows();
        if (selectedRows.size() != 1) {
            throw new IllegalStateException(I18N.getString("errorRowCount") + selectedRows.size());
        }
        var entity = editSupport.getEditableModel().getEntity(selectedRows.get(0));
        var detailDialog = editSupport.getDialogFactory().newDetailDialog(entity);
        detailDialog.disableComponents();
        detailDialog.show(editSupport.getParentComponent(), JOptionPane.DEFAULT_OPTION, new JButton[]{new JButton("Cancel")});
    }

    @Override
    public boolean isEnabled() {
        return editSupport.getSelectedRowCount() == 1;
    }
}
