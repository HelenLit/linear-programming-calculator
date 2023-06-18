package org.example.Simplex;

import junit.framework.TestCase;
import org.apache.commons.math.optimization.GoalType;

public class SimplexTableauTest extends TestCase {
    
  public void testCreateTableau() {
    LinearModel model = createModel();
    SimplexTableau tableau = new SimplexTableau(model);
    tableau.createTableau(model);
    double[][] expectedInitialTableau = {
        {-1, 0,   0,   0,  0, 0, 0, 1, 0},
        { 0, 1, -15, -10, 25, 0, 0, 0, 0},
        { 0, 0,   1,   0, -1, 1, 0, 0, 2},
        { 0, 0,   0,   1, -1, 0, 1, 0, 3},
        { 0, 0,   1,   1, -2, 0, 0, 1, 4}
    };
    assertMatrixEquals(expectedInitialTableau, tableau.createTableau(model));
  }

  public void testInitialization() {    
    LinearModel model = createModel();
    SimplexTableau tableau = new SimplexTableau(model);
    double[][] expectedInitialTableau = {
        {-1, 0,  -1,  -1,  2, 0, 0, 0, -4},
        { 0, 1, -15, -10, 25, 0, 0, 0,  0},
        { 0, 0,   1,   0, -1, 1, 0, 0,  2},
        { 0, 0,   0,   1, -1, 0, 1, 0,  3},
        { 0, 0,   1,   1, -2, 0, 0, 1,  4}
    };
    assertMatrixEquals(expectedInitialTableau, tableau.getData());
  }

  public void testdiscardArtificialVariables() {    
    LinearModel model = createModel();
    SimplexTableau tableau = new SimplexTableau(model);
    double[][] expectedTableau = {
        { 1, -15, -10, 25, 0, 0, 0},
        { 0,   1,   0, -1, 1, 0, 2},
        { 0,   0,   1, -1, 0, 1, 3},
        { 0,   1,   1, -2, 0, 0, 4}
    };
    tableau.discardArtificialVariables();
    assertMatrixEquals(expectedTableau, tableau.getData());
  }
  
  public void testTableauWithNoArtificialVars() {
    LinearModel model = new LinearModel(new LinearObjectiveFunction(new double[] {15, 10},7,GoalType.MINIMIZE));
    model.addConstraint(new LinearEquation(new double[] {1, 0}, Relationship.LEQ, 2));
    model.addConstraint(new LinearEquation(new double[] {0, 1}, Relationship.LEQ, 3));
    model.addConstraint(new LinearEquation(new double[] {1, 1}, Relationship.LEQ, 4));    
    SimplexTableau tableau = new SimplexTableau(model);
    double[][] initialTableau = {
        {1, -15, -10, 25, 0, 0, 0, 0},
        {0,   1,   0, -1, 1, 0, 0, 2},
        {0,   0,   1, -1, 0, 1, 0, 3},
        {0,   1,   1, -2, 0, 0, 1, 4}
    };
    assertMatrixEquals(initialTableau, tableau.getData());
  }
  
  private LinearModel createModel() {
    LinearModel model = new LinearModel(new LinearObjectiveFunction(new double[] {15, 10},7, GoalType.MAXIMIZE));
    model.addConstraint(new LinearEquation(new double[] {1, 0}, Relationship.LEQ, 2));
    model.addConstraint(new LinearEquation(new double[] {0, 1}, Relationship.LEQ, 3));
    model.addConstraint(new LinearEquation(new double[] {1, 1}, Relationship.EQ, 4));
    return model;
  }
  
  private void assertMatrixEquals(double[][] expected, double[][] result) {
    assertEquals("Wrong number of rows.", expected.length, result.length);
    for (int i = 0; i < expected.length; i++) {
      assertEquals("Wrong number of columns.", expected[i].length, result[i].length);
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals("Wrong value at position [" + i + "," + j + "]", expected[i][j], result[i][j]);
      }
    }
  }
  
}
