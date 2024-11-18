package hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.ModelInternalUse;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;

// import java.io.Serial;

/**
 * <h3>The {@code TypeCriterion} Criterion Class</h3>
 * This class represents the simple criterion with {@code attrName == type}, see {@code [REQ9]}.
 */
public final class TypeCriterion implements Criterion {
    // @Serial
    private static final long serialVersionUID = 1L;

    private final String name;

    private final String typename;

    private int referenceCount;


    /**
     * Construct a new simple criterion with {@code attrName == name}.
     * @param name the name of the criterion.
     * @param val the parameter, the type to match.
     */
    public TypeCriterion(String name, String val) {
        this.name = name;
        this.typename = val;
        this.referenceCount = 0;
    }

    @Override
    public boolean check(File file) {
        return (file instanceof Document) && ((Document)file).getType().equals(typename);
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
        return new Criterion[0];
    }

    @Override
    public String toString() {
        return name + ": " + "type equals " + "\"" + typename + "\"";
    }
}
