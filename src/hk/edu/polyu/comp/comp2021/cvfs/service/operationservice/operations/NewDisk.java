package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.vdisk.VDisk;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.OperationRecord;

/**
 * <h3>The {@code NewDisk} Operation Class</h3>
 * This class encapsulates the operation of the {@code newDisk} command, see {@code [REQ1]}.
 */
public final class NewDisk implements Operation {
    private final FileSystem fs;

    private final OperationRecord operationRecord;

    /**
     * The size of the new disk. It's guaranteed that {@code diskSize > 0} once the {@code NewDisk} object is created.
     */
    private final long diskSize;


    /**
     * Construct a new {@code NewDisk} Operation.
     * <p>
     * User command: {@code newDisk <diskSize>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @param operationRecord the reference to the operation record. 
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public NewDisk(FileSystem fs, OperationRecord operationRecord, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.operationRecord = operationRecord;
        this.diskSize = Long.parseLong(command[1]);
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            operationRecord.clearFileRelated();
            // fs.ejectVDisk();
            fs.mountVDisk(new VDisk(diskSize));
            return "New disk with size " + diskSize + " has been created and mounted successfully, and the previous disk, if exists, has been ejected.";
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 2) {
            throw new InvalidCommandException("Wrong number of arguments: " + (command.length - 1));
        }

        try {
            if (Long.parseLong(command[1]) <= 0) {
                throw new InvalidCommandException("Invalid disk size. Disk size should be larger than 0.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Invalid disk size. Disk size should be a positive and appropriate integer.");
        }
    }
}

