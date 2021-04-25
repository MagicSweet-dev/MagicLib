package com.magicsweet.bukkitminecraftadditions.Util;

@Deprecated
public class ThreadedExecution<T0> {
	
	
	public T0 executeIndependent(ThreadedExecutionLambdaExpression<T0> exp) {
		return exp.run();
	}
	
	public static void execute(ThreadedExecutionLambdaExpression<Object> exp) {
		new Thread(new ThreadedExecutor(exp)).start();
	}
	
	
}
