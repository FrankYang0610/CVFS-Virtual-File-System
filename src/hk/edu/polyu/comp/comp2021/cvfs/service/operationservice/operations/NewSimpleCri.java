package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.CriterionFactory;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

/**
 * <h3>The {@code NewSimpleCri} Operation Class</h3>
 * This class encapsulates the operation of the {@code newSimpleCri} command, see {@code [REQ9]}.
 */
public final class NewSimpleCri implements FileUnrelatedUndoableOperation {
    private final FileSystem fs;

    private final String criName;

    private final String attrName;

    private final String op;

    private final String val;

    /**
     * The created criterion of this operation.
     */
    private Criterion criterion;


    /**
     * Construct a new {@code NewSimpleCri} Operation.
     * <p>
     * User command: {@code newSimpleCri <criName> <attrName> <op> <val>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public NewSimpleCri(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;

        this.criName = command[1];
        this.attrName = command[2];
        this.op = command[3];
        this.val = command[4];
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            CriterionFactory criterionFactory = new CriterionFactory();
            criterion = criterionFactory.createSimpleCriterion(criName, attrName, op, val);
            fs.addCriterion(criterion);
            return "The new criterion has been created successfully: " + criterion.toString() + ".";
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 5) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }
    }

    @Override
    public boolean isUndoOperation() {
        return false;
    }

    @Override
    public boolean isRedoOperation() {
        return false;
    }

    @Override
    public Operation getInverseOperation() {
        return new RemoveCri(fs, criterion, OperationType.UNDO);
    }
}
