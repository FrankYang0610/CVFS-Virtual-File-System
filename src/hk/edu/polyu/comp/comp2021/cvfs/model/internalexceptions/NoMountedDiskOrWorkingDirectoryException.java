package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code NoMountedDiskOrWorkingDirectoryException} Exception</h3>
 * Thrown to indicate that the the command requires a mounted virtual disk and an available working directory, while there is not. 
 */
public class NoMountedDiskOrWorkingDirectoryException extends ModelException {
    /**
     * Construct a new exception.
     */
    public NoMountedDiskOrWorkingDirectoryException() {
        super("No mounted virtual disk or available working directory.");
    }
}
