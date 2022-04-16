package com.magicsweet.lib.magiclib.menu;

import com.magicsweet.lib.magiclib.MagicLib;
import com.magicsweet.lib.magiclib.color.Colorizer;
import com.magicsweet.lib.magiclib.menu.event.EventListener;
import com.magicsweet.lib.magiclib.menu.event.PlayerClickInMenuEvent;
import com.magicsweet.lib.magiclib.menu.item.DefaultItems;
import com.magicsweet.lib.magiclib.menu.item.MenuItem;
import com.magicsweet.lib.magiclib.util.RangeUtil;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
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

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


@Setter @Getter
@NoArgsConstructor
public abstract class Menu implements InventoryHolder {
	@Getter @Setter Executor executor = Executors.newFixedThreadPool(1);
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
	
	private boolean threaded = true;
	
	Map<Player, Inventory> inventories = new ConcurrentHashMap<>();
	
	Inventory inventory;
	
	/*
		PROPERTIES
	*/
	
	public Menu(@Nullable Menu parent) {
		this.parent = parent;
		EventListener.getCleanup().add(it -> inventories.remove(it));
	}
	
	public Inventory getInventory(Player player) {
		return Optional.ofNullable(inventories.get(player)).orElseGet(() -> {
			var inventory = Bukkit.createInventory(this, size.apply(player), Colorizer.format(MenuItem.formatText(player, title.apply(player))));
			inventories.put(player, inventory);
			return inventory;
		});
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
	
	public Requirement requirement(String permission) {
		return requirement(pl -> pl.hasPermission(permission));
	}
	
	public Requirement requirement(boolean requirement) {
		var object = new Requirement();
		this.requirements.add(object);
		object.requirement = player -> requirement;
		return object;
	}
	
	public void threaded(boolean threaded) {
		this.threaded = threaded;
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
		Runnable expr = () -> {
			for (var it: requirements) {
				if (!it.requirement.apply(player)) {
					if (it.getMessage() != null) {
						Colorizer.send(player, MenuItem.formatText(player, it.toString()));
					}
					return;
				}
			}
			refresh(player);
			Bukkit.getScheduler().runTask(MagicLib.getInstance(), () -> {
				player.openInventory(getInventory(player));
			});
		};
		if (threaded) executor.execute(expr);
		else Bukkit.getScheduler().runTask(MagicLib.getInstance(), expr);
	}
	
	public void open(Player player, int delay) {
		new Thread(() -> {
			try {
				TimeUnit.MILLISECONDS.sleep(delay);
				open(player);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public void refresh(@Nullable Player player) {
		refreshNotContents(player);
		for (var it: new HashSet<>(items.entrySet())) {
			if (it.getValue().isFitterRelated()) {
				items.remove(it.getKey());
			}
		}
		for (var it: fitters) {
			it.getCurrentPageContents(player).forEach((slot, item) -> {
				item(slot, item.clone().setFitterRelated(true));
			});
			if (it.isPageAvailable(player, it.getCurrentPage(player) + 1)) {
				item(it.nextPageItemSlot().apply(this, player), DefaultItems.getNextPageItem().apply(this, it).clone().setFitterRelated(true));
			}
			if (it.isPageAvailable(player, it.getCurrentPage(player) - 1)) {
				item(it.previousPageItemSlot().apply(this, player), DefaultItems.getPreviousPageItem().apply(this, it).clone().setFitterRelated(true));
			}
		}
		
		var inventory = getInventory(player);
		Map<Integer, ItemStack> itemMap = new HashMap<>();
		for (var it: items.entrySet()) itemMap.put(it.getKey().get(), it.getValue().getItemStack(player));
		inventory.clear();
		for (var en: itemMap.entrySet()) inventory.setItem(en.getKey(), en.getValue());
		
	}
	
	public void refreshNotContents(@Nullable Player player) {
		inventories.put(player, Bukkit.createInventory(this, size.apply(player), Colorizer.format(MenuItem.formatText(player, title.apply(player)))));
	}
	
	private void onClick(PlayerClickInMenuEvent event) {
		items.entrySet().stream().filter(it -> it.getKey().get() == event.getSlot()).findAny().ifPresent(entry -> {
			entry.getValue().getClickRequirements().stream().filter(req -> !req.getRequirement().apply(event.getPlayer())).findAny().ifPresentOrElse(req -> {
				if (req.getMessage() != null) {
					event.getPlayer().sendMessage(Colorizer.format(req.getMessage().get()));
				}
			}, () -> entry.getValue().handleClick(event));
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
