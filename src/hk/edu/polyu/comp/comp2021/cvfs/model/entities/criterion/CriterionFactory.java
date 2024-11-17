package hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.compositecriterion.LogicAndCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.compositecriterion.LogicOrCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.compositecriterion.NegationCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion.NameCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion.SizeCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion.TypeCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.InvalidCriterionParameterException;

/**
 * <h3>The {@code CriterionFactory} Class</h3>
 * Similar to the {@code OperationFactory} class, The {@code CriterionFactory} class is designed to create {@code Criterion} objects of the correspond criteria specified by user commands.
 * <p>
 * Different criteria have different constructors, and the allowed parameters also vary. In this {@code CriterionFactory} part, we only do the most basic checks.
 *
 * @see Criterion
 */
public final class CriterionFactory {
    /**
     * The method is used to generate simple criteria. Since the simple criterion does not involve other criteria, it can be set directly String parameters.
     * <p>
     * Please also view the instruction document.
     *
     * @param criName the name of the new criterion.
     * @param attrType the type of the attribute.
     * @param op the operator.
     * @param val the value.
     * @return a new simple criterion.
     * @throws InvalidCriterionParameterException if any given parameter(s) are invalid.
     */
    public Criterion createSimpleCriterion(String criName, String attrType, String op, String val) throws InvalidCriterionParameterException {
        try {
            checkCriName(criName);

            return switch (attrType) {
                case "name" -> {
                    if (!op.equals("contains")) {
                        throw new InvalidCriterionParameterException(op);
                    }
                    if (val.length() <= 2 || !val.startsWith("\"") || !val.endsWith("\"")) {
                        throw new InvalidCriterionParameterException(val);
                    }
                    yield new NameCriterion(criName, val.substring(1, val.length() - 1));
                }
                case "type" -> {
                    if (!op.equals("equals")) {
                        throw new InvalidCriterionParameterException(op);
                    }
                    if (val.length() <= 2 || !val.startsWith("\"") || !val.endsWith("\"")) {
                        throw new InvalidCriterionParameterException(val);
                    }
                    yield new TypeCriterion(criName, val.substring(1, val.length() - 1));
                }
                case "size" -> new SizeCriterion(criName, op, Long.parseLong(val)); // The validity of op will be judged in the SizeCriterion constructor.
                default -> throw new InvalidCriterionParameterException("Unknown attribute type: " + attrType);
            };
        } catch (NumberFormatException e) {
            throw new InvalidCriterionParameterException("An integer expected.");
        }
    }


    /* The following methods are used to generate composite criteria. */
    // Since they involve other criteria,
    // they might still be used even if those criteria are removed or become invalid.
    // The result is uncertain.

    /**
     * Generate new negation criteria.
     * @param criName the name of the new criterion.
     * @param anotherCriterion another criterion.
     * @return a new negation criterion.
     * @throws InvalidCriterionParameterException if any given parameter(s) are invalid.
     */
    public Criterion createNegationCriterion(String criName, Criterion anotherCriterion) throws InvalidCriterionParameterException {
        checkCriName(criName);
        return new NegationCriterion(criName, anotherCriterion);
    }

    /**
     *
     * @param criName the name of the new criterion.
     * @param cri3 another criterion.
     * @param logicOp the logic operator for two another criteria. 
     * @param cri4 another criterion.
     * @return a new binary criterion.
     * @throws InvalidCriterionParameterException if any given parameter(s) are invalid.
     */
    public Criterion createBinaryCriterion(String criName, Criterion cri3, String logicOp, Criterion cri4) throws InvalidCriterionParameterException {
        checkCriName(criName);
        return switch (logicOp) {
            case "&&":
                yield new LogicAndCriterion(criName, cri3, cri4);
            case "||":
                yield new LogicOrCriterion(criName, cri3, cri4);
            default:
                throw new InvalidCriterionParameterException(logicOp);
        };
    }

    /**
     * Check if the name of a criterion is valid.
     * @param criName the name of the criterion.
     * @throws InvalidCriterionParameterException if the name is invalid, i.e., it does not contain two English letters.
     */
    private void checkCriName(String criName) throws InvalidCriterionParameterException {
        if (criName.length() != 2 || !criName.matches("[a-zA-Z]+")) {
            throw new InvalidCriterionParameterException("Invalid criterion name");
        }
    }
}
