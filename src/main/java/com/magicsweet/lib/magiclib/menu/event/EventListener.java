package com.magicsweet.lib.magiclib.menu.event;

import com.magicsweet.lib.magiclib.event.AbstractEventListener;
import com.magicsweet.lib.magiclib.menu.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class EventListener extends AbstractEventListener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getHolder() instanceof Menu && event.getClickedInventory() != null) {
			Menu inv = (Menu) event.getInventory().getHolder();
			event.setCancelled(true);
			inv.handleClick(event);
		}
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if (event.getInventory().getHolder() instanceof Menu) {
			Menu inv = (Menu) event.getInventory().getHolder();
			inv.handleOpen(event);
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getInventory().getHolder() instanceof Menu) {
			Menu inv = (Menu) event.getInventory().getHolder();
			inv.handleClose(event);
		}
	}

}
