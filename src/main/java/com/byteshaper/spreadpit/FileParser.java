package com.byteshaper.spreadpit;

import java.util.ArrayList;
import java.util.List;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;


public class FileParser {
	
//    private static final String DOC_PATH = "/home/henning/Dropbox/Französisch/FranzösischVokabelnKopieFürSpreadpit.ods";
    private static final String DOC_PATH = "/home/elhefe/Dropbox/Französisch/FranzösischVokabelnKopieFürSpreadpit.ods";
	public static List<Row> readWords() throws Exception {
		SpreadsheetDocument doc = SpreadsheetDocument.loadDocument(DOC_PATH); // continue
		Table sheet = doc.getSheetByIndex(0);
		List<Row> words = new ArrayList<>();
		
		for(int rowIdx = 1; rowIdx <= sheet.getRowCount(); rowIdx++) {
		    Row word = new Row(rowIdx);
		    word.setFirstColumn(sheet.getCellByPosition("A" + rowIdx).getStringValue().trim());
		    word.setSecondColumn(sheet.getCellByPosition("B" + rowIdx).getStringValue().trim());
		    word.setThirdColumn(sheet.getCellByPosition("C" + rowIdx).getStringValue().trim());
		    words.add(word);
		}
		
		return words;
	}
}
