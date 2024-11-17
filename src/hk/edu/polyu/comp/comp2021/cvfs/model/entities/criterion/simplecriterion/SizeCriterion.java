package hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.InvalidCriterionParameterException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;

// import java.io.Serial;
import java.util.Arrays;
import java.util.List;

/**
 * <h3>The {@code SizeCriterion} Criterion Class</h3>
 * This class represents the simple criterion with {@code attrName == size}, see {@code [REQ9]}.
 */
public final class SizeCriterion implements Criterion {
    // @Serial
    private static final long serialVersionUID = 1L;

    private static final List<String> validOperators = Arrays.asList(">", "<", ">=", "<=", "==", "!=");

    private final String name;

    private final String op;

    private final long size;

    private int referenceCount;

    /**
     * Construct a new simple criterion with {@code attrName == name}.
     * @param name the name of the criterion.
     * @param op the operator.
     * @param size the size to match.
     * @throws InvalidCriterionParameterException if the parameter(s) are invalid.
     */
    public SizeCriterion(String name, String op, long size) throws InvalidCriterionParameterException {
        if (!isValidFiletype(op)) {
            throw new InvalidCriterionParameterException(op);
        }

        this.name = name;
        this.op = op;
        this.size = size;
        this.referenceCount = 0;
    }

    private boolean isValidFiletype(String type) {
        return validOperators.contains(type);
    }

    @Override
    public boolean check(File file) {
        switch (op) {
            case ">": return file.getSize() > size;
            case "<": return file.getSize() < size;
            case ">=": return file.getSize() >= size;
            case "<=": return file.getSize() <= size;
            case "==": return file.getSize() == size;
            case "!=": return file.getSize() != size;
            default: return false;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getReferenceCount() {
        return referenceCount;
    }

    @Override
    public void increaseReferenceCount() {
        referenceCount++;
    }

    @Override
    public void decreaseReferenceCount() {
        referenceCount--;
    }

    @Override
    public Criterion[] getDependencies() {
        return new Criterion[0];
    }

    @Override
    public String toString() {
        return name + ": " + "size" + " " + op + " " + size;
    }
}
