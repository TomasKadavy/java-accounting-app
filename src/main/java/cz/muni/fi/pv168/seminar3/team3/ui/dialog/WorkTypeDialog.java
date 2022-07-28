package cz.muni.fi.pv168.seminar3.team3.ui.dialog;

import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

/**
 * Represents dialog window for WorkType objects
 *
 * @author Vojtech Sykora
 * @since milestone-1
 */
public class WorkTypeDialog extends EntityDialog<WorkType> {

    private final MandatoryTextField nameField = new MandatoryTextField(20);
    private final MandatoryTextField defaultRateField = new MandatoryTextField(20);
    private final WorkType type;

    private static final I18N I18N = new I18N(ProjectDialog.class);

    public WorkTypeDialog(WorkType type, String title) {
        super(title);
        this.type = type;
        nameField.setText(type.toString());
        defaultRateField.setText(Double.toString(type.getDefaultRate()));
        add(I18N.getString("workType"), nameField, 0, 0);
        add(I18N.getString("rate"), defaultRateField, 0, 2);
    }

    @Override
    public void disableComponents() {
        nameField.setEditable(false);
    }

    @Override
    WorkType getEntity() {
        type.setType(nameField.getText());
        type.setDefaultRate(Double.parseDouble(defaultRateField.getText()));
        return type;
    }
}
