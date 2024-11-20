package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;

/**
 * <h3>The {@code PutBack} Operation Class</h3>
 * {@code PutBack} is the inverse operation of {@code Remove}. Note that this operation does not exist in the instruction document, so it is an internal operation. It should not be exposed externally and hence does not involve checking the existence of object references.
 *
 * @implNote Note that a disk space checking is still included in the {@code exec()} method of this class, since {@code PutBack} operation may not be the undo operation of another one. Actually, the instruction document doesn't mention a non-undo {@code putBack} command, so the disk space checking could be removed safely.
 * @see Remove
 */
public final class PutBack implements UndoableOperation {
    private final FileSystem fs;

    private final OperationType type;

    /**
     * The removed file to be put back.
     */
    private final File file;


    /* No constructor for OperationFactory and user commands. */

    /**
     * Construct a new {@code PutBack} Operation.
     * <p>
     * Constructor for another {@code Operation} object.
     * @param fs the reference to the file system.
     * @param file the reference to the file to put back.
     * @param type the type of this operation.
     * @implNote It's guaranteed that file is not {@code null}.
     */
    public PutBack(FileSystem fs, File file, OperationType type) {
        this.fs = fs;
        this.file = file;
        this.type = type;
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            fs.storeFile(file);
            return "The file " + file.getFullname() + " has been put back to the directory " + file.__INTERNAL__getParent().getFullname() + " successfully.";
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
        return new Remove(fs, file, type.getInverseType());
    }
}
