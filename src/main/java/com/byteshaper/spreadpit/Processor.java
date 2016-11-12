package com.byteshaper.spreadpit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Processor {

	public static void main(String[] args) throws Exception {
		List<Row> processedWords = processRows();
	}
	
    public static List<Row> processRows() throws Exception {
    	List<Row> processedRows = new ArrayList<>();
        List<Row> rows = FileParser.readWords();
        Map<String, Row> byFrench = new HashMap<>();
        Map<String, Row> byGerman = new HashMap<>();
        int frenchDuplicateCount = 0;
        int germanDuplicateCount = 0;
        int bothDuplicateCount = 0;
        int frenchEmptyCount = 0;
        int germanEmptyCount = 0;
        
        System.out.println("Starting analyze:");
        
        for(Row row: rows) {
        	
        	Optional<Row> frenchDup = processedRows.stream()
        		.filter(w -> w.getFirstColumn().equals(row.getFirstColumn()))
        		.findFirst();
        	
        	Optional<Row> germanDup = processedRows.stream()
            		.filter(w -> w.getSecondColumn().equals(row.getSecondColumn()))
            		.findFirst();
        	
        	if(frenchDup.isPresent() && !germanDup.isPresent()) {
        		System.out.println("Dup line: " + frenchDup.get().getLineNumber());
        		System.out.println(parseMeanings(frenchDup.get().getSecondColumn()));
        		System.out.println("Row line:  " + row.getLineNumber());
        		System.out.println(parseMeanings(row.getSecondColumn()));
        	} else if(germanDup.isPresent() && !frenchDup.isPresent()) {
        		
        	} else if(!frenchDup.isPresent() && !germanDup.isPresent()) {
        		processedRows.add(row);
        	}
        		
        	
        	/*
        	if(byFrench.containsKey(w.getFirstColumn())) {
        		if(byGerman.containsKey(w.getSecondColumn())) {
        			printBothDuplicate(w);
        			bothDuplicateCount++;
        		} else {
        			printFrenchDuplicate(w, byFrench.get(w.getFirstColumn()));
        			byGerman.put(w.getSecondColumn(), w);
        			frenchDuplicateCount++;
        		}
        	} else if(byGerman.containsKey(w.getSecondColumn())) {
    			printGermanDuplicate(w, byGerman.get(w.getSecondColumn()));
    			germanDuplicateCount++;
    			byFrench.put(w.getFirstColumn(), w);
    		} 
        	
        	else {
        		byFrench.put(w.getFirstColumn(), w);
        		byGerman.put(w.getSecondColumn(), w);
        	}
        	
        	if(w.getFirstColumn().equals("")) {
        		frenchEmptyCount++;
        		System.out.println("French empty: " + w.getSecondColumn() + " / line: " + w.getLineNumber());
        	}
        	
        	if(w.getSecondColumn().equals("")) {
        		germanEmptyCount++;
        		System.out.println("German empty: " + w.getFirstColumn() + " / line: " + w.getLineNumber());
        	} 
        */	
        }
        
        System.out.println("French duplicates: " + frenchDuplicateCount);
        System.out.println("German duplicates: " + germanDuplicateCount);
        System.out.println("Both   duplicates: " + bothDuplicateCount);
        System.out.println("French empty: " + frenchEmptyCount);
        System.out.println("German empty: " + germanEmptyCount);
        return processedRows;
        
    }
    
    private static Set<Meaning> parseMeanings(String cellContent) {
    	Set<Meaning> meanings = new HashSet<>();
    	Arrays.stream(cellContent.split(";")).forEach(s -> meanings.add(Meaning.create(s)));	
    	return meanings;
    }
    
    private static void printFrenchDuplicate(Row w0, Row w1) {
    	System.out.println("Found french duplicate: " + w0.getFirstColumn() + ": " + w0.getSecondColumn() + " // "
				+ w1.getSecondColumn());
    }
    
    private static void printGermanDuplicate(Row w0, Row w1) {
    	System.out.println("Found german duplicate: " + w0.getSecondColumn() + ": " + w0.getFirstColumn() + " // "
				+ w1.getFirstColumn());
    }
    
    private static void printBothDuplicate(Row w) {
    	System.out.println("Found both duplicate: " + w.getFirstColumn() + ": " + w.getSecondColumn());
    }

}
