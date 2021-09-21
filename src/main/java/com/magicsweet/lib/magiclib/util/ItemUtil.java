package com.magicsweet.lib.magiclib.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

@UtilityClass
public class ItemUtil {
	public <T extends ItemMeta> ItemStack meta(ItemStack item, Consumer<T> consumer) {
		var meta = item.getItemMeta();
		consumer.accept((T) meta);
		item.setItemMeta(meta);
		return item;
	}
	
	public <T extends ItemMeta> ItemStack meta(Material material, Consumer<T> consumer) {
		return meta(new ItemStack(material), consumer);
	}
	
}
