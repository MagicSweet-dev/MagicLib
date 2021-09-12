package com.magicsweet.lib.magiclib.event;

public class AsyncServerTickEvent extends ServerTickEvent {
	public AsyncServerTickEvent(int tick) {
		super(tick, true);
	}
}
