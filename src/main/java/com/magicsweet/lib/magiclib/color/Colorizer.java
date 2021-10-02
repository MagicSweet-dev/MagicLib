package com.magicsweet.lib.magiclib.color;

import com.magicsweet.lib.magiclib.util.Pair;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class Colorizer {
	
	public List<String> format(List<String> stringList) {
		return stringList.stream().map(Colorizer::format).collect(Collectors.toList());
	}
	
	public void send(Player pl, String msg, Pair<@Nullable ?, @Nullable ?>... placers) {
		msg = setPlacers(msg, placers);
		pl.sendMessage(format(msg));
	}
	
	public String setPlacers(String msg, Pair<@Nullable ?, @Nullable ?>... placers) {
		for (var it: placers) {
			msg = msg.replace(it.getKey() + "", it.getValue() + "");
		}
		return msg;
	}
	
	public void sendJson(Player pl, String msg, Pair<@Nullable ?, @Nullable ?>... placers) {
		msg = setPlacers(msg, placers);
		pl.sendMessage(json(msg));
	}
	
	public String json(String string) {
		return json(string, null);
	}
	
	public List<String> json(List<String> strings) {
		return strings.stream().map(Colorizer::json).collect(Collectors.toList());
	}
	
	
	public String json(String string, OfflinePlayer player) {
		try {
			return format(TextComponent.toLegacyText(ComponentSerializer.parse(string)), player);
		} catch (Exception e) {
			return format(string, player);
		}
	}
	
	
	public String format(String string) {
		return format(string, null);
	}
	
	public String format(String string, OfflinePlayer player) {
		
		
		final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
		
		Matcher match = pattern.matcher(string);
		while (match.find()) {
			String color = string.substring(match.start(), match.end());
			
			string = string.replace(color, ChatColor.of(color).toString());
			
			match = pattern.matcher(string);
		}
		
		
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") && player != null) {
			string = PlaceholderAPI.setPlaceholders(player, string);
		}
		
		string = ChatColor.translateAlternateColorCodes('&', string);
		
		return string;
	}
	
	@Deprecated
	public List<String> formatLegacy(List<String> stringList) {
		return stringList.stream().map(Colorizer::formatLegacy).collect(Collectors.toList());
	}
	
	@Deprecated
	public String formatLegacy(String string) {
		final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
		
		Matcher match = pattern.matcher(string);
		while (match.find()) {
			String color = string.substring(match.start(), match.end());
			
			string = string.replace(color, convertHexToNearest(color));
			
			match = pattern.matcher(string);
		}
		
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	
	private String convertHexToNearest(String hex) {
		
		HashMap<Color, String> mcColors = new HashMap<>();
		
		mcColors.put(Color.decode("#000000"), "0");
		mcColors.put(Color.decode("#0000AA"), "1");
		mcColors.put(Color.decode("#00AA00"), "2");
		mcColors.put(Color.decode("#00AAAA"), "3");
		mcColors.put(Color.decode("#AA0000"), "4");
		mcColors.put(Color.decode("#AA00AA"), "5");
		mcColors.put(Color.decode("#FFAA00"), "6");
		mcColors.put(Color.decode("#AAAAAA"), "7");
		mcColors.put(Color.decode("#555555"), "8");
		mcColors.put(Color.decode("#5555FF"), "9");
		mcColors.put(Color.decode("#55FF55"), "a");
		mcColors.put(Color.decode("#55FFFF"), "b");
		mcColors.put(Color.decode("#FF5555"), "c");
		mcColors.put(Color.decode("#FF5555"), "d");
		mcColors.put(Color.decode("#FFFF55"), "e");
		mcColors.put(Color.decode("#FFFFFF"), "f");
		
		var initial = Color.decode(hex);
		
		var differences = new HashMap<Color, Integer>();
		
		mcColors.keySet().forEach(key -> {
			int r, g, b;
			r = initial.getRed() - key.getRed(); if (r < 0) r = r*-1;
			g = initial.getGreen() - key.getGreen(); if (g < 0) g = g*-1;
			b = initial.getBlue() - key.getBlue(); if (b < 0) b = b*-1;
			
			differences.put(key, r + g + b);
			
		});
		
		int c = Integer.MAX_VALUE;
		Color color = null;
		
		for (var col: differences.keySet()) {
			var val = differences.get(col);
			if (val < c) {
				c = val;
				color = col;
			}
		}
		
		if (color != null) {
			return "&" + mcColors.get(color);
		} else {
			return null;
		}
	}
}
