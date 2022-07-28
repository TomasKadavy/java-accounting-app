package cz.muni.fi.pv168.seminar3.team3.ui.dialog;

import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.model.Project;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import javax.swing.ListModel;
import java.time.LocalDate;

/**
 * Factory for ProjectDialog objects
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 */
public class ProjectDialogFactory implements EntityDialogFactory<Project>{

    private static final I18N I18N = new I18N(ProjectDialogFactory.class);

    private final ListModel<Client> clientListModel;
    private final ListModel<WorkType> workTypeListModel;

    public ProjectDialogFactory(ListModel<Client> clientListModel, ListModel<WorkType> workTypeListModel) {
        this.clientListModel = clientListModel;
        this.workTypeListModel = workTypeListModel;
    }

    @Override
    public EntityDialog<Project> newDetailDialog(Project entity) {
        return new ProjectDialog(entity, clientListModel, workTypeListModel, I18N.getString("detail"));
    }

    @Override
    public EntityDialog<Project> newEditDialog(Project entity) {
        return new ProjectDialog(entity, clientListModel, workTypeListModel, I18N.getString("edit"));
    }

    @Override
    public EntityDialog<Project> newAddDialog() {
        return new ProjectDialog(new Project("", Client.EMPTY_CLIENT, WorkType.EMPTY_TYPE,
                LocalDate.now(), 0, 0, ""),
                clientListModel, workTypeListModel, I18N.getString("add"));
    }

}
