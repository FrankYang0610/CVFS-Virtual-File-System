package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

/**
 * <h3>The {@code UndoableOperation} Interface</h3>
 * This interface indicates an operation is undoable (i.e., it has an inverse operation).
 *
 * @see Operation
 * @see OperationType
 */
public interface UndoableOperation extends Operation {
    /**
     * Check if this operation is the undo operation of another operation.
     * @return if this operation is the undo operation of another operation.
     */
    public boolean isUndoOperation();

    /**
     * Check if this operation is the redo operation of another operation.
     * @return if this operation is the redo operation of another operation.
     */
    public boolean isRedoOperation();

    /**
     * Get the corresponding inverse operation, i.e., the undo operation.
     * <p>
     * It should be guaranteed that this method shall never be invoked before the {@code exec()} method is invoked.
     * @return the {@code Operation} object that refers to the inverse operation of the current operation.
     */
    public Operation getInverseOperation();
}
