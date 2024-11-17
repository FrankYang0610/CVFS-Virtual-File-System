package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

/**
 * <h3>The {@code RemoveCri} Operation Class</h3>
 * This class has two purposes. First, This class encapsulates the operation of the {@code deleteCri} command, and second, it is the inverse operation of {@code PutBackCri}. The {@code removeCri} command isn't mentioned in the Instruction Document, but we consider it necessary. It's helpful for debugging and not difficult to implement.
 * @see PutBackCri
 */
public final class RemoveCri implements FileUnrelatedUndoableOperation {
    private final FileSystem fs;

    private final OperationType type;

    /**
     * The name of the criterion to delete.
     */
    private final String name;

    /**
     * The criterion to remove.
     */
    private Criterion criterion;


    /**
     * Construct a new {@code RemoveCri} Operation.
     * <p>
     * Constructor for the {@code OperationFactory} and for the {@code deleteCri} command.
     * <p>
     * User command: {@code deleteCri <criName>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public RemoveCri(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        type = OperationType.REGULAR;
        this.fs = fs;
        this.name = command[1];
    }

    /**
     * Construct a new {@code RemoveCri} Operation.
     * <p>
     * Constructor for another {@code Operation} objects, for the {@code undo} operation.
     *
     * @param fs the reference to the file system.
     * @param criterion the reference to the criterion to remove.
     * @param type the type of this operation.
     */
    public RemoveCri(FileSystem fs, Criterion criterion, OperationType type) {
        this.fs = fs;
        this.criterion = criterion;
        this.name = criterion.getName();
        this.type = type;
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            if (criterion == null) {
                criterion = fs.findCriterion(name);
            }
            fs.removeCriterion(criterion);
            return "The criterion has been removed successfully: " + criterion.toString();
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 2) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }
    }

    @Override
    public boolean isUndoOperation() {
        return type == OperationType.UNDO;
    }

    @Override
    public boolean isRedoOperation() {
        return type == OperationType.REDO;
    }

    @Override
    public Operation getUndoOperation() {
        return new PutBackCri(fs, criterion, type.getInverseType());
    }
}
