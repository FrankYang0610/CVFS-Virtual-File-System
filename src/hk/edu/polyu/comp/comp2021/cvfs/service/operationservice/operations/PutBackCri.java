package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

/**
 * <h3>The {@code PutBackCri} Operation Class</h3>
 * {@code PutBackCri} is the inverse operation of {@code RemoveCri}. Note that this operation does not exist in the instruction document, so it is an internal operation. It should not be exposed externally and hence does not involve checking the existence of object references.
 * @see RemoveCri
 */
public final class PutBackCri implements FileUnrelatedUndoableOperation {
    private final FileSystem fs;

    private final OperationType type;

    private final Criterion criterion;


    /* No constructor for OperationFactory and user commands. */

    /**
     * Construct a new {@code PutBackCri} Operation.
     * <p>
     * Constructor for another {@code Operation} object.
     * @param fs the reference to the file system.
     * @param criterion the reference to the criterion to put back.
     * @param type the type of this operation.
     * @implNote It's guaranteed that file is not {@code null}.
     */
    public PutBackCri(FileSystem fs, Criterion criterion, OperationType type) {
        this.fs = fs;
        this.criterion = criterion;
        this.type = type;
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            fs.addCriterion(criterion);
            return "The criterion has been put back successfully: " + criterion.toString() + ".";
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {}

    @Override
    public boolean isUndoOperation() {
        return type == OperationType.UNDO;
    }

    @Override
    public boolean isRedoOperation() {
        return type == OperationType.REDO;
    }

    @Override
    public Operation getInverseOperation() {
        return new RemoveCri(fs, criterion, type.getInverseType());
    }
}
