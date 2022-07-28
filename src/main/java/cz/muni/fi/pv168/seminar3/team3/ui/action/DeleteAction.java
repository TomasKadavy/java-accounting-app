package cz.muni.fi.pv168.seminar3.team3.ui.action;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.DeleteOperation;
import cz.muni.fi.pv168.seminar3.team3.ui.resources.Icons;

import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Action for deleting rows
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public final class DeleteAction extends AbstractEntityAction<DeleteOperation>{

    private static final I18N I18N = new I18N(DeleteAction.class);

    /**
     * Create delete action
     *
     * @param tabbedPane tab context for action
     */
    public DeleteAction(JTabbedPane tabbedPane) {
            super(DeleteOperation.class, tabbedPane);
            putValue(NAME, I18N.getString("name"));
            putValue(SMALL_ICON, Icons.DELETE_ICON.icon);
            putValue(MNEMONIC_KEY, KeyEvent.VK_D);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
            putValue(SHORT_DESCRIPTION, I18N.getString("description"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            getOperation()
                    .orElseThrow(() -> new IllegalStateException(I18N.getString("notSupported")))
                    .delete();
        }
}
