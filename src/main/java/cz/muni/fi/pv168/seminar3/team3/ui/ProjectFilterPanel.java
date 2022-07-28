package cz.muni.fi.pv168.seminar3.team3.ui;

import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import cz.muni.fi.pv168.seminar3.team3.ui.model.ComboBoxModelAdapter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for filter side panel in projects tab
 *
 * @author Vojtěch Sýkora
 * @since milestone-2
 *
 */
public class ProjectFilterPanel extends JPanel {

    private final I18N I18N = new I18N(MainWindow.class);

    Map<String, JComboBox> comboBoxes;

    /**
     * Creates side panel with filtering options
     *
     * @param filters filtering options
     * @return side panel with comboboxes based on filters
     */
    private ProjectFilterPanel(List<JPanel> filters,
                               Map<String, JComboBox> comboBoxes)
    {
        this.comboBoxes = comboBoxes;
        var boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        var filter = createLabel(I18N.getString("filterLabel"), 20, this.getFont());
        add(filter);
        add(Box.createRigidArea(new Dimension(0, 10)));

        for (JPanel filterOption: filters) {
            add(filterOption);
            add(Box.createRigidArea(new Dimension(0, 25)));
        }
        List<ComboBoxModel> comboBoxModels = new ArrayList<>();
        comboBoxes.forEach((key, value) -> comboBoxModels.add(value.getModel()));

        add(new FilterResetButton(comboBoxModels));

    }

    public JComboBox<Client> getClientFilterComboBox() {
        return comboBoxes.get(I18N.getString("filterClients"));
    }

    public JComboBox<WorkType> getWorkTypeFilterComboBox() {
        return comboBoxes.get(I18N.getString("filterWorkType"));
    }

    public JComboBox<String> getPeriodFilterComboBox() {
        return comboBoxes.get(I18N.getString("filterPeriod"));
    }

    public static ProjectFilterPanelBuilder builder() {
        return new ProjectFilterPanelBuilder();
    }

    /**
     * Builder class for ProjectFilterPanel
     *
     */
    public static class ProjectFilterPanelBuilder {

        private final List<ComboBoxModel> comboBoxModels = new ArrayList<>();
        private final List<JPanel> filters = new ArrayList<>();
        private final Map<String, JComboBox> comboBoxes = new HashMap<>();

        /**
         * Creates a single combobox filter with label wrapped in JPanel.
         *
         * @param labelText label
         * @param model list model of options
         * @return single filtering option
         */
        public <E> ProjectFilterPanelBuilder addFilter(String labelText, ListModel<E> model, E defaultOption) {
            var container = new JPanel();
            var boxLayout = new BoxLayout(container, BoxLayout.Y_AXIS);
            container.setLayout(boxLayout);
            var comboBoxModelAdapter = new ComboBoxModelAdapter<>(model, defaultOption);

            var label = createLabel(labelText, 12, container.getFont());
            container.add(label);

            var comboBox = new JComboBox<>(comboBoxModelAdapter);
            comboBox.setMaximumSize(new Dimension(95, 35));
            comboBox.setAlignmentX( Component.LEFT_ALIGNMENT);
            container.add(comboBox);
            comboBoxes.put(labelText, comboBox);

            this.comboBoxModels.add(comboBoxModelAdapter);
            filters.add(container);
            return this;
        }

        public ProjectFilterPanel build() {
            return new ProjectFilterPanel(filters, comboBoxes);
        }

    }

    /**
     * Creates and aligns label to the left
     *
     * @param labelText text
     * @param fontSize font size
     * @param font font
     * @return left aligned label
     */
    private static JLabel createLabel(String labelText, float fontSize, Font font) {
        var label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(font.deriveFont(fontSize));
        return label;
    }


}
