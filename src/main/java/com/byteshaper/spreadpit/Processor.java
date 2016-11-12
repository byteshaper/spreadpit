package com.byteshaper.spreadpit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {

    public static void main(String[] args) throws Exception {
        List<Word> words = FileParser.readWords();
        Map<String, Word> byFrench = new HashMap<>();
        Map<String, Word> byGerman = new HashMap<>();
        int frenchDuplicateCount = 0;
        int germanDuplicateCount = 0;
        int bothDuplicateCount = 0;
        int frenchEmptyCount = 0;
        int germanEmptyCount = 0;
        
        System.out.println("Starting analyze:");
        
        for(Word w: words) {
        	
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
        }
        
        System.out.println("French duplicates: " + frenchDuplicateCount);
        System.out.println("German duplicates: " + germanDuplicateCount);
        System.out.println("Both   duplicates: " + bothDuplicateCount);
        System.out.println("French empty: " + frenchEmptyCount);
        System.out.println("German empty: " + germanEmptyCount);
        
    }
    
    private static void printFrenchDuplicate(Word w0, Word w1) {
    	System.out.println("Found french duplicate: " + w0.getFirstColumn() + ": " + w0.getSecondColumn() + " // "
				+ w1.getSecondColumn());
    }
    
    private static void printGermanDuplicate(Word w0, Word w1) {
    	System.out.println("Found german duplicate: " + w0.getSecondColumn() + ": " + w0.getFirstColumn() + " // "
				+ w1.getFirstColumn());
    }
    
    private static void printBothDuplicate(Word w) {
    	System.out.println("Found both duplicate: " + w.getFirstColumn() + ": " + w.getSecondColumn());
    }

}
