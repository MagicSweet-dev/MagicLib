package com.magicsweet.lib.magiclib.util;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@Builder
public class Pair<T0, T1> {
	T0 key;
	T1 value;
}
