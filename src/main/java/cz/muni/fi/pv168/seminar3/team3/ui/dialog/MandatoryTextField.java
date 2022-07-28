package cz.muni.fi.pv168.seminar3.team3.ui.dialog;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MandatoryTextField extends JTextField implements MandatoryDialogField {

    MandatoryTextField(int columns) {
        super(columns);
    }

    @Override
    public boolean isFilled() {
        return !this.getText().equals("");
    }

    @Override
    public void addOKChangeListener(Runnable function) {
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                function.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                function.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                function.run();
            }
        });
    }
}
