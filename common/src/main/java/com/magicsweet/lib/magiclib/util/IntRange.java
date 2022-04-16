package com.magicsweet.lib.magiclib.util;

import java.util.ArrayList;
import java.util.List;

public class IntRange {
	public List<Integer> available = new ArrayList<>();
	
	public void clear() {
		available.clear();
	}
	
	public int random() {
		if (available.size() == 0) return 0;
		return available.get(RandomNumber.generateInt(0, available.size() - 1));
	}
	
	public int first() {
		if (available.size() == 0) return 0;
		return available.get(0);
	}
	
	public int last() {
		if (available.size() == 0) return 0;
		return available.get(available.size() - 1);
	}
	
	public int highest() {
		return available.stream().mapToInt(i -> i).max().orElse(0);
	}
	
	public int lowest() {
		return available.stream().mapToInt(i -> i).min().orElse(0);
	}
	
	public IntRange append(int first, int last) {
		for (int i = first; i <= last; i++) {
			available.add(i);
		}
		return this;
	}
	
	public IntRange append(int number) {
		available.add(number);
		return this;
	}
	
	public List<Integer> getAllAvailable() {
		return available;
	}
	
	public static IntRange fromString(String string) {
		string = string.replace(" ", "");
		
		var intr = new IntRange();
		
		for (var range: string.split(",")) {
			if (range.contains("-")) {
				var split = range.split("-");
				intr.append(Integer.parseInt(split[0]), Integer.parseInt(split[split.length - 1]));
			} else if (range.contains("/")) {
				var split = range.split("/");
				for (var i: split) {
					intr.append(Integer.parseInt(i));
				}
			} else {
				intr.append(Integer.parseInt(range));
			}
		}
		
		return intr;
	}
}
