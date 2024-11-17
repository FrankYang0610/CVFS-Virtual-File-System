package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code VDiskOutOfSpaceException} Exception</h3>
 * Thrown to indicate that the virtual disk is out of space to store the new file.
 */
public class VDiskOutOfSpaceException extends ModelException {
    /**
     * Construct a new exception.
     * @param fileSize the size of the file to be saved in the virtual disk.
     * @param vDiskFreeSpace the free space of the virtual disk. 
     */
    public VDiskOutOfSpaceException(long fileSize, long vDiskFreeSpace) {
        super("VDisk out of space to save the document: " + fileSize + " out of " + vDiskFreeSpace + ".");
    }
}
