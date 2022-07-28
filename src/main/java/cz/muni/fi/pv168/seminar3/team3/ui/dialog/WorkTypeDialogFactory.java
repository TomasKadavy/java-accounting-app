package cz.muni.fi.pv168.seminar3.team3.ui.dialog;

import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

/**
 * Dialog factory for WorkType dialog windows
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 */
public class WorkTypeDialogFactory implements EntityDialogFactory<WorkType>{

    private static final I18N I18N = new I18N(WorkTypeDialogFactory.class);

    @Override
    public EntityDialog<WorkType> newDetailDialog(WorkType entity) {
        return new WorkTypeDialog(entity, I18N.getString("detail"));
    }

    @Override
    public EntityDialog<WorkType> newEditDialog(WorkType entity) {
        return new WorkTypeDialog(entity, I18N.getString("edit"));
    }

    @Override
    public EntityDialog<WorkType> newAddDialog() {
        return new WorkTypeDialog(new WorkType("", 0), I18N.getString("add"));
    }
}
