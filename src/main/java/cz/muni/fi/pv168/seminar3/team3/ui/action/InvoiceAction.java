package cz.muni.fi.pv168.seminar3.team3.ui.action;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.InvoiceOperation;
import cz.muni.fi.pv168.seminar3.team3.ui.resources.Icons;

import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


/**
 * Action for creating invoices
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public final class InvoiceAction extends AbstractEntityAction<InvoiceOperation> {

    private static final cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N I18N = new I18N(InvoiceAction.class);

    /**
     * Creates object for creating invoices
     *
     * @param tabbedPane tab context for action
     */
    public InvoiceAction(JTabbedPane tabbedPane) {
        super(InvoiceOperation.class, tabbedPane);
        putValue(NAME, I18N.getString("name"));
        putValue(SMALL_ICON, Icons.INVOICE_ICON.icon);
        putValue(SHORT_DESCRIPTION, I18N.getString("description"));
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getOperation()
                .orElseThrow(() -> new IllegalStateException(I18N.getString("notSupported")))
                .createInvoice();
    }
}
