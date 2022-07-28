package cz.muni.fi.pv168.seminar3.team3.ui.dialog;


import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import java.util.Objects;

public class MandatoryComboBox<E> extends JComboBox<E> implements MandatoryDialogField {

    MandatoryComboBox(ComboBoxModel<E> model) {
        super(model);
    }
    @Override
    public boolean isFilled() {
        return Objects.nonNull(this.getSelectedItem()) && !this.getSelectedItem().toString().equals("");
    }

    @Override
    public void addOKChangeListener(Runnable function) {
        this.addActionListener(e -> function.run());
    }
}
