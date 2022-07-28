package cz.muni.fi.pv168.seminar3.team3.ui.operation;


import javax.swing.JOptionPane;
import javax.swing.JButton;

/**
 * Add operation implementation
 *
 * @author Vojtěch Sýkora
 * @param <E> entity class
 */
public class AddOperationImpl<E> extends AbstractOperation implements AddOperation {

    private final EditSupport<E> editSupport;

    public AddOperationImpl(EditSupport<E> editSupport, String description) {
        super(description);
        this.editSupport = editSupport;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void add() {
        editSupport.getDialogFactory().newAddDialog()
                .show(editSupport.getParentComponent(), JOptionPane.OK_CANCEL_OPTION, new JButton[]{new JButton("Ok"), new JButton("Cancel")})
                .ifPresent(editSupport.getEditableModel()::addRow);
    }
}
