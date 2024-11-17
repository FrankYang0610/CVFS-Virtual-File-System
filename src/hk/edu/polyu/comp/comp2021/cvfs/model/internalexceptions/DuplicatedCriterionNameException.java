package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code DuplicatedCriterionNameException} Exception</h3>
 * Thrown to indicate that there is another criterion in the criterion list with the same name.
 */
public class DuplicatedCriterionNameException extends ModelException {
    /**
     * Construct a new exception.
     * @param name the duplicated name of the criterion.
     */
    public DuplicatedCriterionNameException(String name) {
        super("Duplicated criterion name " + "\"" + name + "\"" + ".");
    }
}
