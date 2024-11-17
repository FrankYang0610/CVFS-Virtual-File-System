package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code CriterionNotExistsException} Exception</h3>
 * Thrown to indicate that the criterion with the specific name does not exist in the criterion list.
 */
public class CriterionNotExistsException extends ModelException {
    /**
     * Construct a new exception.
     * @param name the name of the unexisted file.
     */
    public CriterionNotExistsException(String name) {
        super("Criterion not exists: " + "\"" + name + "\"" + ".");
    }
}
