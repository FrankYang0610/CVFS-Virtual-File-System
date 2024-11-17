package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code CannotInitializeEntityException} Exception</h3>
 * Thrown to indicate that the file cannot be initialized. This is most likely because the provided parameters do not meet the system requirements.
 */
public class CannotInitializeFileException extends ModelException {
    /**
     * Construct a new exception.
     * @param message the exception message.
     */
    public CannotInitializeFileException(String message) {
        super("Cannot initialize the file: " + message);
    }
}
