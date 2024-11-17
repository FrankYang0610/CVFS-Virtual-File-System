package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.OperationRecord;

/**
 * <h3>The {@code LoadCri} Operation Class</h3>
 * This class encapsulates the operation of the {@code loadCri} command, see {@code [BON1]}.
 */
public final class LoadCri implements Operation {
    private final FileSystem fs;

    private final OperationRecord operationRecord;

    private final String path;

    /**
     * Construct a new {@code ChangeDir} Operation.
     * <p>
     * User command: {@code loadCri <path>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @param operationRecord the reference to the operation record.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public LoadCri(FileSystem fs, OperationRecord operationRecord, String[] command) throws InvalidCommandException {
        try {
            commandValidityCheck(command);
            this.fs = fs;
            this.operationRecord = operationRecord;
            path = command[1];
        } catch (InvalidCommandException e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            operationRecord.clearFileUnrelated();
            fs.loadCriteria(path);
            return "The criteria has been successfully loaded from: " + path + ".";
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
