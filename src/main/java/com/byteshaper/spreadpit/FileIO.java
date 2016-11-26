package com.byteshaper.spreadpit;

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
	
//    private static final String DOC_PATH_FOR_READING = "/home/henning/Dropbox/Französisch/FranzösischVokabelnKopieFürSpreadpit.ods";
    private static final String DOC_PATH_FOR_READING = "/home/elhefe/Dropbox/Französisch/FranzösischVokabelnKopieFürSpreadpit.ods";
    
    private static final String DOC_PATH_FOR_WRITING = "/home/elhefe/Dropbox/Französisch/FranzösischVokabelnProcessed.ods";
    
	public static List<Row> readRows() throws Exception {
		SpreadsheetDocument doc = SpreadsheetDocument.loadDocument(DOC_PATH_FOR_READING); // continue
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
	
	public static void write(List<Row> rows) throws Exception {
		SpreadsheetDocument doc = SpreadsheetDocument.newSpreadsheetDocument();
		//doc.addTable();
		Table sheet = doc.getSheetByIndex(0);
//		sheet.appendColumns(3);
//		sheet.appendRows(rows.size());
		
		for(int rowIdx = 1; rowIdx <= rows.size(); rowIdx++) {
		    Row row = rows.get(rowIdx - 1);
			sheet.getCellByPosition("A" + rowIdx).setStringValue(row.getFirstColumn());
			sheet.getCellByPosition("B" + rowIdx).setStringValue(row.getSecondColumn());
			sheet.getCellByPosition("C" + rowIdx).setStringValue(row.getThirdColumn());
		}
		
		doc.save(DOC_PATH_FOR_WRITING);
	}
}
