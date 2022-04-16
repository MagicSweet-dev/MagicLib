package com.magicsweet.lib.magiclib.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Fires every tick server is alive
 */
@Getter
@RequiredArgsConstructor
public class ServerTickEvent extends Event {
	/**
	 * Tick number since MagicLib initialization
	 */
	@NotNull Integer tickNumber;
	
	@Getter private static final HandlerList handlerList = new HandlerList();
	
	protected ServerTickEvent(int tick, boolean b) {
		super(b);
		this.tickNumber = tick;
	}
	
	@NonNull
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
}
