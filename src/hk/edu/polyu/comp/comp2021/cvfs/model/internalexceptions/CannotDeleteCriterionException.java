package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code CannotDeleteCriterionException} Exception</h3>
 * Thrown to indicate that the criterion cannot be deleted. Generally, this is because other criteria depend on this criterion.
 */
public class CannotDeleteCriterionException extends ModelException {
    /**
     * Construct a new exception.
     */
    public CannotDeleteCriterionException() {
        super("The criterion cannot be deleted. Possibly because other criterion depend on this criterion.");
    }
}

