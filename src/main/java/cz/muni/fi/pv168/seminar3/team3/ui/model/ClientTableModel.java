package cz.muni.fi.pv168.seminar3.team3.ui.model;

import cz.muni.fi.pv168.seminar3.team3.data.DataAccessObject;
import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import java.util.List;

/**
 * Table model for Client objects
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 */
public class ClientTableModel extends EditableEntityTableModel<Client> {

    private static final I18N I18N = new I18N(ClientTableModel.class);

    private static final List<Column<Client, ?>> COLUMNS = List.of(
            Column.readOnly(I18N.getString("name"), String.class, Client::getName),
            Column.readOnly(I18N.getString("phone"), String.class, Client::getPhoneNumber),
            Column.readOnly(I18N.getString("mail"), String.class, Client::getEmail),
            Column.readOnly(I18N.getString("crn"), String.class, Client::getCrn)
    );


    public ClientTableModel(DataAccessObject<Client> clientDao) {
        super(COLUMNS, clientDao);
    }
}
