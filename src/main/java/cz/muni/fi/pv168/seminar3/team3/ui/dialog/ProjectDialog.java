package cz.muni.fi.pv168.seminar3.team3.ui.dialog;

import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.model.Project;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.seminar3.team3.ui.model.LocalDateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.ComboBoxModel;
import javax.swing.JTextField;
import javax.swing.ListModel;


/**
 * @author Adam Majzlik
 *
 * Represents dialog window of project
 * @since milestone-1
 */
public class ProjectDialog extends EntityDialog<Project> {

    private static final I18N I18N = new I18N(ProjectDialog.class);

    private final MandatoryTextField nameField = new MandatoryTextField(20);
    private final MandatoryTextField hoursWorked = new MandatoryTextField(20);
    private final MandatoryTextField hourRateField = new MandatoryTextField(20);
    private final JTextField descriptionField = new JTextField(20);

    private final LocalDateModel startTimeModel = new LocalDateModel();

    private final JDatePicker startTimePicker;

    private final ComboBoxModel<Client> clientModel;
    private final ComboBoxModel<WorkType> workTypeModel;

    private final MandatoryComboBox<Client> clientComboBox;
    private final MandatoryComboBox<WorkType> workTypeComboBox;

    private final Project project;

    /**
     * Creates new instance of ProjectDialog
     *
     * @param project displayed project
     * @param title label for dialog window
     */
    public ProjectDialog(Project project, ListModel<Client> clientModel, ListModel<WorkType> workTypeModel, String title) {
        super(title);
        this.project = project;
        this.clientModel =  new ComboBoxModelAdapter<>(clientModel, Client.EMPTY_CLIENT);
        this.workTypeModel = new ComboBoxModelAdapter<>(workTypeModel, WorkType.EMPTY_TYPE);
        this.clientComboBox = new MandatoryComboBox<>(this.clientModel);
        this.workTypeComboBox = new MandatoryComboBox<>(this.workTypeModel);
        this.workTypeComboBox.addActionListener(actionEvent -> {
            var selectedType = (WorkType) workTypeComboBox.getSelectedItem();
            hourRateField.setText(Double.toString(selectedType.getDefaultRate()));
        });
        this.startTimePicker = new JDatePicker(startTimeModel);
        setValues();
        addFields();
    }

    @Override
    public void disableComponents() {
        mandatoryFields.forEach( field -> {
            if (field instanceof JTextField) {
                JTextField textField = (JTextField) field;
                textField.setEditable(false);
            }
        });

        descriptionField.setEditable(false);
        clientComboBox.setEnabled(false);
        workTypeComboBox.setEnabled(false);
        startTimePicker.setEnabled(false);
    }

    private void setValues() {
        nameField.setText(project.getName());
        workTypeModel.setSelectedItem(project.getWorkType());
        clientModel.setSelectedItem(project.getClient());
        startTimeModel.setValue(project.getStartTime());
        hoursWorked.setText(Integer.toString(project.getHoursWorked()));
        hourRateField.setText(Double.toString(project.getHourRate()));
        descriptionField.setText(project.getDescription());
    }

    private void addFields() {
        add(I18N.getString("name"), nameField, 0, 0);
        add(I18N.getString("client"), clientComboBox, 2, 0);
        add(I18N.getString("workType"), workTypeComboBox, 0, 2);
        add(I18N.getString("start"), startTimePicker, 0, 4);
        add(I18N.getString("hours"), hoursWorked, 1, 4);
        add(I18N.getString("rate"), hourRateField, 2, 4);
        add(I18N.getString("description"), descriptionField, 0, 6);
    }

    @Override
    Project getEntity() {
        project.setName(nameField.getText());
        project.setWorkType((WorkType) workTypeModel.getSelectedItem());
        project.setClient((Client) clientModel.getSelectedItem());
        project.setStartTime(startTimeModel.getValue());
        project.setHoursWorked(Integer.parseInt(hoursWorked.getText()));
        project.setHourRate(Double.parseDouble(hourRateField.getText()));
        project.setDescription(descriptionField.getText());
        return project;
    }
}
