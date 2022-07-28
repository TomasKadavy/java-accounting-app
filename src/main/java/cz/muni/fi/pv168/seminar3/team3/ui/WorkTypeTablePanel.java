package cz.muni.fi.pv168.seminar3.team3.ui;

import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.action.GlobalActions;
import cz.muni.fi.pv168.seminar3.team3.ui.dialog.WorkTypeDialogFactory;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.model.WorkTypeTableModel;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.*;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;


import java.awt.BorderLayout;


/**
 * JPanel containg work types JTable
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 */
public class WorkTypeTablePanel extends OperationProviderPanel {

    public static final I18N I18N = new I18N(WorkTypeTablePanel.class);

    public WorkTypeTablePanel(WorkTypeTableModel tableModel, GlobalActions actions) {
        var table = new JTable(tableModel);

        var editSupport = new TableEditSupport<>(table, tableModel, new WorkTypeDialogFactory());
        configureOperations(editSupport);

        table.setComponentPopupMenu(createProjectTablePopupMenu(actions));

        setLayout(new BorderLayout());

        add(new JScrollPane(table));
    }

    /**
     * Set all allowed operations for worktype JTable
     *
     * @param editSupport edit support
     */
    private void configureOperations(EditSupport<WorkType> editSupport) {
        addOperation(new AddOperationImpl<>(editSupport, I18N.getString("add")));
        addOperation(new DeleteOperationImpl<>(editSupport, I18N.getString("delete")));
        addOperation(new EditOperationImpl<>(editSupport, I18N.getString("edit")));
        addOperation(new DetailOperationImpl<>(editSupport, I18N.getString("detail")));
    }

    /**
     * Create popup menu with allowed actions for worktype JTable rows
     *
     * @param globalActions all global actions
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
