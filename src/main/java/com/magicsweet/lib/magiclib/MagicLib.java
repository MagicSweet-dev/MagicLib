package com.magicsweet.lib.magiclib;

import com.magicsweet.lib.magiclib.event.AsyncServerTickEvent;
import com.magicsweet.lib.magiclib.event.ServerTickEvent;
import fr.mrmicky.fastinv.FastInvManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class MagicLib extends JavaPlugin {
	@Getter @Setter static JavaPlugin instance;
	
	public MagicLib() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		enable();
	}
	
	public static void enable() {
		FastInvManager.register(instance);
		new BukkitRunnable() {
			
			int tick = 0;
			
			@Override
			public void run() {
				Bukkit.getPluginManager().callEvent(new ServerTickEvent(tick));
				Bukkit.getPluginManager().callEvent(new AsyncServerTickEvent(tick));
				tick = tick + 1;
			}
			
		}.runTaskTimer(instance, 0, 1);
	}
	
}
