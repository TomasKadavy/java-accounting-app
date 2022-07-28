package cz.muni.fi.pv168.seminar3.team3.ui.dialog;

import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

/**
 * Factory for ClientDialog objects
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 */
public class ClientDialogFactory implements EntityDialogFactory<Client>{

    public static final I18N I18N = new I18N(ClientDialogFactory.class);

    @Override
    public EntityDialog<Client> newDetailDialog(Client entity) {
        return new ClientDialog(entity, I18N.getString("detail"));
    }

    @Override
    public EntityDialog<Client> newEditDialog(Client entity) {
        return new ClientDialog(entity, I18N.getString("edit"));
    }

    @Override
    public EntityDialog<Client> newAddDialog() {
        return new ClientDialog(new Client("", "", "", ""), I18N.getString("add"));
    }
}
