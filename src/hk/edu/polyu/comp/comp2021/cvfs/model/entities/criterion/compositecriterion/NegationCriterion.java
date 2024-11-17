package hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.compositecriterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;

import java.io.Serial;

/**
 * <h3>The {@code NewNegation} Criterion Class</h3>
 * This class represents the {@code newNegation} composite criterion, see {@code [REQ11]}.
 */
public final class NegationCriterion implements Criterion {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private final String name;

    private final Criterion anotherCriterion;

    private int referenceCount;


    /**
     * Construct a new negation criterion.
     * @param name the name of the criterion.
     * @param anotherCriterion another criterion.
     */
    public NegationCriterion(String name, Criterion anotherCriterion) {
        this.name = name;
        this.anotherCriterion = anotherCriterion;
        this.referenceCount = 0;
    }

    @Override
    public boolean check(File file) {
        return !anotherCriterion.check(file);
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
        return new Criterion[]{anotherCriterion};
    }

    @Override
    public String toString() {
        return name + ": " + "!" + anotherCriterion.getName();
    }
}
