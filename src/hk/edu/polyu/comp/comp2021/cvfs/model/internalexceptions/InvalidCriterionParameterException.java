package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code InvalidCriterionParameterException} Exception</h3>
 * Thrown to indicate that the parameter is invalid for creating a criterion.
 */
public class InvalidCriterionParameterException extends ModelException {
    /**
     * Construct a new exception.
     * @param parameter the invalid parameter which causes this exception.
     */
    public InvalidCriterionParameterException(String parameter) {
        super("Invalid criterion parameter: " + parameter + ".");
    }
}
