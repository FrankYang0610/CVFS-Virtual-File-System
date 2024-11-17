package hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;

import java.io.Serial;

/**
 * <h3>The {@code IsDocument} Criterion Class</h3>
 * This class represents the {@code IsDocument} criterion, see {@code [REQ10]}.
 */
public final class IsDocument implements Criterion {
    @Serial
    private static final long serialVersionUID = 1L;

    private int referenceCount;
    
    @Override
    public boolean check(File file) {
        return file instanceof Document;
    }

    @Override
    public String getName() {
        return "IsDocument";
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
        return getName();
    }
}
