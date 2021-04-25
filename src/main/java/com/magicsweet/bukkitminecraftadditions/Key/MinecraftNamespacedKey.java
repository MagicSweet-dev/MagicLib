package com.magicsweet.bukkitminecraftadditions.Key;

@Deprecated
public class MinecraftNamespacedKey {
	String namespace;
	String key;
	
	public MinecraftNamespacedKey(String id) {
		this.namespace = id.split(":")[0];
		this.key = id.split(":")[1];
	}
	
	
	public static MinecraftNamespacedKey of(String id) {
		return new MinecraftNamespacedKey(id);
	}
	
}
