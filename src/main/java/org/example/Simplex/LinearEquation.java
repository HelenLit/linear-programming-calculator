package org.example.Simplex;

import org.apache.commons.math.linear.RealVector;
import org.apache.commons.math.linear.ArrayRealVector;



public class LinearEquation {

  private final RealVector leftHandSide;
  private final Relationship relationship;
  private final double rightHandSide;
  
  public LinearEquation(double[] coefficients, Relationship relationship, double rightHandSide) {
    this(new ArrayRealVector(coefficients), relationship, rightHandSide);
  }
  
  public LinearEquation(RealVector leftHandSide, Relationship relationship, double rightHandSide) {
    this.leftHandSide = leftHandSide;
    this.relationship = relationship;
    this.rightHandSide = rightHandSide;
  }

  public LinearEquation(RealVector leftHandSide, Relationship relationship, RealVector rightHandSide) {
    this.leftHandSide = leftHandSide.subtract(rightHandSide);
    this.relationship = relationship;
    this.rightHandSide = 0;
  }
    
  public RealVector getCoefficients() {
    return leftHandSide;
  }
  
  public Relationship getRelationship() {
    return relationship;
  }

  public double getRightHandSide() {
    return rightHandSide;
  }
  
}