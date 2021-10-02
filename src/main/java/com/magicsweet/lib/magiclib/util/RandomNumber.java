package com.magicsweet.lib.magiclib.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class RandomNumber {
	
	public int generateInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public Collection<Integer> generateInts(int min, int max, int count) {
		//not the most effective but works
		Collection<Integer> collection = new ArrayList<Integer>();
		if (max - min + 1 >= count) {
			for (int t = 0; t < count; t++) {
				int num = ThreadLocalRandom.current().nextInt(min, max + 1);
				if (collection.contains(num)) {
					t--;
				} else {
					collection.add(num);
				}
				
			}
			return collection;
		} else {
			System.out.println("Total number count can't be greater than requested number count!");
			return null;
		}
		
	}
	
	public Collection<Integer> generateCompleteInts(int min, int max, int count) {
		//not the most effective but works
		Collection<Integer> collection = new ArrayList<Integer>();
		collection.add(ThreadLocalRandom.current().nextInt(min, max + 1));
		return collection;
	}
	
	public int fromString(String range) {
		range = range.replace(" ", "");
		if (range.contains("-")) {
			var split = range.split("-");
			return generateInt(Integer.parseInt(split[0]), Integer.parseInt(split[split.length - 1]));
		}
		if (range.contains("/")) {
			var split = range.split("/");
			return Integer.parseInt(split[generateInt(0, split.length - 1)]);
		}
		return Integer.parseInt(range);
	}
	
}
