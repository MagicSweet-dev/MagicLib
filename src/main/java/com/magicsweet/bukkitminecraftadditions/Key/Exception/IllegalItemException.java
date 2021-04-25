package com.magicsweet.bukkitminecraftadditions.Key.Exception;

public class IllegalItemException extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalItemException(String message) {
		super("\n" + message);
	}
}
