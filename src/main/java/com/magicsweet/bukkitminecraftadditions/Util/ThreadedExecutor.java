package com.magicsweet.bukkitminecraftadditions.Util;

@Deprecated
public class ThreadedExecutor implements Runnable {
	ThreadedExecutionLambdaExpression<Object> lam;
	
	public ThreadedExecutor(ThreadedExecutionLambdaExpression<Object> lam) {
		this.lam = lam;
	}
	
	@Override
	public void run() {
		lam.run();
	}
}
