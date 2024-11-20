package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;
import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;

/**
 * <h3>The {@code Remove} Operation Class</h3>
 * This class has two purposes. First, it encapsulates the operation of the {@code list} command, see {@code [REQ4]}, and second, it is the inverse operation of {@code PutBack}.
 * @see PutBack
 */
public final class Remove implements UndoableOperation {
    private final FileSystem fs;

    private final OperationType type;

    /**
     * The name of the file to be removed.
     */
    private final String name;

    /**
     * The file to be removed.
     */
    private File file;

    /**
     * Construct a new {@code Remove} Operation.
     * <p>
     * Constructor for the {@code OperationFactory} and for the {@code delete} command.
     * <p>
     * User command: {@code delete <fileName>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public Remove(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        type = OperationType.REGULAR;
        this.fs = fs;
        this.name = command[1];
    }

    /**
     * Construct a new {@code Remove} Operation.
     * <p>
     * Constructor for another {@code Operation} objects, for the {@code undo} operation.
     *
     * @param fs the reference to the file system.
     * @param file the reference to the file to remove.
     * @param type the type of this operation.
     */
    public Remove(FileSystem fs, File file, OperationType type) {
        this.fs = fs;
        this.file = file;
        this.name = this.file.getName();
        this.type = type;
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            // For user-command-created objects where file is not specified.
            if (file == null) {
                file = fs.findFile(fs.getWorkingDirectory(), name);
            }
            fs.removeFile(file);
            return "The file " + "\"" + file.getFullname() + "\"" + " has been removed successfully.";
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

    /**
     * The undo operation of the {@code Remove} is {@code PutBack}.
     */
    @Override
    public Operation getInverseOperation() {
        return new PutBack(fs, file, type.getInverseType());
    }
}
