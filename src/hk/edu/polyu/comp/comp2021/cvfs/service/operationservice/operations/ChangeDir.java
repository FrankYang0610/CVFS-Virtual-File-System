package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

/**
 * <h3>The {@code ChangeDir} Operation Class</h3>
 * This class encapsulates the operation of the {@code changeDir} command, see {@code [REQ6]}.
 * @implNote A path can be divided into different components from {@code :}. For example, a path {@code $:Main:main.java} can be divided into {@code $}, {@code Main} and {@code main.java}. This is useful for navigation. However, this feature is removed from the instruction document, while our application still keeps this.
 */
public final class ChangeDir implements UndoableOperation {
    private final FileSystem fs;

    private final OperationType type;

    /**
     * The path to the new directory. Only used in user commands.
     */
    private final String newDirectoryPath;

    private Directory originalDirectory;

    private Directory newDirectory;


    /**
     * Construct a new {@code ChangeDir} Operation.
     * <p>
     * User command: {@code changeDir <dirName>}
     * 
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public ChangeDir(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.newDirectoryPath = command[1];
        this.type = OperationType.REGULAR;
    }

    /**
     * Constructor for another {@code Operation} objects, for {@code undo} operation.
     * @param fs the reference to the file system.
     * @param originalDirectory the reference to the original directory.
     * @param newDirectory the reference to the new directory.
     * @param type the type of the operation.
     * @implNote No {@code commandValidityCheck} required for this method, because an undo operation shall always be legal.
     */
    public ChangeDir(FileSystem fs, Directory originalDirectory, Directory newDirectory, OperationType type) {
        this.fs = fs;
        this.originalDirectory = originalDirectory;
        this.newDirectory = newDirectory;
        this.newDirectoryPath = newDirectory.getPath();
        this.type = type;
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            if (originalDirectory == null) {
                originalDirectory = fs.getWorkingDirectory();
            }

            if (newDirectory == null) {
                Directory directoryNavigator = originalDirectory;
                String[] pathComponents = newDirectoryPath.split(":");

                int cur = 0;

                if (pathComponents[cur].equals("$")) {
                    directoryNavigator = fs.getRootDirectory();
                    cur++;
                }

                // Traverse every path component.
                for (; cur < pathComponents.length; cur++) {
                    if (pathComponents[cur].equals("..")) {
                        directoryNavigator = (Directory)fs.getParent(directoryNavigator);
                        if (directoryNavigator == null) {
                            throw new InvalidCommandException("The parent directory does not exist.");
                        }
                        continue;
                    } else if (pathComponents[cur].equals(".")) { // Useless, this keeps the navigator in the current directory.
                        continue;
                    }

                    File nextDirectory = fs.findFile(directoryNavigator, pathComponents[cur]);

                    if (nextDirectory instanceof Directory) {
                        directoryNavigator = (Directory)nextDirectory;
                    } else {
                        throw new InvalidCommandException(pathComponents[cur] + " is not a directory.");
                    }
                } // end for

                newDirectory = directoryNavigator;
            }

            fs.setNewWorkingDirectory(newDirectory);

            return "The directory was successfully changed into: " + newDirectory.getPath() + ".";
        } catch (ModelException | InvalidCommandException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 2) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }

        if (command[1].length() > 10) {
            throw new InvalidCommandException("The length of the directory name cannot be more than 10, with provided length: " + command[1].length() + ".");
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
        return new ChangeDir(fs, newDirectory, originalDirectory, type.getInverseType());
    }
}
