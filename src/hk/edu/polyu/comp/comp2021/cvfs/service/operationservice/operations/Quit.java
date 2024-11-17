package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.OperationRecord;

/**
 * <h3>The {@code Quit} Operation Class</h3>
 * This class encapsulates the operation of the {@code quit} command, see {@code [REQ17]}.
 */
public final class Quit implements Operation {
    private final FileSystem fs;

    private final OperationRecord operationRecord;


    /**
     * Construct a new {@code Quit} Operation.
     * <p>
     * User command: {@code quit}
     *
     * @param fs the reference to the file system.
     * @param operationRecord the reference to the operation record.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public Quit(FileSystem fs, OperationRecord operationRecord, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.operationRecord = operationRecord;
    }

    @Override
    public String exec() {
        operationRecord.clearAll();
        fs.releaseResource();
        System.exit(0);
        return null;
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 1) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }
    }
}

