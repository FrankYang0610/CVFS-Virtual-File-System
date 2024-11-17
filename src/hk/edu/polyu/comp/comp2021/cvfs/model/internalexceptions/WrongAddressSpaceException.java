package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code WrongAddressSpaceException} Exception</h3>
 * Thrown to indicate that the directory does not belong to the virtual disk. From a simulation perspective, this is an error related to address mapping.
 */
public class WrongAddressSpaceException extends ModelException {
    /**
     * Construct a new exception.
     */
    public WrongAddressSpaceException() {
        super("Fatal error: The directory does not belongs to the virtual disk!");
    }
}
