package com.byteshaper.spreadpit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Meaning {
	
	private static final List<Character> BRACKET_OPEN_CHARS = Arrays.asList('{', '[', '(');
	
	private static final List<Character> BRACKET_CLOSE_CHARS = Arrays.asList('}', ']', ')');
	
	public static Meaning create(String commaSeparatedWords) {
		return new Meaning(stringToWords(commaSeparatedWords));
	}
	
	public static Set<Meaning> mergeMeanings(List<Meaning> meanings0, List<Meaning>meanings1) {
		List<Meaning> allMeanings = new ArrayList<>(meanings0);
		allMeanings.addAll(meanings1);
		Set<Meaning> resultSet = new LinkedHashSet<>();
		
		for(Meaning meaning: allMeanings) {
			Optional<Meaning> related = resultSet.stream().filter(m -> m.hasSomethingInCommonWith(meaning)).findAny();
			
			if(related.isPresent()) {
				resultSet.remove(related.get());
				resultSet.add(related.get().createMergedInstance(meaning));
			} else {
				resultSet.add(meaning);
			}
		}
		
		return resultSet;
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
		HashSet<String> words = new LinkedHashSet<>(this.words);
		words.addAll(other.getWords());
		return new Meaning(words);
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
