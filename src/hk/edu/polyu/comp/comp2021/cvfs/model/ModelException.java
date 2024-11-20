package hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.globalexceptions.CVFS_Exception;

/**
 * <h3>The {@code ModelException} Exception</h3>
 * The general exception class of the Model part. All exceptions to the Model part should extend this exception.
 */
public class ModelException extends CVFS_Exception {
    /**
     * Construct a new exception.
     * @param message the exception message.
     */
    public ModelException(String message) {
        super(message);
    }
}
