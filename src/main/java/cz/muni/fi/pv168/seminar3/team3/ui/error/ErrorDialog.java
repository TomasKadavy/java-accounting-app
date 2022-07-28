package cz.muni.fi.pv168.seminar3.team3.ui.error;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


/**
 * Class for displaying errors to user
 *
 * @author Vojtěch Sýkora
 * @since milestone-2
 */
public class ErrorDialog {

    private final JPanel panel = new JPanel();

    private static final I18N I18N = new I18N(ErrorDialog.class);

    private ErrorDialog(String message, Throwable throwable) {
        panel.setLayout(new GridBagLayout());
        var constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        var messageLabel = new JLabel(message);
        messageLabel.setFont(messageLabel.getFont().deriveFont(Font.BOLD));
        panel.add(messageLabel, constraints);

        var messageText = new JLabel(throwable.getMessage());
        panel.add(messageText);
    }

    private void show(Component parentComponent) {
        JOptionPane.showMessageDialog(parentComponent,
                panel,
                I18N.getString("title"),
                JOptionPane.ERROR_MESSAGE);
    }

    public static void show(String message, Throwable throwable) {
        show(message, throwable, null);
    }

    public static void show(String message, Throwable throwable, Component parentComponent) {
        new ErrorDialog(message, throwable).show(parentComponent);
    }

}