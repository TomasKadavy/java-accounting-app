package cz.muni.fi.pv168.seminar3.team3.ui.dialog;

import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import javax.swing.JComponent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Dialog window for Clients objects
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 */
public class ClientDialog extends EntityDialog<Client> {

    public static final I18N I18N = new I18N(ClientDialog.class);

    private final MandatoryTextField nameField = new MandatoryTextField(20);
    private final MandatoryTextField phoneNumberField = new MandatoryTextField(20);
    private final MandatoryTextField emailField = new MandatoryTextField(20);
    private final MandatoryTextField crnField = new MandatoryTextField(20);

    private final Client client;

    public ClientDialog(Client client, String title) {
        super(title);
        this.client = client;
        setValues();
        addFields();
        setClientNameLimit();
    }

    @Override
    public void disableComponents() {
        mandatoryFields.forEach( field -> {
            JTextField textField = (JTextField) field;
            textField.setEditable(false);
        });
    }

    private void setValues() {
        nameField.setText(client.getName());
        phoneNumberField.setText(client.getPhoneNumber());
        emailField.setText(client.getEmail());
        crnField.setText(client.getCrn());
    }

    private void addFields() {
        add(I18N.getString("name"), nameField, 0,0);
        add(I18N.getString("phoneNumber"), phoneNumberField, 2, 0);
        add(I18N.getString("email"), emailField, 0,2);
        add(I18N.getString("crn"), crnField, 2,2);
    }

    private void setClientNameLimit() {
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (nameField.getText().length() >= 20) {
                    e.consume();
                }
            }
        });
    }

    @Override
    Client getEntity() {
        client.setName(nameField.getText());
        client.setPhoneNumber(phoneNumberField.getText());
        client.setEmail(emailField.getText());
        client.setCrn(crnField.getText());
        return client;
    }
}
