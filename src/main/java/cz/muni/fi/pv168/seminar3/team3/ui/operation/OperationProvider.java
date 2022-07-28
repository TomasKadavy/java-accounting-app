package cz.muni.fi.pv168.seminar3.team3.ui.operation;

import java.util.Optional;

/**
 * Interface for classes, that want to provide operations
 *
 */
public interface OperationProvider {

    /**
     * Getter for operation
     *
     * @param operationType type of operation
     * @param <O> operation
     * @return
     */
    <O extends Operation> Optional<O> getOperation(Class<O> operationType);
}
