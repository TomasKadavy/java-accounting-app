package cz.muni.fi.pv168.seminar3.team3.ui;

import cz.muni.fi.pv168.seminar3.team3.data.ClientDao;
import cz.muni.fi.pv168.seminar3.team3.data.ProjectDao;
import cz.muni.fi.pv168.seminar3.team3.data.WorkTypeDao;
import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.DefaultListModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import cz.muni.fi.pv168.seminar3.team3.ui.action.AddAction;
import cz.muni.fi.pv168.seminar3.team3.ui.action.DeleteAction;
import cz.muni.fi.pv168.seminar3.team3.ui.action.DetailAction;
import cz.muni.fi.pv168.seminar3.team3.ui.action.EditAction;
import cz.muni.fi.pv168.seminar3.team3.ui.action.GlobalActions;
import cz.muni.fi.pv168.seminar3.team3.ui.action.InvoiceAction;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.model.ProjectTableModel;
import cz.muni.fi.pv168.seminar3.team3.ui.model.ClientTableModel;
import cz.muni.fi.pv168.seminar3.team3.ui.model.EntityListModelAdapter;
import cz.muni.fi.pv168.seminar3.team3.ui.model.WorkTypeTableModel;


/**
 * Class for representing main window and also first window in an application
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public class MainWindow {

    private final JFrame frame;

    private final I18N I18N = new I18N(MainWindow.class);

    /**
     * Creates a new object of MainWindow class.
     */
    public MainWindow(ProjectDao projectDao, WorkTypeDao workTypeDao, ClientDao clientDao) {
        frame = createFrame();
        var projectTableModel = new ProjectTableModel(projectDao);
        var clientTableModel = new ClientTableModel(clientDao);
        var clientListModel = new EntityListModelAdapter<>(clientTableModel);
        var workTypeTableModel = new WorkTypeTableModel(workTypeDao);
        var workTypeListModel = new EntityListModelAdapter<>(workTypeTableModel);

        var tabbedPane = new JTabbedPane();

        var globalActions = GlobalActions.builder()
                .addAction(new AddAction(tabbedPane))
                .deleteAction(new DeleteAction(tabbedPane))
                .editAction(new EditAction(tabbedPane))
                .invoiceAction(new InvoiceAction(tabbedPane))
                .detailAction(new DetailAction(tabbedPane))
                .build();

        String[] periods = { I18N.getString("week"), I18N.getString("month"), I18N.getString("year")};
        DefaultListModel<String> periodModel = new DefaultListModel<>();
        periodModel.addAll(List.of(periods));
        ProjectFilterPanel sidePanel = ProjectFilterPanel.builder()
                .addFilter(I18N.getString("filterClients"),
                        clientListModel,
                        new Client(I18N.getString("filterAll"), null, null, null))
                .addFilter(I18N.getString("filterWorkType"),
                        workTypeListModel,
                        new WorkType(I18N.getString("filterAll"), 0))
                .addFilter(I18N.getString("filterPeriod"),
                        periodModel,
                        I18N.getString("filterAll"))
                .build();

        JPanel projectTable = new ProjectTablePanel(projectTableModel, globalActions, sidePanel, clientListModel, workTypeListModel);
        JPanel clientTable = new ClientTablePanel(clientTableModel, globalActions);
        JPanel workTypeTable = new WorkTypeTablePanel(workTypeTableModel, globalActions);

        tabbedPane.addTab(I18N.getString("projectsTab"), projectTable);
        tabbedPane.addTab(I18N.getString("clientsTab"), clientTable);
        tabbedPane.addTab(I18N.getString("workTypesTab"), workTypeTable);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(createToolbar(globalActions), BorderLayout.BEFORE_FIRST_LINE);
        frame.setJMenuBar(createMenuBar(globalActions));
        frame.pack();
    }

    /**
     * Set the visibility of the mainPro window to true.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Creates main window frame with minimal width and height.
     *
     * @return frame
     */
    private JFrame createFrame() {
        var frame = new JFrame(I18N.getString("title"));
        frame.setMinimumSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    /**
     * Creates menu bar
     *
     * @param actions all supported actions for application
     * @return menu bar
     */
    private JMenuBar createMenuBar(GlobalActions actions) {
        var menuBar = new JMenuBar();
        var editMenu = new JMenu(I18N.getString("menuBarEdit"));
        editMenu.setMnemonic('e');
        editMenu.add(actions.getAddAction());
        editMenu.add(actions.getEditAction());
        editMenu.add(actions.getDeleteAction());
        editMenu.add(actions.getDetailAction());
        menuBar.add(editMenu);

        var financesMenu = new JMenu(I18N.getString("menuBarFinances"));
        financesMenu.setMnemonic('f');
        financesMenu.add(actions.getInvoiceAction());
        menuBar.add(financesMenu);

        return menuBar;
    }

    /**
     * Creates toolbar with all supported actions
     *
     * @param actions supported actions
     * @return toolbar
     */
    private JToolBar createToolbar(GlobalActions actions) {
        var toolbar = new JToolBar();
        toolbar.add(actions.getAddAction());
        toolbar.add(actions.getEditAction());
        toolbar.add(actions.getDeleteAction());
        toolbar.add(actions.getDetailAction());
        toolbar.add(actions.getInvoiceAction());
        return toolbar;
    }

}

