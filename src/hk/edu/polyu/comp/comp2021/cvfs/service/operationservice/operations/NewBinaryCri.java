package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.CriterionFactory;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

/**
 * <h3>The {@code NewBinary} Operation Class</h3>
 * This class encapsulates the operation of the {@code newBinaryCri} command, see {@code [REQ11]}.
 */
public final class NewBinaryCri implements FileUnrelatedUndoableOperation {
    private final FileSystem fs;

    private final String criName1;

    private final String criName3;

    private final String logicOp;

    private final String criName4;

    /**
     * The created criterion of this operation.
     */
    private Criterion criterion;


    /**
     * Construct a new {@code NewBinaryCri} Operation.
     * <p>
     * User command: {@code newBinaryCri <criName1> <criName3> <logicOp> <criName4>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public NewBinaryCri(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.criName1 = command[1];
        this.criName3 = command[2];
        this.logicOp = command[3];
        this.criName4 = command[4];
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            CriterionFactory criterionFactory = new CriterionFactory();
            criterion = criterionFactory.createBinaryCriterion(criName1, fs.findCriterion(criName3), logicOp, fs.findCriterion(criName4));
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
