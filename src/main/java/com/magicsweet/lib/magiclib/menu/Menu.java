package com.magicsweet.lib.magiclib.menu;

import com.magicsweet.lib.magiclib.MagicLib;
import com.magicsweet.lib.magiclib.color.Colorizer;
import com.magicsweet.lib.magiclib.menu.event.PlayerClickInMenuEvent;
import com.magicsweet.lib.magiclib.menu.item.DefaultItems;
import com.magicsweet.lib.magiclib.menu.item.MenuItem;
import com.magicsweet.lib.magiclib.util.RangeUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


@Setter @Getter
@NoArgsConstructor
public abstract class Menu implements InventoryHolder {
	
	
	
	@Getter @Nullable private Menu parent;
	private HashMap<Supplier<Integer>, MenuItem> items = new HashMap<>();
	private Function<Player, Integer> size = player -> 54;
	private Function<Player, String> title = player -> "";
	private Consumer<InventoryOpenEvent> open = event -> {};
	private Consumer<InventoryCloseEvent> close = event -> {};
	
	private List<Requirement> requirements = new ArrayList<>();
	private List<Fitter> fitters = new ArrayList<>();
	
	private Function<Menu, MenuItem> backButton = DefaultItems.getBackItemGenerator();
	private Function<Menu, MenuItem> indevButton = DefaultItems.getIndevItemGenerator();
	
	
	@Getter Inventory inventory;
	
	/*
		PROPERTIES
	*/
	
	public Menu(@Nullable Menu parent) {
		this.parent = parent;
		
	}
	
	public void rows(int rows) {
		rows(player -> rows);
	}
	
	public void rows(Function<Player, Integer> rows) {
		this.size = player -> rows.apply(player) * 9;
	}
	
	public void size(int size) {
		size(player -> size);
	}
	
	public void size(Function<Player, Integer> size) {
	 	this.size = size;
	}
	
	public void title(String title) {
		title(player -> title);
	}
	
	public void title(Function<Player, String> title) {
		this.title = title;
	}
	
	public void item(int slot, MenuItem item) {
		item(() -> slot, item);
	}
	
	public void item(Supplier<Integer> slot, MenuItem item) {
		items.put(slot, item);
	}
	
	public void items(MenuItem item, int... slots) {
		for (var it: slots) {
			item(it, item);
		}
	}
	
	public Fitter fit(Function<Player, List<Object>> objects, Function<Player, int[]> slots) {
		var fitter = new Fitter(objects, slots);
		this.fitters.add(fitter);
		return fitter;
	}
	
	public Fitter fit(Function<Player, List<Object>> objects) {
		return fit(objects, player -> RangeUtil.integer(0, 8));
	}
	
	public Fitter fit(List<Object> objects, int[] slots) {
		return fit(player -> objects, player -> slots);
	}
	
	public Fitter fit(List<Object> objects) {
		return fit(player -> objects, player -> RangeUtil.integer(0, 8));
	}
	
	public Fitter fit(Object... objects) {
		return fit(player -> Arrays.asList(objects), player -> RangeUtil.integer(0, 8));
	}
	
	public void parent(Menu menu) {
		this.parent = menu;
	}
	
	public Requirement requirement(Function<Player, Boolean> requirement) {
		var object = new Requirement();
		this.requirements.add(object);
		object.requirement = requirement;
		return object;
	}
	
	public Requirement requirement(boolean requirement) {
		var object = new Requirement();
		this.requirements.add(object);
		object.requirement = player -> requirement;
		return object;
	}
	
	/*
		PROPERTIES END
	*/
	
	public static MenuItem item() {
		return new MenuItem();
	}
	
	public static MenuItem item(ItemStack itemStack) {
		return new MenuItem(itemStack);
	}
	
	public static MenuItem item(Material material) {
		return item(new ItemStack(material));
	}
	
	public void buttonBack(Function<Menu, MenuItem> backButton) {
		this.backButton = backButton;
	}
	
	public MenuItem buttonBack() {
		return backButton.apply(this);
	}
	
	public void buttonInDev(Function<Menu, MenuItem> indevButton) {
		this.indevButton = indevButton;
	}
	
	public MenuItem buttonInDev() {
		return indevButton.apply(this);
	}
	
	public Slots slots() {
		return new Slots(this);
	}
	
	public Slots slot() {
		return slots();
	}
	
	public void open(@NotNull Player player) {
		new Thread(() -> {
			for (var it: requirements) {
				if (!it.requirement.apply(player)) {
					if (it.getMessage() != null) {
						Colorizer.send(player, it.toString());
					}
					return;
				}
			}
			refresh(player);
			Bukkit.getScheduler().runTask(MagicLib.getInstance(), () -> {
				player.openInventory(inventory);
			});
		}).start();
	}
	
	public void refresh(@Nullable Player player) {
		refreshNotContents(player);
		for (var it: fitters) {
			it.getCurrentPageContents(player).forEach(this::item);
			if (it.isPageAvailable(player, it.getCurrentPage(player) + 1)) {
				item(it.nextPageItemSlot().apply(this), DefaultItems.getNextPageItem().apply(this, it));
			}
			if (it.isPageAvailable(player, it.getCurrentPage(player) - 1)) {
				item(it.previousPageItemSlot().apply(this), DefaultItems.getPreviousPageItem().apply(this, it));
			}
		}
		for (var it: items.entrySet()) {
			inventory.setItem(it.getKey().get(), it.getValue().getItemStack(player));
		}
		
	}
	
	public void refreshNotContents(@Nullable Player player) {
		if (inventory != null) inventory.clear();
		inventory = Bukkit.createInventory(this, size.apply(player), Colorizer.format(title.apply(player)));
	}
	
	public void clear() {
		inventory.clear();
	}
	
	private void onClick(PlayerClickInMenuEvent event) {
		items.entrySet().stream().filter(it -> it.getKey().get() == event.getSlot()).findAny().ifPresent(entry -> {
			entry.getValue().handleClick(event);
		});
	}
	
	public void handleClick(InventoryClickEvent event) {
		onClick(new PlayerClickInMenuEvent(event));
	}
	
	public void handleOpen(InventoryOpenEvent event) {
		open.accept(event);
	}
	
	public void handleClose(InventoryCloseEvent event) {
		close.accept(event);
	}
	
}
