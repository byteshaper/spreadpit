package com.byteshaper.spreadpit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Processor {

	public static void main(String[] args) throws Exception {
		List<Row> processedWords = processRows(FileParser.readRows());
	}
	
    public static List<Row> processRows(List<Row> rows) throws Exception {
    	List<Row> processedRows = new ArrayList<>();
        
        System.out.println("Starting analyze:");
        
        for(Row row: rows) {
        	
        	Optional<Row> frenchDup = processedRows.stream()
        		.filter(w -> w.getFirstColumn().equals(row.getFirstColumn()))
        		.findFirst();
        	
        	Optional<Row> germanDup = processedRows.stream()
            		.filter(w -> w.getSecondColumn().equals(row.getSecondColumn()))
            		.findFirst();
        	
        	if(frenchDup.isPresent() && !germanDup.isPresent()) {
        		List<Meaning> germanMeanings0 = parseMeanings(frenchDup.get().getSecondColumn());
        		List<Meaning> germanMeanings1 = parseMeanings(row.getSecondColumn());
        		System.out.println("German meanings 0: " + germanMeanings0);
        		System.out.println("German meanings 1: " + germanMeanings1);
        		System.out.println("Merged: " + meaningsToColumn(Meaning.mergeMeanings(germanMeanings0, germanMeanings1)));
        		frenchDup.get().setSecondColumn(meaningsToColumn(Meaning.mergeMeanings(germanMeanings0, germanMeanings1)));        		
        	} else if(germanDup.isPresent() && !frenchDup.isPresent()) {
        		List<Meaning> frenchMeanings0 = parseMeanings(germanDup.get().getFirstColumn());
        		List<Meaning> frenchMeanings1 = parseMeanings(row.getFirstColumn());
        		System.out.println("French meanings 0: " + frenchMeanings0);
        		System.out.println("French meanings 1: " + frenchMeanings1);
        		System.out.println("Merged: " + meaningsToColumn(Meaning.mergeMeanings(frenchMeanings0, frenchMeanings1)));
        		germanDup.get().setFirstColumn(meaningsToColumn(Meaning.mergeMeanings(frenchMeanings0, frenchMeanings1)));
        	} else if(germanDup.isPresent() && frenchDup.isPresent()) {
        		System.out.println("***** DOUBLE *******");
        		List<Meaning> germanMeanings0 = parseMeanings(frenchDup.get().getSecondColumn());
        		List<Meaning> germanMeanings1 = parseMeanings(row.getSecondColumn());
        		List<Meaning> frenchMeanings0 = parseMeanings(germanDup.get().getFirstColumn());
        		List<Meaning> frenchMeanings1 = parseMeanings(row.getFirstColumn());
        		System.out.println("French meanings 0: " + frenchMeanings0);
        		System.out.println("French meanings 1: " + frenchMeanings1);
        		System.out.println("German meanings 0: " + germanMeanings0);
        		System.out.println("German meanings 1: " + germanMeanings1);
        		System.out.println("Merged 1: " + meaningsToColumn(Meaning.mergeMeanings(germanMeanings0, germanMeanings1)));
        		System.out.println("Merged 2: " + meaningsToColumn(Meaning.mergeMeanings(frenchMeanings0, frenchMeanings1)));
        		frenchDup.get().setFirstColumn(meaningsToColumn(Meaning.mergeMeanings(frenchMeanings0, frenchMeanings1)));
        		germanDup.get().setSecondColumn(meaningsToColumn(Meaning.mergeMeanings(germanMeanings0, germanMeanings1)));
        	}
        	
        	else if(!frenchDup.isPresent() && !germanDup.isPresent()) {
        		processedRows.add(row);
        	}
        }
        
        System.out.println("Reduced " + rows.size() + " rows to " + processedRows.size());
        return processedRows;
        
    }
    
    private static String meaningsToColumn(Set<Meaning> meanings) {
    	System.out.println("\tmeanings2col: " + meanings);
    	return meanings
    			.stream()
    			.map(m -> m.getWords().stream().reduce((w0, w1) -> w0 + ", " + w1).orElse(""))
    			.reduce((m0, m1) -> m0 + "; " + m1).orElse("");
    }
    
    private static List<Meaning> parseMeanings(String cellContent) {
    	List<Meaning> meanings = new ArrayList<>();
    	Arrays.stream(cellContent.split(";")).forEach(s -> meanings.add(Meaning.create(s)));	
    	return meanings;
    }
}
