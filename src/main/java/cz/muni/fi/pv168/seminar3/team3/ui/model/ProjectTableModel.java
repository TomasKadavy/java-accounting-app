package cz.muni.fi.pv168.seminar3.team3.ui.model;

import cz.muni.fi.pv168.seminar3.team3.data.DataAccessObject;
import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.model.Project;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import java.time.LocalDate;
import java.util.List;

/**
 * Class for representing table for projects
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public class ProjectTableModel extends EditableEntityTableModel<Project> {

    private static final I18N I18N = new I18N(ProjectTableModel.class);

    private static final List<Column<Project, ?>> COLUMNS = List.of(
            Column.readOnly(I18N.getString("name"), String.class, Project::getName),
            Column.readOnly(I18N.getString("type"), WorkType.class, Project::getWorkType),
            Column.readOnly(I18N.getString("client"), Client.class, Project::getClient),
            Column.readOnly(I18N.getString("start"), LocalDate.class, Project::getStartTime),
            Column.readOnly(I18N.getString("worked"), Integer.class, Project::getHoursWorked),
            Column.readOnly(I18N.getString("rate"), Double.class, Project::getHourRate)
    );

    /**
     * Creates an instance of the class
     */
    public ProjectTableModel(DataAccessObject<Project> projectDao) {
        super(COLUMNS, projectDao);
    }
}
