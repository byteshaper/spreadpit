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
    	ReduceDuplicatesResult reduceDuplicatesResult = new ReduceDuplicatesResult(rows, true);
        System.out.println("Starting analyze:");
        
        while(reduceDuplicatesResult.duplicatesFound) {
        	reduceDuplicatesResult = reduceDuplicates(reduceDuplicatesResult.rows);
        }
        
        return reduceDuplicatesResult.rows;
    }
    
    private static ReduceDuplicatesResult reduceDuplicates(List<Row> rows) {
    	List<Row> processedRows = new ArrayList<>();
    	boolean duplicatesFound = false;
    	
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
        		frenchDup.get().setSecondColumn(meaningsToColumn(Meaning.mergeMeanings(germanMeanings0, germanMeanings1)));     
        		duplicatesFound = true;
        	} else if(germanDup.isPresent() && !frenchDup.isPresent()) {
        		List<Meaning> frenchMeanings0 = parseMeanings(germanDup.get().getFirstColumn());
        		List<Meaning> frenchMeanings1 = parseMeanings(row.getFirstColumn());
        		germanDup.get().setFirstColumn(meaningsToColumn(Meaning.mergeMeanings(frenchMeanings0, frenchMeanings1)));
        		duplicatesFound = true;
        	} else if(germanDup.isPresent() && frenchDup.isPresent()) {
        		List<Meaning> germanMeanings0 = parseMeanings(frenchDup.get().getSecondColumn());
        		List<Meaning> germanMeanings1 = parseMeanings(row.getSecondColumn());
        		List<Meaning> frenchMeanings0 = parseMeanings(germanDup.get().getFirstColumn());
        		List<Meaning> frenchMeanings1 = parseMeanings(row.getFirstColumn());
        		frenchDup.get().setFirstColumn(meaningsToColumn(Meaning.mergeMeanings(frenchMeanings0, frenchMeanings1)));
        		germanDup.get().setSecondColumn(meaningsToColumn(Meaning.mergeMeanings(germanMeanings0, germanMeanings1)));
        		duplicatesFound = true;
        	}
        	
        	else if(!frenchDup.isPresent() && !germanDup.isPresent()) {
        		processedRows.add(row);
        	}
        }
        
        System.out.println("Reduced " + rows.size() + " rows to " + processedRows.size());
        return new ReduceDuplicatesResult(processedRows, duplicatesFound);
    }
    
    private static String meaningsToColumn(Set<Meaning> meanings) {
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
    
    private static class ReduceDuplicatesResult {
    	
    	public final List<Row> rows;
    	
    	public final boolean duplicatesFound;
    	
    	public ReduceDuplicatesResult(List<Row> result, boolean duplicatesFound) {
    		this.rows = result;
    		this.duplicatesFound = duplicatesFound;
    	}
    }
}
