package com.magicsweet.lib.magiclib.event;

import com.magicsweet.lib.magiclib.MagicLib;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public interface InterfacedEventListener extends Listener {
	default void listen() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MagicLib.getInstance());
	}
}
