package com.magicsweet.lib.magiclib.menu.event;

import com.magicsweet.lib.magiclib.MagicLib;
import com.magicsweet.lib.magiclib.annotations.LoadAtStartup;
import com.magicsweet.lib.magiclib.event.AbstractEventListener;
import com.magicsweet.lib.magiclib.menu.Menu;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@LoadAtStartup
public class EventListener extends AbstractEventListener {
	@Getter static List<Consumer<Player>> cleanup = new CopyOnWriteArrayList<>();
	
	@EventHandler
	public void event(InventoryClickEvent event) {
		if (event.getInventory().getHolder() instanceof Menu && event.getClickedInventory() != null) {
			Menu inv = (Menu) event.getInventory().getHolder();
			event.setCancelled(true);
			inv.handleClick(event);
		}
	}
	
	@EventHandler
	public void event(InventoryOpenEvent event) {
		if (event.getInventory().getHolder() instanceof Menu) {
			Menu inv = (Menu) event.getInventory().getHolder();
			inv.handleOpen(event);
		}
	}
	
	@EventHandler
	public void event(InventoryCloseEvent event) {
		if (event.getInventory().getHolder() instanceof Menu) {
			Menu inv = (Menu) event.getInventory().getHolder();
			inv.handleClose(event);
		}
	}
	
	@EventHandler
	public void event(PlayerQuitEvent event) {
		MagicLib.getExecutor().execute(() -> cleanup.forEach(it -> it.accept(event.getPlayer())));
	}
	
}
