package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

/**
 * <h3>The {@code Save} Operation Class</h3>
 * This class encapsulates the operation of the {@code save} command, see {@code [REQ15]}.
 */
public final class Save implements Operation {
    private final FileSystem fs;

    private final String path;

    /**
     * Construct a new {@code Save} Operation.
     * <p>
     * User command: {@code save <path>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public Save(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.path = command[1];
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            fs.saveVDisk(path);
            return "The mounted virtual disk has been successfully saved to: " + path + ".";
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 2) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1));
        }
    }
}
