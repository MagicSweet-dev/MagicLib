package com.magicsweet.lib.magiclib.menu.item;

import com.magicsweet.lib.magiclib.color.Colorizer;
import com.magicsweet.lib.magiclib.key.EnumEnchant;
import com.magicsweet.lib.magiclib.menu.Requirement;
import com.magicsweet.lib.magiclib.menu.event.PlayerClickInMenuEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor
public class MenuItem implements Cloneable {
	
	@Getter @Setter static List<BiFunction<Player, String, String>> textFormatters = new ArrayList<>(
		List.of(
			(player, string) -> Colorizer.format(string)
		)
	);
	
	@Getter @Setter static List<BiFunction<Player, ItemStack, ItemStack>> mappers = new ArrayList<>();
	
	
	@NotNull Function<@Nullable Player, @NotNull ItemStack> baseItemStack = player -> new ItemStack(Material.APPLE);
	@Nullable Function<@Nullable Player, @NotNull String> name;
	@Nullable Function<@Nullable Player, @NotNull List<String>> lore;
	@NotNull List<Function<@Nullable Player, @NotNull List<String>>> loreAppenders = new ArrayList<>();
	@NotNull Function<@Nullable Player, @NotNull ItemFlag[]> flags = player -> ItemFlag.values();
	@NotNull Function<@Nullable Player, @NotNull Boolean> glint = player -> false;
	@Setter @Accessors(chain = true, fluent = true) @NotNull BiConsumer<Player, ItemMeta> meta = (player, meta) -> {};
	@NotNull List<Consumer<PlayerClickInMenuEvent>> clicks = new ArrayList<>();
	@Getter @NotNull List<Requirement> clickRequirements = new CopyOnWriteArrayList<>();
	
	@Getter @Setter @Accessors(chain = true) private boolean fitterRelated = false;
	
	public MenuItem(ItemStack itemStack) {
		this.baseItemStack = player -> itemStack;
	}
	
	public ItemStack getItemStack(@Nullable Player player) {
		var item = baseItemStack.apply(player).clone();
		
		var meta = item.getItemMeta();
		this.meta.accept(player, meta);
		if (name != null) meta.setDisplayName(formatText(player, name.apply(player)));
		if (lore != null) {
			var lore = new ArrayList<>(this.lore.apply(player));
			loreAppenders.forEach(it -> {
				lore.addAll(it.apply(player));
			});
			meta.setLore(formatText(player, lore));
		}
		for (var flag: flags.apply(player)) {
			meta.addItemFlags(flag);
		}
		if (glint.apply(player)) {
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addEnchant(EnumEnchant.POWER, 0, true);
		}
		item.setItemMeta(meta);
		
		for (var mapper: mappers) item = mapper.apply(player, item);
		
		return item;
	}
	
	public MenuItem item(Function<Player, ItemStack> function) {
		this.baseItemStack = function;
		return this;
	}
	
	public MenuItem item(ItemStack item) {
		this.baseItemStack = player -> item;
		return this;
	}
	
	public MenuItem item(Material item) {
		return item(new ItemStack(item));
	}
	
	public MenuItem name(@Nullable Function<@Nullable Player, @NotNull String> name) {
		this.name = name;
		return this;
	}
	
	public MenuItem name(String name) {
		return name(player -> name);
	}
	
	public MenuItem lore(Function<@Nullable Player, @NotNull List<String>> lore) {
		this.lore = lore;
		return this;
	}
	
	public MenuItem lore(List<String> lore) {
		return lore(player -> lore);
	}
	
	public MenuItem lore(String... lore) {
		var list = new ArrayList<String>();
		for (var str: lore) list.addAll(Arrays.asList(str.split("\n")));
		return lore(list);
	}
	
	public MenuItem loreAppender(Function<@Nullable Player, @NotNull List<String>> lore) {
		this.loreAppenders.add(lore);
		return this;
	}
	
	public MenuItem loreAppender(List<String> lore) {
		return loreAppender(player -> lore);
	}
	
	public MenuItem loreAppender(String... lore) {
		return loreAppender(Arrays.asList(lore));
	}
	
	
	public MenuItem flags(Function<@Nullable Player, @NotNull ItemFlag[]> flags) {
		this.flags = flags;
		return this;
	}
	
	public MenuItem flags(ItemFlag... flags) {
		return flags(player -> flags);
	}
	
	public MenuItem glint(Function<@Nullable Player, @NotNull Boolean> glint) {
		this.glint = glint;
		return this;
	}
	
	public MenuItem glint(boolean glint) {
		return glint(player -> glint);
	}
	
	public MenuItem glint() {
		return glint(true);
	}
	
	
	public MenuItem click(@NotNull Consumer<PlayerClickInMenuEvent> click, ClickType... clickTypes) {
		if (clickTypes.length == 0) {
			this.clicks.add(click);
		} else {
			this.clicks.add(event -> {
				if (Arrays.asList(clickTypes).contains(event.getClick())) click.accept(event);
			});
		}
		return this;
	}
	
	public <T extends ItemMeta> MenuItem meta(Class<T> clazz, Consumer<T> editor) {
		return meta((pl, it) -> editor.accept((T) it));
	}
	
	public MenuItem click(String command, ClickType... clickTypes) {
		return click(event -> {
			Bukkit.dispatchCommand(event.getPlayer(), command);
		}, clickTypes);
	}
	
	public void handleClick(PlayerClickInMenuEvent event) {
		for (var it: clicks) {
			it.accept(event);
		}
	}
	
	public Requirement clickRequirement(boolean bool) {
		var req = new Requirement(pl -> bool);
		clickRequirements.add(req);
		return req;
	}
	
	public Requirement clickRequirement(Function<Player, Boolean> function) {
		var req = new Requirement(function);
		clickRequirements.add(req);
		return req;
	}
	
	public Requirement clickRequirement(Requirement req) {
		clickRequirements.add(req);
		return req;
	}
	
	public Requirement clickRequirement(String permission) {
		var req = new Requirement(pl -> pl.hasPermission(permission));
		req.message("&cYou need to have permission &8" + permission + " &cto do this!");
		clickRequirements.add(req);
		return req;
	}
	
	
	@SneakyThrows
	@Override
	public MenuItem clone() {
		var item = (MenuItem) super.clone();
		
		item.loreAppenders = new ArrayList<>(this.loreAppenders);
		item.clicks = new ArrayList<>(this.clicks);
		
		return item;
	}
	
	public static String formatText(Player player, String string) {
		for (var func: textFormatters) string = func.apply(player, string);
		return string;
	}
	
	public static List<String> formatText(Player player, List<String> strings) {
		var list = new ArrayList<String>();
		for (var str: strings) list.add(formatText(player, str));
		return list;
	}
	
}
