package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

/**
 * <h3>The {@code FileUnrelatedUndoableOperation} Interface</h3>
 * This is a subset of {@code UndoableOperation}, representing those undoable operations that are not related to file operations. When ejecting the old virtual disk and mounting a new virtual disk, the records of these operations will [not] be deleted.
 * @see UndoableOperation
 */
public interface FileUnrelatedUndoableOperation extends UndoableOperation{}
