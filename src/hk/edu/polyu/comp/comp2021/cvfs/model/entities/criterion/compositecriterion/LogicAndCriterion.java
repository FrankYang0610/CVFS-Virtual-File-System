package hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.compositecriterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.ModelInternalUse;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;

// import java.io.Serial;

/**
 * <h3>The {@code LogicAndCriterion} Criterion Class</h3>
 * This class represents the {@code newBinary} composite criterion with {@code logicOp == &&}, see {@code [REQ11]}.
 */
public final class LogicAndCriterion implements Criterion {
    // @Serial
    private static final long serialVersionUID = 1L;

    private final String name;

    private final Criterion anotherCriterion1, anotherCriterion2;

    private int referenceCount;


    /**
     * Construct a new logic-and binary criterion.
     * @param name the name of the criterion.
     * @param anotherCriterion1 another criterion.
     * @param anotherCriterion2 another criterion. 
     */
    public LogicAndCriterion(String name, Criterion anotherCriterion1, Criterion anotherCriterion2) {
        this.name = name;
        this.anotherCriterion1 = anotherCriterion1;
        this.anotherCriterion2 = anotherCriterion2;
        this.referenceCount = 0;
    }

    @Override
    public boolean check(File file) {
        return anotherCriterion1.check(file) && anotherCriterion2.check(file);
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
    @ModelInternalUse
    public void __INTERNAL__increaseReferenceCount() {
        referenceCount++;
    }

    @Override
    @ModelInternalUse
    public void __INTERNAL__decreaseReferenceCount() {
        referenceCount--;
    }

    @Override
    public Criterion[] getDependencies() {
        return new Criterion[]{anotherCriterion1, anotherCriterion2};
    }

    @Override
    public String toString() {
        return name + ": " + anotherCriterion1.getName() + " && " + anotherCriterion2.getName();
    }
}
