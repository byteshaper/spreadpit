package com.byteshaper.spreadpit;

import java.util.ArrayList;
import java.util.List;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;


public class FileParser {
	
//    private static final String DOC_PATH = "/home/henning/Dropbox/Französisch/FranzösischVokabelnKopieFürSpreadpit.ods";
    private static final String DOC_PATH = "/home/elhefe/Dropbox/Französisch/FranzösischVokabelnKopieFürSpreadpit.ods";
	public static List<Word> readWords() throws Exception {
		SpreadsheetDocument doc = SpreadsheetDocument.loadDocument(DOC_PATH); // continue
		Table sheet = doc.getSheetByIndex(0);
		List<Word> words = new ArrayList<>();
		
		for(int rowIdx = 1; rowIdx <= sheet.getRowCount(); rowIdx++) {
		    Word word = new Word(rowIdx);
		    word.setFirstColumn(sheet.getCellByPosition("A" + rowIdx).getStringValue());
		    word.setSecondColumn(sheet.getCellByPosition("B" + rowIdx).getStringValue());
		    word.setThirdColumn(sheet.getCellByPosition("C" + rowIdx).getStringValue());
		    words.add(word);
		}
		
		return words;
	}
}
