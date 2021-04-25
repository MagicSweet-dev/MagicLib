package com.magicsweet.bukkitminecraftadditions.EventManager;

import com.magicsweet.lib.magiclib.MagicLib;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class ExtendableEvent implements Listener {

	public ExtendableEvent() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MagicLib.getInstance());
	}
}
