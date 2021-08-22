package com.magicsweet.lib.magiclib.text;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PaperColorizer {
	public Component json(String string) {
		try {
			return GsonComponentSerializer.gson().deserialize(string);
		} catch (Exception e) {
			return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
		}
	}
	
	public List<Component> json(List<String> strings) {
		return strings.stream().map(PaperColorizer::json).collect(Collectors.toList());
	}
	
	
}
