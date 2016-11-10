package com.byteshaper.spreadpit;

import java.util.List;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;


public class FileParser {
	
    private static final String DOC_PATH = "/home/henning/Dropbox/Französisch/FranzösischVokabelnKopieFürSpreadpit.ods";
    
    public static void main(String[] args) throws Exception {
        readWords();
    }
    
	public static List<Word> readWords() throws Exception {
		SpreadsheetDocument doc = SpreadsheetDocument.loadDocument(DOC_PATH); // continue
		Table sheet = doc.getSheetByIndex(0);
		int maxColumnCount = 0;
		
		for(int rowIdx = 1; rowIdx <= sheet.getRowCount(); rowIdx++) {
		    Cell cell = sheet.getCellByPosition("A" + rowIdx);
		    String frenchWord = cell.getStringValue();
		    System.out.println("french: " + frenchWord);
		}
		
		// System.out.println(sheet.getColumnCount()); => 1024
		
		return null;
	}
}
