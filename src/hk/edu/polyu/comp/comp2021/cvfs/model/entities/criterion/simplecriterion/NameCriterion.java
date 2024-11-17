package hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;

// import java.io.Serial;

/**
 * <h3>The {@code NameCriterion} Criterion Class</h3>
 * This class represents the simple criterion with {@code attrName == name}, see {@code [REQ9]}.
 */
public final class NameCriterion implements Criterion {
    // @Serial
    private static final long serialVersionUID = 1L;

    private final String name;

    private final String target;

    private int referenceCount;

    /**
     * Construct a new simple criterion with {@code attrName == name}.
     * @param name the name of the criterion.
     * @param val the parameter, the substring to match. 
     */
    public NameCriterion(String name, String val) {
        this.name = name;
        this.target = val;
        this.referenceCount = 0;
    }

    @Override
    public boolean check(File file) {
        return file.getName().contains(target);
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
        return name + ": " + "name contains " + "\"" + target + "\"";
    }
}
