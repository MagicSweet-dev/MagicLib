package com.magicsweet.lib.magiclib.menu;

import com.magicsweet.lib.magiclib.event.AbstractEventListener;
import com.magicsweet.lib.magiclib.menu.item.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Fitter extends AbstractEventListener {
	
	Function<Player, List<Object>> objectList;
	BiFunction<Player, Object, MenuItem> mapper = (player, object) -> (MenuItem) object;
	Function<Player, int[]> slots;
	
	BiFunction<Menu, Player, Integer> previousPageItemSlot = (menu, pl) -> menu.slot().bottomLeftCorner().apply(pl);
	BiFunction<Menu, Player, Integer> nextPageItemSlot = (menu, pl) -> menu.slot().bottomRightCorner().apply(pl);
	
	
	private HashMap<Player, Integer> currentPagePositions = new HashMap<>();
	
	public Fitter(Function<Player, List<Object>> objects, Function<Player, int[]> slots) {
		this.objectList = objects;
		this.slots = slots;
	}
	
	public Fitter into(Function<Player, int[]> slots) {
		this.slots = slots;
		return this;
	}
	
	public Fitter into(int... slots) {
		return into(player -> slots);
	}
	
	public Fitter into(List<Integer> slots) {
		return into(player -> slots.stream().mapToInt(i -> i).toArray());
	}
	
	
	public <T> Fitter mapBoth(BiFunction<Player, T, MenuItem> mapper) {
		this.mapper = (player, it) -> mapper.apply(player, (T) it);
		return this;
	}
	
	public <T> Fitter map(Function<T, MenuItem> mapper) {
		this.mapper = (player, it) -> mapper.apply((T) it);
		return this;
	}
	
	public HashMap<Integer, MenuItem> getPageContents(Player player, int page) {
		var map = new HashMap<Integer, MenuItem>();
		
		var objectList = this.objectList.apply(player);
		var slots = Arrays.stream(this.slots.apply(player)).boxed().collect(Collectors.toList());
		
		var ii = 0;
		for (int i = page * slots.size(); i < objectList.size(); i++) {
			if (ii >= slots.size()) break;
			map.put(slots.get(ii), mapper.apply(player, objectList.get(i)));
			ii = ii + 1;
		}
		
		return map;
	}
	
	public HashMap<Integer, MenuItem> getCurrentPageContents(Player player) {
		return getPageContents(player, getCurrentPage(player));
	}
	
	public int getCurrentPage(Player player) {
		return Optional.ofNullable(this.currentPagePositions.get(player)).orElse(0);
	}
	
	public void flipPage(Player player) {
		this.currentPagePositions.put(player, getCurrentPage(player) + 1);
	}
	
	public void flipPageBack(Player player) {
		this.currentPagePositions.put(player, getCurrentPage(player) <= 0 ? 0 : getCurrentPage(player) - 1);
	}
	
	public boolean isPageAvailable(Player player, int page) {
		if (page < 0) return false;
		if (getPageContents(player, page).isEmpty()) return false;
		return getPageCount(player) >= page;
	}
	
	public int getPageCount(Player player) {
		return objectList.apply(player).size() / slots.apply(player).length;
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		this.currentPagePositions.remove(event.getPlayer());
	}
	
	public BiFunction<Menu, Player, Integer> previousPageItemSlot() {
		return this.previousPageItemSlot;
	}
	
	public Fitter previousPageItemSlot(BiFunction<Menu, Player, Integer> previousPageItemSlot) {
		this.previousPageItemSlot = previousPageItemSlot;
		return this;
	}
	
	public Fitter previousPageItemSlot(int previousPageItemSlot) {
		this.previousPageItemSlot = (menu, pl) -> previousPageItemSlot;
		return this;
	}
	
	public BiFunction<Menu, Player, Integer> nextPageItemSlot() {
		return this.nextPageItemSlot;
	}
	
	public Fitter nextPageItemSlot(BiFunction<Menu, Player, Integer> nextPageItemSlot) {
		this.nextPageItemSlot = nextPageItemSlot;
		return this;
	}
	
	public Fitter nextPageItemSlot(int nextPageItemSlot) {
		this.nextPageItemSlot = (menu, pl) -> nextPageItemSlot;
		return this;
	}
}
