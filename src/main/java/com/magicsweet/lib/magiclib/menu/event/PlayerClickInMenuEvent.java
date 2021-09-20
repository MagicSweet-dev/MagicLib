package com.magicsweet.lib.magiclib.menu.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@Getter
public class PlayerClickInMenuEvent extends InventoryClickEvent {
	Player player;
	
	public PlayerClickInMenuEvent(InventoryClickEvent event) {
		super(event.getView(), event.getSlotType(), event.getRawSlot(), event.getClick(), event.getAction(), event.getHotbarButton());
		this.player = (Player) event.getWhoClicked();
	}
}
