package com.magicsweet.bukkitminecraftadditions.Expression;

@Deprecated
public class LambdaExpression<T0> {
	
	public T0 run(LambdaExpressionConsumer<T0> consumer) {
		return consumer.run();
	}
}
