package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code LocalFileSystemException} Exception</h3>
 * Thrown to indicate that the there are error(s) from the local file system, which prevents the virtual disk and criteria from being loaded or saved.
 */
public class LocalFileSystemException extends ModelException {
    /**
     * Construct a new exception.
     * @param message the exception message.
     */
    public LocalFileSystemException(String message) {
        super("Local file system error: " + message + ".");
    }
}
