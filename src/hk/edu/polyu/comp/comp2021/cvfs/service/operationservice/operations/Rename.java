package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;
import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;

/**
 * <h3>The {@code Rename} Operation Class</h3>
 * This class encapsulates the operation of the {@code rename} command, see {@code [REQ5]}.
 */
public final class Rename implements UndoableOperation {
    private final FileSystem fs;

    private final OperationType type;

    /**
     * The file to be renamed.
     */
    private File file;

    private final String originalName;

    private final String newName;


    /**
     * Construct a new {@code Rename} Operation.
     * <p>
     * Constructor for the {@code OperationFactory} and for the {@code rename} command.
     * <p>
     * User command: {@code rename <oldFileName> <newFileName>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public Rename(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.type = OperationType.REGULAR;
        this.originalName = command[1];
        this.newName = command[2];
    }

    /**
     * Constructor for another {@code Operation} objects, for the {@code undo} operation.
     * @param fs the reference to the file system.
     * @param file the reference to the file to rename.
     * @param originalName the original name of the file.
     * @param newName the new name of the file
     * @param type the type of this operation.
     * @implNote No {@code commandValidityCheck} required for this method, because an undo operation shall always be legal.
     */
    public Rename(FileSystem fs, File file, String originalName, String newName, OperationType type) {
        this.fs = fs;
        this.type = type;
        this.file = file;
        this.originalName = originalName;
        this.newName = newName;
    }
    
    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            if (file == null) {
                file = fs.findFile(fs.getWorkingDirectory(), originalName);
            }
            fs.renameFile(file, newName);
            return "The file " + "\"" + originalName + "\"" + " has been successfully renamed to " + "\"" + file.getName() + "\"" + ", " + "now the full name is: " + "\"" + file.getFullname() + "\"" + ".";
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 3) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }

        if (command[2].length() > 10) {
            throw new InvalidCommandException("The length of the filename cannot be more than 10, with provided length: " + command[2].length() + ".");
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
     * The undo operation of the {@code Rename} is itself.
     */
    @Override
    public Operation getInverseOperation() {
        return new Rename(fs, file, newName, originalName, type.getInverseType());
    }
}
