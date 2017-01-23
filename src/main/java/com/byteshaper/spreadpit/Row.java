package com.byteshaper.spreadpit;

public class Row {

  private final int lineNumber;

  private String firstColumn;

  private String secondColumn;

  private String thirdColumn;

  public Row(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public Row(int lineNumber, String firstColumn, String secondColumn) {
    this.lineNumber = lineNumber;
    this.firstColumn = firstColumn;
    this.secondColumn = secondColumn;
  }

  public Row(int lineNumber, String firstColumn, String secondColumn, String thirdColumn) {
    this.lineNumber = lineNumber;
    this.firstColumn = firstColumn;
    this.secondColumn = secondColumn;
    this.thirdColumn = thirdColumn;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public String getFirstColumn() {
    return firstColumn;
  }

  public void setFirstColumn(String firstColumn) {
    this.firstColumn = firstColumn;
  }

  public String getSecondColumn() {
    return secondColumn;
  }

  public void setSecondColumn(String secondColumn) {
    this.secondColumn = secondColumn;
  }

  public String getThirdColumn() {
    return thirdColumn;
  }

  public void setThirdColumn(String thirdColumn) {
    this.thirdColumn = thirdColumn;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + lineNumber;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
      
    if (obj == null) {
      return false;
    }
      
    if (getClass() != obj.getClass()) {
      return false;
    }
    
    Row other = (Row) obj;
    if (lineNumber != other.lineNumber) {
      return false;
    }
      
    return true;
  }

  @Override
  public String toString() {
    return "{{" + firstColumn + " -> " + secondColumn + "}}";
  }
}
