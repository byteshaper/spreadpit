package com.byteshaper.spreadpit;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Meaning {
	
	public static Meaning create(String commaSeparatedWords) {
		return new Meaning(Arrays.stream(commaSeparatedWords.split(";")).map(w -> w.trim()).collect(Collectors.toSet()));
	}
	
	private Meaning(Set<String> words) {
		this.words = words;
	}
	
	private final Set<String> words;

	public Set<String> getWords() {
		return words;
	}

	
	@Override
	public String toString() {
		return "{{" + words + "}}";
	}
}
