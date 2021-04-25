package com.magicsweet.bukkitminecraftadditions.Util;


public class Pair<T0, T1> {
	T0 pairObject;
	T1 pairObject2;
	
	public Pair(T0 o, T1 o2) {
		this.pairObject = o;
		this.pairObject2 = o2;
	}
	
	public T0 getKey() {
		return pairObject;
	}
	
	public T1 getValue() {
		return pairObject2;
	}
	
	public void setKey(T0 object) {
		this.pairObject = object;
	}
	
	public void setValue(T1 object) {
		this.pairObject2 = object;
	}
	
	
}
