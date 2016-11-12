package com.byteshaper.spreadpit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Meaning {
	
	public static Meaning create(String commaSeparatedWords) {
		return new Meaning(Arrays
				.stream(commaSeparatedWords.split(","))
				.map(w -> w.trim())
				.collect(Collectors.toSet()));
	}
	
	public static Set<Meaning> mergeMeanings(List<Meaning> meanings0, List<Meaning>meanings1) {
		Set<Meaning> resultSet = new LinkedHashSet<>();
		Set<Meaning> added = new HashSet<>();
		
		for(Meaning m0: meanings0) {
			for(Meaning m1: meanings1) {
				if(m0.hasSomethingInCommonWith(m1)) {
					resultSet.add(m0.createMergedInstance(m1));
					added.add(m0);
					added.add(m1);
				} else if(!added.contains(m1)) {
					resultSet.add(m1);
					added.add(m1);
				}
			}
			
			if(!added.contains(m0)) {
				for(Meaning result: resultSet) {
					if(result.hasSomethingInCommonWith(m0)) {
						resultSet.add(result.createMergedInstance(m0));
						added.add(m0);
					} 
				}
				
				if(!added.contains(m0)) { 
					resultSet.add(m0);
					added.add(m0);
				}
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
		HashSet<String> words = new HashSet<>(this.words);
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
	
	
}
