package org.example.Simplex;
import org.apache.commons.math.util.MathUtils;

import java.util.function.Consumer;

public class SimplexSolver {

    Consumer<SimplexTableau> printTable;
    private static final double DEFAULT_EPSILON = 0.0000000001;
    protected SimplexTableau tableau;
    protected final double epsilon;
    private boolean nonNegative = true;

    public SimplexSolver() {
        epsilon =  DEFAULT_EPSILON;
    }
    /**
     * @param model the {@link LinearModel} to solve.
     */
    public SimplexSolver(LinearModel model) {
        this(model, true);
    }

    /**
     * @param model the {@link LinearModel} to solve
     * @param restrictToNonNegative whether to restrict the variables to non-negative values
     */
    public SimplexSolver(LinearModel model, boolean restrictToNonNegative) {
        this(model, DEFAULT_EPSILON);
        nonNegative = restrictToNonNegative;

    }

    /**
     * @param model the {@link LinearModel} to solve
     * @param epsilon the amount of error to accept in floating point comparisons
     */
    public SimplexSolver(LinearModel model, double epsilon) {
        this.epsilon = epsilon;
        createTableau(model);
    }

    public void createTableau(LinearModel model){
        this.tableau = new SimplexTableau(model, nonNegative);
    }

    /**
     * Returns the column with the most negative coefficient in the objective function row.
     */
    private Integer getPivotColumn() {
        double minValue = 0;
        Integer minPos = null;
        for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getWidth() - 1; i++) {
            if (tableau.getEntry(0, i) < minValue) {
                minValue = tableau.getEntry(0, i);
                minPos = i;
            }
        }
        return minPos;
    }

    public void setNonNegative(boolean value){
        nonNegative = value;
        //tableau.nonNegative = value;
    }
    /**
     * Returns the row with the minimum ratio as given by the minimum ratio test (MRT).
     *
     * @param col the column to test the ratio of.  See {@link #getPivotColumn()}
     */
    private Integer getPivotRow(int col) {
        double minRatio = Double.MAX_VALUE;
        Integer minRatioPos = null;
        for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getHeight(); i++) {
            double rhs = tableau.getEntry(i, tableau.getWidth() - 1);
            if (tableau.getEntry(i, col) >= 0) {
                double ratio = rhs / tableau.getEntry(i, col);
                if (ratio < minRatio) {
                    minRatio = ratio;
                    minRatioPos = i;
                }
            }
        }
        return minRatioPos;
    }


    /**
     * Runs one iteration of the Simplex method on the given model.
     * @throws UnboundedSolutionException if the model is found not to have a
     *     bounded solution
     */
    protected void doIteration() throws UnboundedSolutionException {
        Integer pivotCol = getPivotColumn();
        Integer pivotRow = getPivotRow(pivotCol);
        if (pivotRow == null) {
            throw new UnboundedSolutionException();
        }

        // set the pivot element to 1
        double pivotVal = tableau.getEntry(pivotRow, pivotCol);
        tableau.divideRow(pivotRow, pivotVal);

        // set the rest of the pivot column to 0
        for (int i = 0; i < tableau.getHeight(); i++) {
            if (i != pivotRow) {
                double multiplier = tableau.getEntry(i, pivotCol);
                tableau.subtractRow(i, pivotRow, multiplier);
            }
        }
    }

    /**
     * Checks whether Phase 1 is solved.
     *
     * @return whether Phase 1 is solved
     */
    private boolean isPhase1Solved() {
        if (tableau.getNumArtificialVariables() == 0) {
            return true;
        }
        for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getWidth() - 1; i++) {
            if (tableau.getEntry(0, i) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether the problem is at an optimal state.
     *
     * @return whether the model has been solved
     */
    public boolean isOptimal() {
        if (tableau.getNumArtificialVariables() > 0) {
            return false;
        }
        for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getWidth() - 1; i++) {
            if (tableau.getEntry(0, i) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Solves Phase 1 of the Simplex method.
     *
     * @throws UnboundedSolutionException if the model is found not to have a
     *     bounded solution
     * @throws NoFeasibleSolutionException if there is no feasible solution
     */
    protected void solvePhase1() throws UnboundedSolutionException, NoFeasibleSolutionException {
        // make sure we're in Phase 1
        if (tableau.getNumArtificialVariables() == 0) {
            return;
        }

        while (!isPhase1Solved()) {
            doIteration();
        }

        // if W is not zero then we have no feasible solution
        if (!MathUtils.equals(tableau.getEntry(0, tableau.getRhsOffset()), 0, epsilon)) {
            throw new NoFeasibleSolutionException();
        }
    }

    /**
     * Iterates until the optimal solution is arrived at.
     *
     * @throws UnboundedSolutionException if the model is found not to have a
     *     bounded solution
     * @throws NoFeasibleSolutionException if there is no feasible solution
     */
    public LinearEquation solve() throws UnboundedSolutionException, NoFeasibleSolutionException {
        solvePhase1();
        tableau.discardArtificialVariables();
        while (!isOptimal()) {
            doIteration();
        }
        return tableau.getSolution();
    }
    public LinearEquation solveFirst() throws UnboundedSolutionException, NoFeasibleSolutionException {
        solvePhase1();
        printFirstTable();
        tableau.discardArtificialVariables();
        return solveNext();
    }
    public LinearEquation solveNext() throws UnboundedSolutionException, NoFeasibleSolutionException {
        if (!isOptimal()) {
            printTable.accept(tableau);
            doIteration();
            return null;
        }else
            return tableau.getSolution();
    }
    public  LinearEquation solveToFinal() throws UnboundedSolutionException, NoFeasibleSolutionException {
        while (!isOptimal()) {
            doIteration();
        }
        return tableau.getSolution();
    }
    public void printFirstTable(){
        printTable.accept(tableau);
    }

    public void setPrintTableMethod(Consumer<SimplexTableau> method){
        printTable = method;
    }

}
