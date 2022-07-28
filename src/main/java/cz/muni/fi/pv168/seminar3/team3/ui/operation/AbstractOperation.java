package cz.muni.fi.pv168.seminar3.team3.ui.operation;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * General implementation of operation
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 */
abstract class AbstractOperation implements Operation {

    private final List<ChangeListener> actionEnabledChangeListeners = new ArrayList<>();
    private final String description;

    protected AbstractOperation(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void addOperationEnabledListener(ChangeListener changeListener) {
        actionEnabledChangeListeners.add(changeListener);
    }

    @Override
    public void removeOperationEnabledListener(ChangeListener changeListener) {
        actionEnabledChangeListeners.remove(changeListener);
    }

    protected void fireActionEnabledChange() {
        var event = new ChangeEvent(this);
        actionEnabledChangeListeners.forEach(l -> l.stateChanged(event));
    }
}
