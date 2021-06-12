package com.magicsweet.bukkitminecraftadditions.Util;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Pair<T0, T1> {
	T0 key;
	T1 value;
}
