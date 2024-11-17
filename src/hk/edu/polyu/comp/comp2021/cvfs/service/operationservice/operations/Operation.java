package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

/**
 * <h3>The {@code Operation} Interface</h3>
 * This interface encapsulates operations.
 * <p>
 * An {@code Operation} object represents an operation. Moreover, the {@code Operation} object also records the current application state (a reference to a {@code State} object) for execution. To simplify the logic, in the following text, the {@code Operation} object and the operation it refers to, are interchangeable.
 *
 * @implNote The logic is reasonable because an operation must obtain the current application state when it is being executed, and the state must be up-to-date. For undo or redo operations, they need the application state at the time of the original operation, and two stacks in the {@code OperationRecord} object ensure that the state for undo and redo is correct. However, fundamentally, this is not part of what the {@code Operation} should be responsible for, and it does not contradict our statement that, "an operation must obtain the current state when it is being executed", instead, it is even consistent with it.
 *
 * @see hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.OperationFactory
 * @see hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.OperationRecord
 */
public interface Operation {
    /**
     * Execute the corresponding operation and returns back a status value.
     * <p>
     * Please note that an entity object (e.g., {@code Document} or {@code Directory}) should be created here, instead of the constructor. The {@code exec()} method not only connects the new data to existing data, but also creates the new data.
     * @return information about the successful execution of the operation.
     * @throws OperationCannotExecuteException if the operation cannot be executed.
     */
    public String exec() throws OperationCannotExecuteException;

    /**
     * Validate if the command is valid for creating the operation.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the command is invalid.
     */
    public void commandValidityCheck(String[] command) throws InvalidCommandException;
}
