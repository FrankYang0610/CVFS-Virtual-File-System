package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

/**
 * <h3>The {@code OperationType} Enum</h3>
 *
 * This enum type is specifically for {@code UndoableOperation}, and it does not make sense for other {@code Operations}.
 * It is used to indicate whether an {@code Operation} object is the undo operation and/or redo operation of another operation, or, neither (i.e., a "regular" operation).
 */
public enum OperationType {
    /**
     * Regular operation, i.e., neither undo operation nor redo operation.
     */
    REGULAR,
    /**
     * Undo operation of another operation.
     */
    UNDO,
    /**
     * Redo operation of another operation.
     */
    REDO;

    /**
     * Get the inverse operation type. The rules are as follows:
     * <ul>
     *     <li>The inverse type of {@code REGULAR} is {@code UNDO}</li>
     *     <li>The inverse type of {@code UNDO} is {@code REDO}</li>
     *     <li>The inverse type of {@code REDO} is {@code UNDO}</li>
     * </ul>
     * @return the inverse operation type.
     */
    public OperationType getInverseType() {
        return switch (this) {
            case REGULAR -> UNDO;
            case UNDO -> REDO;
            case REDO -> UNDO;
        };
    }
}
