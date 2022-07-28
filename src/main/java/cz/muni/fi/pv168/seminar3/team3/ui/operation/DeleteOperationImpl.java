package cz.muni.fi.pv168.seminar3.team3.ui.operation;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;

/**
 * Delete operation implementation
 *
 * @author Vojtěch Sýkora
 * @param <E> entity class
 */
public class DeleteOperationImpl<E> extends AbstractOperation implements DeleteOperation {

    private final EditSupport<E> editSupport;

    private static final I18N I18N = new I18N(DeleteOperation.class);

    public DeleteOperationImpl(EditSupport<E> editSupport, String description) {
        super(description);
        this.editSupport = editSupport;
        this.editSupport.addListSelectionListener(e -> fireActionEnabledChange());
    }

    @Override
    public boolean isEnabled() {
        return editSupport.getSelectedRowCount() > 0;
    }

    @Override
    public void delete() {

        UIManager.put("OptionPane.yesButtonText", I18N.getString("yes"));
        UIManager.put("OptionPane.noButtonText", I18N.getString("no"));
        UIManager.put("OptionPane.cancelButtonText", I18N.getString("cancel"));

        List<Integer> selectedRows = editSupport.getSelectedRows();
        int confirmationOption = JOptionPane.showConfirmDialog(editSupport.getParentComponent(), I18N.getString("message"),
                I18N.getString("title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirmationOption == JOptionPane.YES_OPTION) {
            selectedRows.stream()
                    .sorted(Comparator.reverseOrder())
                    .forEach(editSupport.getEditableModel()::deleteRow);
        }
    }

}
