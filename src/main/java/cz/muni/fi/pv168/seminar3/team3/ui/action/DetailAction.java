package cz.muni.fi.pv168.seminar3.team3.ui.action;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.DetailOperation;
import cz.muni.fi.pv168.seminar3.team3.ui.resources.Icons;

import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Action for details of row
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public final class DetailAction extends AbstractEntityAction<DetailOperation>{

    private static final I18N I18N = new I18N(DetailAction.class);

    /**
     * Creates object for details
     * @param tabbedPane tab context for action
     */
    public DetailAction(JTabbedPane tabbedPane) {
        super(DetailOperation.class, tabbedPane);
        putValue(NAME, I18N.getString("name"));
        putValue(SMALL_ICON, Icons.DETAIL_ICON.icon);
        putValue(SHORT_DESCRIPTION, I18N.getString("description"));
        putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Q"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getOperation()
                .orElseThrow(() -> new IllegalStateException(I18N.getString("notSupported")))
                .show();
    }
}
