package com.magicsweet.lib.magiclib.menu;

import com.magicsweet.lib.magiclib.color.Colorizer;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
public class Requirement {
	
	Function<Player, Boolean> requirement;
	@Nullable private Supplier<String> message;
	
	public void message(String message) {
		this.message = () -> message;
	}
	
	public void message(Supplier<String> message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		if (message != null) {
			return Colorizer.format(message.get());
		}
		return null;
	}
}
