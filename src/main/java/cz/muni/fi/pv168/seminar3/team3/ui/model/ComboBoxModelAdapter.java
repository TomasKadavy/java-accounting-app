package cz.muni.fi.pv168.seminar3.team3.ui.model;

import javax.swing.ComboBoxModel;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import java.util.Objects;

/**
 * This class adapts {@link ListModel} to {@link ComboBoxModel}. The purpose is to allow
 * creating multiple {@code ComboBoxModels} with independent {@code selectedItem} backed up
 * by the same {@code ListModel}.
 *
 * @param <E> the type of the elements of this model
 */
public class ComboBoxModelAdapter<E> extends AbstractListModel<E> implements ComboBoxModel<E> {

    private final ListModel<E> listModel;
    private Object selectedItem;
    private final E defaultValue;

    public ComboBoxModelAdapter(ListModel<E> listModel, E defaultValue) {
        this.listModel = listModel;
        this.defaultValue = defaultValue;
    }

    @Override
    public int getSize() {
        return listModel.getSize();
    }

    @Override
    public E getElementAt(int index) {
        return listModel.getElementAt(index);
    }

    @Override
    public void addListDataListener(ListDataListener listener) {
        super.addListDataListener(listener);
        listModel.addListDataListener(listener);
    }

    @Override
    public void removeListDataListener(ListDataListener listener) {
        super.removeListDataListener(listener);
        listModel.removeListDataListener(listener);
    }

    @Override
    public Object getSelectedItem() {
        if (selectedItem == null) {
            return defaultValue;
        }
        return selectedItem;
    }

    @Override
    public void setSelectedItem(Object item) {
        if (!Objects.equals(item, selectedItem)) {
            selectedItem = item;
            fireContentsChanged(this, -1, -1);
        }
    }
}