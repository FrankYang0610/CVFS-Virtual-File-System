package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.CriterionFactory;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

/**
 * <h3>The {@code NewNegation} Operation Class</h3>
 * This class encapsulates the operation of the {@code newNegation} command, see {@code [REQ11]}.
 */
public final class NewNegation implements FileUnrelatedUndoableOperation {
    private final FileSystem fs;

    private final String criName1;

    private final String criName2;

    /**
     * The created criterion of this operation.
     */
    private Criterion criterion;


    /**
     * Construct a new {@code NewNegation} Operation.
     * <p>
     * User command: {@code newNegation <criName1> <criName2>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public NewNegation(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.criName1 = command[1];
        this.criName2 = command[2];
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            CriterionFactory criterionFactory = new CriterionFactory();
            criterion = criterionFactory.createNegationCriterion(criName1, fs.findCriterion(criName2));
            fs.addCriterion(criterion);
            return "The new criterion has been created successfully: " + criterion.toString() + ".";
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 3) {
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
