package com.byteshaper.spreadpit;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Processor {

    public static void main(String[] args) throws Exception {
        List<Word> words = FileParser.readWords();
        Set<String> frenchMeanings = words.stream().map(w -> w.getFirstColumn()).collect(Collectors.toSet());
        System.out.println(words.size() + " words, " + frenchMeanings.size() + " unique french => " + (words.size()-frenchMeanings.size()) + " duplicates.");
    }

}
