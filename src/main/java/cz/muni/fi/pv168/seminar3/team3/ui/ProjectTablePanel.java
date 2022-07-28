package cz.muni.fi.pv168.seminar3.team3.ui;

import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.model.Project;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.action.GlobalActions;
import cz.muni.fi.pv168.seminar3.team3.ui.dialog.ProjectDialogFactory;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.model.*;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.*;

import javax.swing.JComboBox;
import javax.swing.ListModel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

/**
 * JPanel class containing JTable with projects and filtering options on the left side
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 */
public class ProjectTablePanel extends OperationProviderPanel {

    private static final I18N I18N = new I18N(ProjectTablePanel.class);

    public ProjectTablePanel(ProjectTableModel projectTableModel, GlobalActions actions, ProjectFilterPanel sidePanel,
                             ListModel<Client> clientTableModel, ListModel<WorkType> workTypeTableModel) {

        var table = new JTable(projectTableModel);
        JComboBox<Client> clientBox = sidePanel.getClientFilterComboBox();
        JComboBox<WorkType> workTypeBox = sidePanel.getWorkTypeFilterComboBox();
        JComboBox<String> periodBox = sidePanel.getPeriodFilterComboBox();

        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    actions.getDetailAction().actionPerformed(null);
                }
            }
        } );

        var leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        table.getColumnModel().getColumn(4).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(leftRenderer);

        var editSupport = new TableEditSupport<>(table, projectTableModel,
                new ProjectDialogFactory(clientTableModel, workTypeTableModel));
        configureOperations(editSupport);
        configureFilter(projectTableModel, table, clientBox, workTypeBox, periodBox);

        table.setComponentPopupMenu(createProjectTablePopupMenu(actions));

        setLayout(new BorderLayout());

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(sidePanel),
                new JScrollPane(table));
        add(splitPane, BorderLayout.CENTER);

    }

    /**
     * Set all allowed operations for project table
     *
     * @param editSupport edit support
     */
    private void configureOperations(EditSupport<Project> editSupport) {
        addOperation(new AddOperationImpl<>(editSupport, I18N.getString("add")));
        addOperation(new DeleteOperationImpl<>(editSupport, I18N.getString("delete")));
        addOperation(new EditOperationImpl<>(editSupport, I18N.getString("edit")));
        addOperation(new DetailOperationImpl<>(editSupport, I18N.getString("detail")));
        addOperation(new InvoiceOperationImpl<>(editSupport, I18N.getString("invoice")));
    }

    private void configureFilter(ProjectTableModel projectTableModel, JTable table, JComboBox<Client> clientBox, JComboBox<WorkType> workTypeBox, JComboBox<String> periodBox) {
        var rowSorter = new TableRowSorter<>(projectTableModel);
        var projectFilter = new ProjectFilter(rowSorter);
        table.setRowSorter(rowSorter);
        clientBox.addActionListener(e -> projectFilter.filter((WorkType) workTypeBox.getSelectedItem(), (Client) clientBox.getSelectedItem(), (String) periodBox.getSelectedItem()));
        workTypeBox.addActionListener(e -> projectFilter.filter((WorkType) workTypeBox.getSelectedItem(), (Client) clientBox.getSelectedItem(), (String) periodBox.getSelectedItem()));
        periodBox.addActionListener(e -> projectFilter.filter((WorkType) workTypeBox.getSelectedItem(), (Client) clientBox.getSelectedItem(), (String) periodBox.getSelectedItem()));
    }

    /**
     * Create popup menu with allowed actions for project table rows
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
        menu.add(globalActions.getInvoiceAction());
        return menu;
    }
}
