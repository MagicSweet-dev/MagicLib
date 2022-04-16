package com.magicsweet.lib.magiclib.event;

import com.magicsweet.lib.magiclib.MagicLib;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;


public abstract class AbstractEventListener implements Listener {
	
	public AbstractEventListener() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MagicLib.getInstance());
	}
}
