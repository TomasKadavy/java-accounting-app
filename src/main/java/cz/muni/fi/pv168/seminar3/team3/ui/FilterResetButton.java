package cz.muni.fi.pv168.seminar3.team3.ui;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import javax.swing.JButton;
import javax.swing.ComboBoxModel;
import java.util.List;

/**
 * Button for reseting filters for given combobox models
 *
 */
public class FilterResetButton extends JButton {

    private final List<ComboBoxModel> models;

    public FilterResetButton(List<ComboBoxModel> models) {
        this.models = models;
        addActionListener(e -> resetFilters());
        I18N I18N = new I18N(FilterResetButton.class);
        setText(I18N.getString("label"));
    }
    private void resetFilters() {
        models.forEach(model -> model.setSelectedItem(null));
    }
}
