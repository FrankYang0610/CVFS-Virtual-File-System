package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code CannotDeleteFileException} Exception</h3>
 * Thrown to indicate that the root directory cannot be edited. This is possible but rarely happens.
 */
public class CannotEditRootDirectoryException extends ModelException {
    /**
     * Construct a new exception.
     */
    public CannotEditRootDirectoryException() {
        super("Root directory cannot be edited.");
    }
}
