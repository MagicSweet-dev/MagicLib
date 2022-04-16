package com.magicsweet.lib.magiclib.menu.item;

import com.magicsweet.lib.magiclib.menu.Fitter;
import com.magicsweet.lib.magiclib.menu.Menu;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DefaultItems {
	@Getter @Setter private static Function<Menu, MenuItem> backItemGenerator = menu -> Menu.item(Material.ARROW)
		.name("&eBack")
		.lore(
			"&7Click to go to previous menu.",
			"&7or don't ya?"
		).click(event -> {
			if (menu.getParent() != null)
				menu.getParent().open(event.getPlayer());
		});
	
	@Getter @Setter private static Function<Menu, MenuItem> indevItemGenerator = menu -> Menu.item(Material.BARRIER)
		.name("&cUnder development")
		.lore(
			"&7This feature is still under development.",
			"&fCheck back later!"
		).click(event -> {
			if (menu.getParent() != null)
				menu.getParent().open(event.getPlayer());
		});
	
	@Getter @Setter private static BiFunction<Menu, Fitter, MenuItem> previousPageItem = (menu, fitter) -> Menu.item(Material.ARROW)
		.name("&ePrevious page")
		.lore(
			"&7Click to go to previous page"
		)
		.click(event -> {
			fitter.flipPageBack(event.getPlayer());
			menu.refresh(event.getPlayer());
			menu.open(event.getPlayer());
		});
	
	@Getter @Setter private static BiFunction<Menu, Fitter, MenuItem> nextPageItem = (menu, fitter) -> Menu.item(Material.SPECTRAL_ARROW)
		.name("&eNext page")
		.lore(
			"&7Click to go to next page"
		)
		.click(event -> {
			fitter.flipPage(event.getPlayer());
			menu.refresh(event.getPlayer());
			menu.open(event.getPlayer());
		});
	
	
}
