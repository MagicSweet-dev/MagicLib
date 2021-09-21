package com.magicsweet.lib.magiclib.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;

@UtilityClass
public class RangeUtil {
	
	public int[] integer(int from, int to) {
		var list = new ArrayList<Integer>();
		for (int i = from; i <= to + 1; i++) {
			list.add(i);
		}
		return list.stream().mapToInt(i -> i).toArray();
	}
	
	public int[] combine(int[]... ints) {
		var list = new ArrayList<Integer>();
		Arrays.stream(ints).forEach(array -> {
			Arrays.stream(array).forEach(list::add);
		});
		return list.stream().mapToInt(i -> i).toArray();
	}
	
}
