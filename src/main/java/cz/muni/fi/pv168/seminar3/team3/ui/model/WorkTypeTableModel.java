package cz.muni.fi.pv168.seminar3.team3.ui.model;

import cz.muni.fi.pv168.seminar3.team3.data.DataAccessObject;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import java.util.List;

/**
 * Class for representing work type table
 *
 * @author Tomas Kadavy
 * @since Milestone-1
 */
public class WorkTypeTableModel extends EditableEntityTableModel<WorkType>{

    private static final I18N I18N = new I18N(ProjectTableModel.class);

    private static final List<Column<WorkType, ?>> COLUMNS = List.of(
            Column.readOnly(I18N.getString("name"), String.class, WorkType::toString)
    );

    public WorkTypeTableModel(DataAccessObject<WorkType> workTypesDao) {
        super(COLUMNS, workTypesDao);
    }

}
