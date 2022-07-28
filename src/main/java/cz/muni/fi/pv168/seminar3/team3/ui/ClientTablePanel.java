package cz.muni.fi.pv168.seminar3.team3.ui;

import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.ui.action.GlobalActions;
import cz.muni.fi.pv168.seminar3.team3.ui.dialog.ClientDialogFactory;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.model.ClientTableModel;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.AddOperationImpl;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.DeleteOperationImpl;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.DetailOperationImpl;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.EditOperationImpl;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.EditSupport;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.TableEditSupport;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import javax.swing.table.TableRowSorter;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * JPanel class containing Client table and side panel for filtering
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 */
public class ClientTablePanel extends OperationProviderPanel {

    private static final I18N I18N = new I18N(ClientTablePanel.class);

    public ClientTablePanel(ClientTableModel clientTableModel, GlobalActions actions) {

        var table = new JTable(clientTableModel);
        table.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    actions.getDetailAction().actionPerformed(null);
                }
            }
        } );

        var editSupport = new TableEditSupport<>(table, clientTableModel, new ClientDialogFactory());
        configureOperations(editSupport);

        var rowSorter = new TableRowSorter<>(clientTableModel);
        table.setRowSorter(rowSorter);

        table.setComponentPopupMenu(createProjectTablePopupMenu(actions));

        setLayout(new BorderLayout());

        add(new JScrollPane(table));
    }

    /**
     * Set all allowed operations for client table
     *
     * @param editSupport edit support
     */
    private void configureOperations(EditSupport<Client> editSupport) {
        addOperation(new AddOperationImpl<>(editSupport, I18N.getString("add")));
        addOperation(new DeleteOperationImpl<>(editSupport, I18N.getString("delete")));
        addOperation(new EditOperationImpl<>(editSupport, I18N.getString("edit")));
        addOperation(new DetailOperationImpl<>(editSupport, I18N.getString("detail")));
    }

    /**
     * Create popup menu with allowed actions for client table rows
     *
     * @param globalActions all actions
     * @return popup menu
     */
    private JPopupMenu createProjectTablePopupMenu(GlobalActions globalActions) {
        var menu = new JPopupMenu();
        menu.add(globalActions.getAddAction());
        menu.add(globalActions.getDeleteAction());
        menu.add(globalActions.getEditAction());
        menu.add(globalActions.getDetailAction());
        return menu;
    }

}
