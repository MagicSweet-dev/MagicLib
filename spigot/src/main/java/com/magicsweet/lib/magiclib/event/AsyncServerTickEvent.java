package com.magicsweet.lib.magiclib.event;

/**
 * Fires every tick server is alive
 * This event is running asynchronously
 */
public class AsyncServerTickEvent extends ServerTickEvent {
	public AsyncServerTickEvent(int tick) {
		super(tick, true);
	}
}
