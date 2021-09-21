package com.magicsweet.lib.magiclib.util;

@FunctionalInterface
public interface TriFunction<A, B, C, D> {
	public D run(A a, B b, C c);
}
