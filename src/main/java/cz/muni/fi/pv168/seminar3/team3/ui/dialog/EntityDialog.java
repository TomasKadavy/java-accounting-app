package cz.muni.fi.pv168.seminar3.team3.ui.dialog;

import cz.muni.fi.pv168.seminar3.team3.model.InvalidFormatException;
import cz.muni.fi.pv168.seminar3.team3.model.Validable;
import cz.muni.fi.pv168.seminar3.team3.ui.error.ErrorDialog;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Adam Majzlik
 *
 * Represents abstract dialog window
 * @since milestone-1
 */
public abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();
    private final String title;
    private JButton okButton;
    protected final List<MandatoryDialogField> mandatoryFields = new ArrayList<>();

    private static final I18N I18N = new I18N(EntityDialog.class);

    /**
     * Abstract constructor for dialog window
     *
     * @param title dialog window title
     */
    EntityDialog(String title) {
        this.title = title;
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
    }

    /**
     * Adds component to dialog window
     *
     * @param labelText label text
     * @param component component to be added
     * @param x x coordinate of component
     * @param y y coordinate of component
     */
    protected void add(String labelText, JComponent component, int x, int y) {
        if (component instanceof MandatoryDialogField) {
            MandatoryDialogField field = (MandatoryDialogField) component;
            mandatoryFields.add(field);
            field.addOKChangeListener(this::changeOKEnabled);
        }
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        addLabel(labelText, component, constraints);
        addTextField(labelText, component, constraints);

    }

    private void changeOKEnabled() {
        okButton.setEnabled(mandatoryFields.stream().allMatch(MandatoryDialogField::isFilled));
    }

    private void addTextField(String labelText, JComponent component, GridBagConstraints constraints) {
        constraints.gridy++;
        if (labelText.equals("Description")) {
            constraints.gridwidth = 3;
            constraints.gridheight = 3;
            constraints.fill = GridBagConstraints.BOTH;
        }
        if (labelText.equals("Work Type")) {
            constraints.gridwidth = 3;
            constraints.fill = GridBagConstraints.HORIZONTAL;
        }
        panel.add(component, constraints);
    }

    private void addLabel(String labelText, JComponent component, GridBagConstraints constraints) {
        constraints.anchor = GridBagConstraints.LINE_START;

        var label = new JLabel(labelText);
        label.setFont(new Font("Gravitas One", Font.PLAIN, 17));
        label.setLabelFor(component);
        panel.add(label, constraints);
    }

    /**
     * Gives entity
     *
     * @return entity
     */
    abstract E getEntity();

    /**
     * Disables components of dialog window
     */
    public abstract void disableComponents();

    private JOptionPane getOptionPane(JComponent parent) {
        if (!(parent instanceof JOptionPane)) {
            return getOptionPane((JComponent)parent.getParent());
        } else {
            return (JOptionPane) parent;
        }
    }

    /**
     * Displays dialog window
     *
     * @param parentComponent content
     * @param option option type of optionDialog
     * @param options button options
     */
    public Optional<E> show(JComponent parentComponent, int option, JButton[] options) {
        for (JButton button : options) {
            if (button.getText().equals("Ok")) {
                this.okButton = button;
                button.setEnabled(false);
            }
            button.addActionListener(e -> {
                JOptionPane pane = getOptionPane((JComponent)e.getSource());
                if (button.getText().equals("Ok")) {
                    validate(pane, button);
                } else {
                    pane.setValue(button);
                }
            });
        }
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
                option, PLAIN_MESSAGE, null, options, null);
        if (result == OK_OPTION) {
            return Optional.of(getEntity());
        } else {
            return Optional.empty();
        }
    }

    private void validate(JOptionPane pane, JButton button) {
        Validable entity = (Validable) getEntity();
        try {
            entity.validate();
            pane.setValue(button);
        } catch (InvalidFormatException ex) {
            ErrorDialog.show(I18N.getString("error"), ex);
        } catch (NumberFormatException e1) {
            ErrorDialog.show(I18N.getString("numberError"), e1);
        }
    }
}
