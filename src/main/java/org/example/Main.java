package org.example;

import org.apache.commons.math.optimization.GoalType;
import org.example.Simplex.*;

public class Main {
    private static LinearModel model;
    private static SimplexSolver solver;

    public static void main(String[] args) {
        createModel(new LinearObjectiveFunction(
                new double[]{7, 5}, 0, GoalType.MAXIMIZE));
        addConstraint(new LinearEquation(new double[]{7, 5}, Relationship.GEQ, 7));
        addConstraint(new LinearEquation(new double[]{7, -5}, Relationship.GEQ, 35));
        addConstraint(new LinearEquation(new double[]{1, -1}, Relationship.LEQ, 10));
        addConstraint(new LinearEquation(new double[]{1, 1}, Relationship.LEQ, 12));

//        createModel(new LinearObjectiveFunction(
//                new double[]{1}, 0, GoalType.MINIMIZE));
//        addConstraint(new LinearEquation(new double[]{1}, Relationship.EQ, 1));

        calculatePrimal();
        calculateDual();
//        calculateDual();
//        changeGoalType();
//        calculatePrimal();
//        calculateDual();
    }

    private static void printSolution(LinearEquation solution) {
        System.out.println("\nSolution:\nX = " + solution.getCoefficients());
        System.out.println("F(X) " + solution.getRelationship() + " " + solution.getRightHandSide());
    }

    private static void calcSolution() {
        try {
            LinearEquation solution = solver.solve();
            printSolution(solution);
        } catch (NoFeasibleSolutionException | UnboundedSolutionException e) {
            e.printStackTrace();
        }
    }

    private static void changeGoalType() {
        LinearObjectiveFunction objFunc = model.getObjectiveFunction();
        GoalType goalType;
        if (objFunc.getGoalType().equals(GoalType.MINIMIZE))
            goalType = GoalType.MAXIMIZE;
        else
            goalType = GoalType.MINIMIZE;
        model = new LinearModel(new LinearObjectiveFunction(objFunc.getCoefficients().mapMultiply(-1), objFunc.getConstantTerm(), goalType));
    }

    public static void createModel(LinearObjectiveFunction function) {
        try {
            model = new LinearModel(function);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static void addConstraint(LinearEquation equation) {
        model.addConstraint(equation);
    }

    public static void calculatePrimal() {
        solver = new SimplexSolver();
        solver.setNonNegative(true);
        solver.createTableau(model);
        solver.setPrintTableMethod(Main::printTable);
        solver.printFirstTable();
        calcSolution();
    }

    public static void calculateDual() {
        if (model.getObjectiveFunction().getGoalType().equals(GoalType.MINIMIZE))
            model.convertAllTo(Relationship.GEQ);
        else model.convertAllTo(Relationship.LEQ);
        solver = new SimplexSolver();
        solver.setNonNegative(true);
        solver.createTableau(model.dualForm());
        solver.setPrintTableMethod(Main::printTable);
        calcSolution();
    }
    public static void printTable(SimplexTableau tabl){
        double[][] table = tabl.tableau.getData();

        System.out.println();
        int numRows = table.length;
        int numCols = table[0].length;

        // Print column headers
        System.out.print("   ");
        for (int j = 0; j < numCols; j++) {
            System.out.printf("%8d", j);
        }
        System.out.println();

        // Print separator line
        System.out.print("   ");
        for (int j = 0; j < numCols; j++) {
            System.out.print("--------");
        }
        System.out.println("-");

        // Print table rows
        for (int i = 0; i < numRows; i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < numCols; j++) {
                System.out.printf("%8.2f|", table[i][j]);
            }
            System.out.println();

            // Print separator line
            System.out.print("   ");
            for (int j = 0; j < numCols; j++) {
                System.out.print("--------");
            }
            System.out.println("-");
        }

    }

}