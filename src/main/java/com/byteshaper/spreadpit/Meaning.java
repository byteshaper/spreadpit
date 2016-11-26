package com.byteshaper.spreadpit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Meaning {
	
	private static final List<Character> BRACKET_OPEN_CHARS = Arrays.asList('{', '[', '(');
	
	private static final List<Character> BRACKET_CLOSE_CHARS = Arrays.asList('}', ']', ')');
	
	public static Meaning create(String commaSeparatedWords) {
		return new Meaning(stringToWords(commaSeparatedWords));
	}
	
	public static Set<Meaning> mergeMeanings(List<Meaning> meanings0, List<Meaning>meanings1) {
		List<Meaning> allMeanings = new ArrayList<>(meanings0);
		allMeanings.addAll(meanings1);		
		allMeanings = allMeanings
				.stream()
				.sorted((m0, m1) -> m0.words.stream()
						.reduce(wordsReducer()).orElse("")
						.compareTo(m1.words.stream().reduce(wordsReducer()).orElse("")) )
				.collect(Collectors.toList());
		
		Set<Meaning> resultSet = new LinkedHashSet<>();
		
		
		for(Meaning meaning: allMeanings) {
			Optional<Meaning> related = resultSet.stream().filter(m -> m.hasSomethingInCommonWith(meaning)).findFirst();
			
			if(related.isPresent()) {
				resultSet.remove(related.get());
				resultSet.add(related.get().createMergedInstance(meaning));
			} else {
				resultSet.add(meaning);
			}
		}
		
		return resultSet;
	}
	
	private static BinaryOperator<String> wordsReducer() {
		return (s0, s1) -> s0 + "," + s1;
	}
	
	private Meaning(Set<String> words) {
		this.words = words;
	}
	
	private final Set<String> words;

	public Set<String> getWords() {
		return words;
	}
	
	public boolean hasSomethingInCommonWith(Meaning other) {
		return words.stream().anyMatch(w -> other.getWords().contains(w));
	}
	
	public Meaning createMergedInstance (Meaning other) {
		Set<String> words = new LinkedHashSet<>(other.getWords());
		words.addAll(this.words);
		return new Meaning(words.stream().sorted().collect(Collectors.toCollection(() -> new LinkedHashSet<>())));
	}
	
	@Override
	public String toString() {
		return "{{" + words + "}}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((words == null) ? 0 : words.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meaning other = (Meaning) obj;
		if (words == null) {
			if (other.words != null)
				return false;
		} else if (!words.equals(other.words))
			return false;
		return true;
	}
	
	private static Set<String> stringToWords(String commaseparatedWords) {
		Set<String> words = new LinkedHashSet<>();
		char[] characters = commaseparatedWords.toCharArray();
		boolean bracketOpen = false;
		StringBuilder word = new StringBuilder();
		
		for(char character: characters) {
			if(BRACKET_OPEN_CHARS.contains(character)) {
				bracketOpen = true;
			} else if(BRACKET_CLOSE_CHARS.contains(character)) {
				bracketOpen = false;
			}
			
			if(character == ',' && !bracketOpen) {
				words.add(word.toString().trim());
				word = new StringBuilder();
			} else {
				word.append(character);
			}
		}
		
		words.add(word.toString().trim());
		return words;
	}
}
