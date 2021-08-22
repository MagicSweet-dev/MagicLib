package com.magicsweet.lib.magiclib;

import fr.mrmicky.fastinv.FastInvManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

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
	}
	
}
