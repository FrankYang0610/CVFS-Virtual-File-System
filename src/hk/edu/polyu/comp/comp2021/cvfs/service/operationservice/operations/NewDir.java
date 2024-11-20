package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Directory;

/**
 * <h3>The {@code NewDir} Operation Class</h3>
 * This class encapsulates the operation of the {@code newDir} command, see {@code [REQ3]}.
 * <p>
 * The undo operation of this is {@code Remove}.
 */
public final class NewDir implements UndoableOperation {
    private final FileSystem fs;

    /**
     * The name of the new directory.
     */
    private final String name;

    private Directory directory;


    /**
     * Construct a new {@code NewDir} Operation.
     * <p>
     * User command: {@code newDir <dirName>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public NewDir(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.name = command[1];
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            directory = new Directory(name, fs.getWorkingDirectory());
            fs.storeFile(directory);
            return "New directory " + directory.getFullname() + " has been created successfully and added into " + fs.getParent(directory).getFullname() + "."; // Using directory.getFullName() instead of name is only for consistent.
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 2) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }

        if (command[1].length() > 10) {
            throw new InvalidCommandException("The length of the filename cannot be more than 10, with provided length: " + command[1].length() + ".");
        }
    }

    /**
     * {@code NewDir} is not the undo operation of any operation, hence the {@code isUndoOperation()} shall always return false.
     */
    @Override
    public boolean isUndoOperation() {
        return false;
    }

    /**
     * {@code NewDir} is not the redo operation of any operation, hence the {@code isRedoOperation()} shall always return false.
     */
    @Override
    public boolean isRedoOperation() {
        return false;
    }

    @Override
    public Operation getInverseOperation() {
        return new Remove(fs, directory, OperationType.UNDO);
    }
}
