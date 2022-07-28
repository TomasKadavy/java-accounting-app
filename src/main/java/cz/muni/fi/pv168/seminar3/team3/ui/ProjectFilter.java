package cz.muni.fi.pv168.seminar3.team3.ui;

import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.model.Project;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.model.ProjectTableModel;

import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for filtering
 *
 * @author Eduard Stefan Mlynarik
 * @since milestone-2
 */
final class ProjectFilter {

    private final I18N I18N = new I18N(MainWindow.class);
    private final TableRowSorter<ProjectTableModel> rowSorter;

    ProjectFilter(TableRowSorter<ProjectTableModel> rowSorter) {
        this.rowSorter = rowSorter;
    }

    void filter(WorkType selectedWorkType, Client selectedClient, String selectedPeriod) {
        boolean clientCheck = checkClient(selectedClient);
        boolean workTypeCheck = checkWorkType(selectedWorkType);
        boolean periodCheck = checkPeriod(selectedPeriod);
        List<RowFilter<ProjectTableModel, Integer>> filters = new ArrayList<>();
        if (clientCheck && workTypeCheck && periodCheck) {
            filters.add(new ClientRowFilter(selectedClient));
            filters.add(new WorkTypeRowFilter(selectedWorkType));
            filters.add(new PeriodFilter(getStartDate(selectedPeriod)));
        } else if (clientCheck && workTypeCheck) {
            filters.add(new ClientRowFilter(selectedClient));
            filters.add(new WorkTypeRowFilter(selectedWorkType));
        } else if (clientCheck && periodCheck) {
            filters.add(new ClientRowFilter(selectedClient));
            filters.add(new PeriodFilter(getStartDate(selectedPeriod)));
        } else if (workTypeCheck && periodCheck) {
            filters.add(new WorkTypeRowFilter(selectedWorkType));
            filters.add(new PeriodFilter(getStartDate(selectedPeriod)));
        } else if (clientCheck) {
            filters.add(new ClientRowFilter(selectedClient));
        } else if (workTypeCheck) {
            filters.add(new WorkTypeRowFilter(selectedWorkType));
        } else if (periodCheck) {
            filters.add(new PeriodFilter(getStartDate(selectedPeriod)));
        }
        rowSorter.setRowFilter(RowFilter.andFilter(filters));
    }

    private LocalDate getStartDate(String selectedPeriod) {
        LocalDate startDate;
        if (selectedPeriod.equals(I18N.getString("week"))) {
            startDate = LocalDate.now().minusWeeks(1);
        } else if (selectedPeriod.equals(I18N.getString("month"))) {
            startDate = LocalDate.now().minusMonths(1);
        } else if (selectedPeriod.equals(I18N.getString("year"))) {
            startDate = LocalDate.now().minusYears(1);
        } else {
            throw new UnsupportedOperationException("Unsupported time period given.");
        }
        return startDate;
    }

    private boolean checkClient(Client client) {
        if (client == null) {
            return false;
        }
        return !I18N.getString("filterAll").equals(client.getName());
    }

    private boolean checkWorkType(WorkType workType) {
        if (workType == null) {
            return false;
        }
        return !I18N.getString("filterAll").equals(workType.getType());
    }

    private boolean checkPeriod(String period) {
        if (period == null) {
            return false;
        }
        return !I18N.getString("filterAll").equals(period);
    }

    private static class ClientRowFilter extends RowFilter<ProjectTableModel, Integer> {

        private final Client selectedClient;

        private ClientRowFilter(Client selectedClient) {
            this.selectedClient = selectedClient;
        }

        @Override
        public boolean include(Entry<? extends ProjectTableModel, ? extends Integer> entry) {
            ProjectTableModel tableModel = entry.getModel();
            int rowIndex = entry.getIdentifier();
            Project project = tableModel.getEntity(rowIndex);
            return selectedClient.getId().equals(project.getClient().getId());
        }

    }

    private static class WorkTypeRowFilter extends RowFilter<ProjectTableModel, Integer> {

        private final WorkType selectedWorkType;

        private WorkTypeRowFilter(WorkType selectedWorkType) {
            this.selectedWorkType = selectedWorkType;
        }

        @Override
        public boolean include(Entry<? extends ProjectTableModel, ? extends Integer> entry) {
            ProjectTableModel tableModel = entry.getModel();
            int rowIndex = entry.getIdentifier();
            Project project = tableModel.getEntity(rowIndex);
            return selectedWorkType.equals(project.getWorkType());
        }

    }

    private static class PeriodFilter extends RowFilter<ProjectTableModel, Integer> {

        private final LocalDate startDate;

        private PeriodFilter(LocalDate startDate) {
            this.startDate = startDate;
        }

        @Override
        public boolean include(Entry<? extends ProjectTableModel, ? extends Integer> entry) {
            ProjectTableModel tableModel = entry.getModel();
            int rowIndex = entry.getIdentifier();
            Project project = tableModel.getEntity(rowIndex);
            return startDate.compareTo(project.getStartTime()) <= 0;
        }

    }
}