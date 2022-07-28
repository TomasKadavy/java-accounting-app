package cz.muni.fi.pv168.seminar3.team3.ui;

import cz.muni.fi.pv168.seminar3.team3.ui.operation.Operation;
import cz.muni.fi.pv168.seminar3.team3.ui.operation.OperationProvider;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OperationProviderPanel extends JPanel implements OperationProvider {

    private final List<Operation> operations = new ArrayList<>();

    protected void addOperation(Operation operation) {
        operations.add(operation);
    }

    @Override
    public <O extends Operation> Optional<O> getOperation(Class<O> operationType) {
        for (Operation operation : operations) {
            if (operationType.isAssignableFrom(operation.getClass())) {
                return Optional.of(operationType.cast(operation));
            }
        }
        return Optional.empty();
    }
}
