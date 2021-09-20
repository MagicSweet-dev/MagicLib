package com.magicsweet.lib.magiclib.menu.item;

import com.magicsweet.lib.magiclib.menu.Menu;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.function.Function;

public class DefaultItems {
	@Getter @Setter private static Function<Menu, MenuItem> backItemGenerator = menu -> menu.item(Material.ARROW)
		.name("&eBack")
		.lore(
			"&7Click to go to previous menu.",
			"&7or don't ya?"
		).click(event -> {
			if (menu.getParent() != null)
				menu.getParent().open(event.getPlayer());
		});
	
	@Getter @Setter private static Function<Menu, MenuItem> indevItemGenerator = menu -> menu.item(Material.BARRIER)
		.name("&cUnder development")
		.lore(
			"&7This feature is still under development.",
			"&fCheck back later!"
		).click(event -> {
			if (menu.getParent() != null)
				menu.getParent().open(event.getPlayer());
		});
	
}
