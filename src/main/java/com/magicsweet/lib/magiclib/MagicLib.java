package com.magicsweet.lib.magiclib;

import fr.mrmicky.fastinv.FastInvManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicLib extends JavaPlugin {
	@Getter static MagicLib instance;
	
	public MagicLib() {
		instance = this;
	}
	
	@Override
	public void onLoad() {
		FastInvManager.register(this);
	}
	
}
