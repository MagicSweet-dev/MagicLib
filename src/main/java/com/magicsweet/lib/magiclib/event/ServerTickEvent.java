package com.magicsweet.lib.magiclib.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class ServerTickEvent extends Event {
	@NotNull Integer tickNumber;
	
	private static final HandlerList handlers = new HandlerList();
	
	protected ServerTickEvent(int tick, boolean b) {
		super(b);
		this.tickNumber = tick;
	}
	
	@NonNull
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
