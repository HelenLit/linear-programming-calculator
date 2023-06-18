package org.example.Simplex;

import org.apache.commons.math.optimization.GoalType;

import java.util.*;


public class LinearModel {

  private final LinearObjectiveFunction objectiveFunction;
  private final List<LinearEquation> constraints;
  

  public LinearModel(LinearObjectiveFunction objectiveFunction) {
    this.objectiveFunction = objectiveFunction;
    this.constraints = new ArrayList<LinearEquation>();
  }
  
  /**
   * Adds the given constraint to the model.
   * 
   * @param constraint The {@link LinearEquation} to add to the model.
   */
  public void addConstraint(LinearEquation constraint) {
    if (constraint.getCoefficients().getDimension() != getNumVariables()) {
      throw new IndexOutOfBoundsException();
    }
    constraints.add(constraint);
  }
  
  public int getNumVariables() {
    return objectiveFunction.getCoefficients().getDimension();
  }

  public List<LinearEquation> getConstraints() {
    return constraints;
  }

  /**
   * Returns new versions of the constraints which have positive right hand sides.
   */
  public List<LinearEquation> getNormalizedConstraints() {
    List<LinearEquation> normalized = new ArrayList<LinearEquation>();
    for (LinearEquation constraint : constraints) {
      normalized.add(normalize(constraint));
    }
    return normalized;
  }
  
  /**
   * Returns a new equation equivalent to this one with a positive right hand side.
   */
  private LinearEquation normalize(LinearEquation constraint) {
    if (constraint.getRightHandSide() < 0) {
      return new LinearEquation(constraint.getCoefficients().mapMultiply(-1),
          constraint.getRelationship().oppositeRelationship(),
          -1 * constraint.getRightHandSide());
    }
    return new LinearEquation(constraint.getCoefficients(), 
        constraint.getRelationship(), constraint.getRightHandSide());
  }
  
  public LinearObjectiveFunction getObjectiveFunction() {
    return objectiveFunction;
  }
  
  /**
   * Returns a map from constraint type to count of the corresponding constraint type.
   */
  public Map<Relationship,Integer> getConstraintTypeCounts() {
    Map<Relationship,Integer> counts = new HashMap<Relationship,Integer>();
    for (Relationship relationship : Relationship.values()) {
      counts.put(relationship, 0);
    }
    for (LinearEquation constraint : getConstraints()) {
      counts.put(constraint.getRelationship(), counts.get(constraint.getRelationship()) + 1);
    }
    return counts;
  }

  public void convertAllTo(Relationship relationship){
    for (int i = 0; i < constraints.size(); i++) {
      if(constraints.get(i).getRelationship().compareTo(relationship)!=0){
        double[] leftSide = Arrays.stream(constraints.get(i).getCoefficients().getData()).map(e->-1*e).toArray();
        double rightSide = -1*constraints.get(i).getRightHandSide();
        constraints.set(i,new LinearEquation(leftSide,relationship,rightSide));
      }
    }
  }
  public LinearModel dualForm(){
    double[] coefArray = constraints.stream().mapToDouble(LinearEquation::getRightHandSide).toArray();
    LinearModel resultModel = new LinearModel(
            new LinearObjectiveFunction(coefArray, 0, oppositeGoal(objectiveFunction.getGoalType()))
    );
    for (int i = 0; i<objectiveFunction.getCoefficients().getDimension(); i++){
      double[] leftSide = createLeftSide(i);
      double rightSide;
      try{
        rightSide = objectiveFunction.getCoefficients().getEntry(i);
      }catch (ArrayIndexOutOfBoundsException e){
        rightSide = 0.0;

      }

      Relationship rel = constraints.get(i).getRelationship().oppositeRelationship();
      resultModel.addConstraint(new LinearEquation(leftSide, rel, rightSide));
    }
    return resultModel;
  }
  private GoalType oppositeGoal(GoalType goalType){
    if (goalType == GoalType.MAXIMIZE)
      return GoalType.MINIMIZE;
    else
      return GoalType.MAXIMIZE;
  }
  private double[] createLeftSide(int i){
    return constraints.stream().mapToDouble(e->{
      double result;
      try{
        result = e.getCoefficients().getEntry(i);
      } catch (ArrayIndexOutOfBoundsException ex){
        result = 1.0;
      }
      return result;
    }).toArray();
  }
  
}
