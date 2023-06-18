package org.example.Simplex;


public enum Relationship {

  EQ("="),
  LEQ("<="),
  GEQ(">=");
  
  private final String stringValue;
  
  private Relationship(String stringValue) {
    this.stringValue = stringValue;
  }
  
  @Override
  public String toString() {
    return stringValue;
  }
  
  public Relationship oppositeRelationship() {
    switch (this) {
      case LEQ: return GEQ;
      case GEQ: return LEQ;
    }
    return EQ;
  }

  public static Relationship getByString(String sign){
    return switch (sign){
      case "=" -> Relationship.EQ;
      case ">=" -> Relationship.GEQ;
      case "<=" -> Relationship.LEQ;
      default -> throw new IllegalArgumentException(sign);
    };
  }
  
}
