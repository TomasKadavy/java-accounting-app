package cz.muni.fi.pv168.seminar3.team3;

import cz.muni.fi.pv168.seminar3.team3.data.ClientDao;
import cz.muni.fi.pv168.seminar3.team3.data.WorkTypeDao;
import cz.muni.fi.pv168.seminar3.team3.ui.MainWindow;

import cz.muni.fi.pv168.seminar3.team3.data.ProjectDao;
import cz.muni.fi.pv168.seminar3.team3.ui.error.UncaughtExceptionHandler;
import org.apache.derby.jdbc.EmbeddedDataSource;
import javax.swing.JOptionPane;

import javax.sql.DataSource;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The entry point of the application.
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public class Main {

    private Main() {
        throw new AssertionError("This class is not intended for instantiation.");
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            var dataSource = createDataSource();
            var workTypeDao = new WorkTypeDao(dataSource);
            var clientDao = new ClientDao(dataSource);
            var projectDao = new ProjectDao(dataSource, clientDao::findById, workTypeDao::findById);

            initNimbusLookAndFeel();

            EventQueue.invokeAndWait(() -> {
                Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler());
                new MainWindow(projectDao, workTypeDao, clientDao).show();
            });
        }
        catch (InterruptedException e) {
            throw e;
        }
        catch (Exception ex) {
            showError(ex);

        }

    }

    private static DataSource createDataSource() {
        String dbPath = System.getProperty("user.home") + "/pv168/db/project-evidence";
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName(dbPath);
        dataSource.setCreateDatabase("create");
        return dataSource;
    }

    private static void initNimbusLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Nimbus layout initialization failed", ex);
        }
    }

    private static void showError(Exception ex) {
        EventQueue.invokeLater(() -> {
            ex.printStackTrace();
            Object[] options = {
                    new JButton()
            };
            JOptionPane.showOptionDialog(null,
                   "Init failed",
                    "Init failed",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[0]);
        });
    }
}
