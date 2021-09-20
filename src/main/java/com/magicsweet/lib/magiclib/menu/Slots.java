package com.magicsweet.lib.magiclib.menu;


import java.util.function.Supplier;

public class Slots {
	Menu menu;
	
	public Slots(Menu menu) {
		this.menu = menu;
	}
	
	public Supplier<Integer> center() {
		return () -> menu.getInventory().getSize() / 2 - 5;
	}
	
	public Supplier<Integer> topRightCorner() {
		return () -> 8;
	}
	
	public Supplier<Integer> topLeftCorner() {
		return () -> 0;
	}
	
	public Supplier<Integer> bottomRightCorner() {
		return () -> (menu.getInventory().getSize() - 1);
	}
	
	public Supplier<Integer> bottomLeftCorner() {
		return () -> (((menu.getInventory().getSize() + 1) / 9) - 1) * 9;
	}
	
}
