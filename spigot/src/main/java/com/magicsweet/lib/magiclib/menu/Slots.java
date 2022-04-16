package com.magicsweet.lib.magiclib.menu;


import java.util.function.Function;
import java.util.function.Supplier;
import org.bukkit.entity.Player;

public class Slots {
	Menu menu;
	
	public Slots(Menu menu) {
		this.menu = menu;
	}
	
	public Function<Player, Integer> center() {
		return it -> menu.getInventory(it).getSize() / 2 - 5;
	}
	
	public Function<Player, Integer> topRightCorner() {
		return it -> 8;
	}
	
	public Function<Player, Integer> topLeftCorner() {
		return it -> 0;
	}
	
	public Function<Player, Integer> bottomRightCorner() {
		return it -> (menu.getInventory(it).getSize() - 1);
	}
	
	public Function<Player, Integer> bottomLeftCorner() {
		return it -> (((menu.getInventory(it).getSize() + 1) / 9) - 1) * 9;
	}
	
}
