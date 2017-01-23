package com.byteshaper.spreadpit;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

/**
 * Reads from and write to file.
 * 
 * @author Henning Schütz <henning@byteshaper.com>
 *
 */
public class FileIO {
  
  private static final String COL_A = "A";
  
  private static final String COL_B = "B";
  
  private static final String COL_C = "C";

  public static List<Row> readRows(Path inputPath) throws Exception {
    SpreadsheetDocument doc = SpreadsheetDocument.loadDocument(inputPath.toString());
    Table sheet = doc.getSheetByIndex(0);
    List<Row> words = new ArrayList<>();

    for (int rowIdx = 1; rowIdx <= sheet.getRowCount(); rowIdx++) {
      Row word = new Row(rowIdx);
      word.setFirstColumn(sheet.getCellByPosition(COL_A + rowIdx).getStringValue().trim());
      word.setSecondColumn(sheet.getCellByPosition(COL_B + rowIdx).getStringValue().trim());
      word.setThirdColumn(sheet.getCellByPosition(COL_C + rowIdx).getStringValue().trim());
      words.add(word);
    }

    return words;
  }

  public static void write(Path outputPath, List<Row> rows) throws Exception {
    SpreadsheetDocument doc = SpreadsheetDocument.newSpreadsheetDocument();
    Table sheet = doc.getSheetByIndex(0);

    for (int rowIdx = 1; rowIdx <= rows.size(); rowIdx++) {
      Row row = rows.get(rowIdx - 1);
      sheet.getCellByPosition(COL_A + rowIdx).setStringValue(row.getFirstColumn());
      sheet.getCellByPosition(COL_B + rowIdx).setStringValue(row.getSecondColumn());
      sheet.getCellByPosition(COL_C + rowIdx).setStringValue(row.getThirdColumn());
    }

    doc.save(outputPath.toString());
  }
}
