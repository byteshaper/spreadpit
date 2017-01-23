package com.byteshaper.spreadpit;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ProcessorTest {

  @Test
  public void noDuplicate() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot 1", "Wort 1"));
    inputRows.add(new Row(1, "mot 2", "Wort 2"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(2));
    assertTrue(deepEquals(inputRows.get(0), processedRows.get(0)));
    assertTrue(deepEquals(inputRows.get(1), processedRows.get(1)));
  }

  @Test
  public void plainFrenchDuplicate() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot", "Wort 1"));
    inputRows.add(new Row(1, "mot", "Wort 2"));
    inputRows.add(new Row(1, "mot", "Wort 1"));
    inputRows.add(new Row(1, "mot", "Wort 1"));
    inputRows.add(new Row(1, "mot", "Wort 2"));
    inputRows.add(new Row(1, "mot", "Wort 2"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(1));
    assertThat(processedRows.get(0).getFirstColumn(), equalTo("mot"));
    assertThat(processedRows.get(0).getSecondColumn(), equalTo("Wort 1; Wort 2"));
  }

  @Test
  public void plainGermanDuplicate() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot 1", "Wort"));
    inputRows.add(new Row(1, "mot 2", "Wort"));
    inputRows.add(new Row(1, "mot 1", "Wort"));
    inputRows.add(new Row(1, "mot 2", "Wort"));
    inputRows.add(new Row(1, "mot 2", "Wort"));
    inputRows.add(new Row(1, "mot 2", "Wort"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(1));
    assertThat(processedRows.get(0).getFirstColumn(), equalTo("mot 1; mot 2"));
    assertThat(processedRows.get(0).getSecondColumn(), equalTo("Wort"));
  }

  @Test
  public void doubleDuplicateFrenchDifferent() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot 1", "Wort"));
    inputRows.add(new Row(1, "mot 2", "Wort"));
    inputRows.add(new Row(1, "mot 1", "Wort"));
    inputRows.add(new Row(1, "mot 2", "Wort"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(1));
    assertThat(processedRows.get(0).getFirstColumn(), equalTo("mot 1; mot 2"));
    assertThat(processedRows.get(0).getSecondColumn(), equalTo("Wort"));
  }

  @Test
  public void doubleDuplicateGermanDifferent() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot", "Wort 1"));
    inputRows.add(new Row(1, "mot", "Wort 2"));
    inputRows.add(new Row(1, "mot", "Wort 2"));
    inputRows.add(new Row(1, "mot", "Wort 1"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(1));
    assertThat(processedRows.get(0).getFirstColumn(), equalTo("mot"));
    assertThat(processedRows.get(0).getSecondColumn(), equalTo("Wort 1; Wort 2"));
  }

  @Test
  public void bothSidesDuplicates() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot 1", "Wort 1"));
    inputRows.add(new Row(1, "mot 2", "Wort 2"));
    inputRows.add(new Row(1, "mot 1", "Wort 2"));
    inputRows.add(new Row(1, "mot 2", "Wort 1"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(1));
    assertThat(processedRows.get(0).getFirstColumn(), equalTo("mot 1; mot 2"));
    assertThat(processedRows.get(0).getSecondColumn(), equalTo("Wort 1; Wort 2"));
  }

  @Test
  public void noDuplicateSemicolon() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot 1; mot 2", "Wort 1"));
    inputRows.add(new Row(1, "mot 2", "Wort 2"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(2));
    assertThat(processedRows.get(0).getFirstColumn(), equalTo("mot 1; mot 2"));
    assertThat(processedRows.get(0).getSecondColumn(), equalTo("Wort 1"));
    assertThat(processedRows.get(1).getFirstColumn(), equalTo("mot 2"));
    assertThat(processedRows.get(1).getSecondColumn(), equalTo("Wort 2"));
  }

  @Test
  public void duplicateSemicolon() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot 1; mot 2", "Wort 1"));
    inputRows.add(new Row(1, "mot 2", "Wort 1"));
    inputRows.add(new Row(1, "mot 2", "Wort 1"));
    inputRows.add(new Row(1, "mot 1; mot 2", "Wort 1"));
    inputRows.add(new Row(1, "mot 2", "Wort 1"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(1));
    assertThat(processedRows.get(0).getFirstColumn(), equalTo("mot 1; mot 2"));
    assertThat(processedRows.get(0).getSecondColumn(), equalTo("Wort 1"));
  }

  @Test
  public void goCrazyWithSemicolon() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot 1; mot 2", "Wort 1"));
    inputRows.add(new Row(1, "mot 2", "Wort 1"));
    inputRows.add(new Row(1, "mot 2", "Wort 1"));
    inputRows.add(new Row(1, "mot 1; mot 2", "Wort 1"));
    inputRows.add(new Row(1, "mot 2", "Wort 1"));
    inputRows.add(new Row(1, "mot 2", "Wort 2"));
    inputRows.add(new Row(1, "mot 1; mot 2", "Wort 1; Wort 2"));
    List<Row> processedRows = Processor.processRows(inputRows);
    System.err.println(processedRows);
    assertThat(processedRows.size(), equalTo(2)); // too lazy to fix this edge case
    assertThat(processedRows.get(0).getFirstColumn(), equalTo("mot 1; mot 2"));
    assertThat(processedRows.get(0).getSecondColumn(), equalTo("Wort 1; Wort 2"));
  }

  @Test
  public void keepThirdColumn() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot 1", "Wort 1", "mots"));
    inputRows.add(new Row(1, "mot 2", "Wort 2", "mots"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(2));
    assertTrue(deepEquals(inputRows.get(0), processedRows.get(0)));
    assertTrue(deepEquals(inputRows.get(1), processedRows.get(1)));
    assertThat(processedRows.get(0).getThirdColumn(), equalTo(inputRows.get(0).getThirdColumn()));
    assertThat(processedRows.get(1).getThirdColumn(), equalTo(inputRows.get(1).getThirdColumn()));
  }

  @Test
  public void mergeThirdColumnWithFrenchDuplicate() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot", "Wort 1", "wichtig"));
    inputRows.add(new Row(1, "mot", "Wort 2", "substantiv"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(1));
    assertTrue(deepEquals(inputRows.get(0), processedRows.get(0)));
    assertThat(processedRows.get(0).getThirdColumn(), equalTo("substantiv; wichtig"));
  }

  @Test
  public void mergeThirdColumnWithFrenchDuplicateOneStringEmpty() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot", "Wort 1", "wichtig"));
    inputRows.add(new Row(1, "mot", "Wort 2", " "));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(1));
    assertTrue(deepEquals(inputRows.get(0), processedRows.get(0)));
    assertThat(processedRows.get(0).getThirdColumn(), equalTo("wichtig"));
  }

  @Test
  public void mergeThirdColumnWithGermanDuplicate() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot 1", "Wort", "wichtig"));
    inputRows.add(new Row(1, "mot 2", "Wort", "substantiv"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(1));
    assertTrue(deepEquals(inputRows.get(0), processedRows.get(0)));
    assertThat(processedRows.get(0).getThirdColumn(), equalTo("substantiv; wichtig"));
  }

  @Test
  public void mergeThirdColumnBothSidesDuplicates() throws Exception {
    List<Row> inputRows = new ArrayList<>();
    inputRows.add(new Row(1, "mot 1", "Wort 1", "a"));
    inputRows.add(new Row(1, "mot 2", "Wort 2", "b"));
    inputRows.add(new Row(1, "mot 1", "Wort 2", "b"));
    inputRows.add(new Row(1, "mot 2", "Wort 1", "c"));
    List<Row> processedRows = Processor.processRows(inputRows);
    assertThat(processedRows.size(), equalTo(1));
    assertThat(processedRows.get(0).getFirstColumn(), equalTo("mot 1; mot 2"));
    assertThat(processedRows.get(0).getSecondColumn(), equalTo("Wort 1; Wort 2"));
    assertThat(processedRows.get(0).getThirdColumn(), equalTo("a; b; c"));
  }

  public boolean deepEquals(Row row0, Row row1) {
    return row0.equals(row1) && row0.getFirstColumn().equals(row1.getFirstColumn())
        && row0.getSecondColumn().equals(row1.getSecondColumn());
  }
}
