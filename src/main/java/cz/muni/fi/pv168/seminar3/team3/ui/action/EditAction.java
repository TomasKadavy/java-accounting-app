package cz.muni.fi.pv168.seminar3.team3.ui.action;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.EditOperation;
import cz.muni.fi.pv168.seminar3.team3.ui.resources.Icons;

import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Action for editing rows
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public final class EditAction extends AbstractEntityAction<EditOperation> {

    private static final I18N I18N = new I18N(EditAction.class);

    /**
     * Create edit action
     *
     * @param tabbedPane tab context for action
     */
    public EditAction(JTabbedPane tabbedPane) {
        super(EditOperation.class, tabbedPane);
        putValue(NAME, I18N.getString("name"));
        putValue(SMALL_ICON, Icons.EDIT_ICON.icon);
        putValue(SHORT_DESCRIPTION, I18N.getString("description"));
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getOperation()
                .orElseThrow(() -> new IllegalStateException(I18N.getString("notSupported")))
                .edit();
    }
}
