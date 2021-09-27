package com.magicsweet.lib.magiclib;

import com.magicsweet.lib.magiclib.annotations.LoadAtStartup;
import com.magicsweet.lib.magiclib.event.AsyncServerTickEvent;
import com.magicsweet.lib.magiclib.event.ServerTickEvent;
import com.magicsweet.lib.magiclib.menu.event.EventListener;
import fr.mrmicky.fastinv.FastInvManager;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public final class MagicLib extends JavaPlugin {
	@Getter @Setter static JavaPlugin instance;
	@Getter static List<Object> startupItems = new ArrayList<>();
	
	@Override
	public void onEnable() {
		enable(this);
	}
	
	@SneakyThrows
	public static void enable(JavaPlugin instance) {
		MagicLib.instance = instance;
		
		FastInvManager.register(instance);

		new BukkitRunnable() {
			
			Executor executor = Executors.newSingleThreadExecutor();
			int tick = 0;
			
			@Override
			public void run() {
				Bukkit.getPluginManager().callEvent(new ServerTickEvent(tick));
				executor.execute(() -> Bukkit.getPluginManager().callEvent(new AsyncServerTickEvent(tick)));
				tick = tick + 1;
			}
			
		}.runTaskTimer(instance, 0, 1);
		log("Scanning plugin classpath...");
		for (var cl: getAllClassesAnnotatedWith(LoadAtStartup.class)) {
			startupItems.add(cl.getDeclaredConstructor().newInstance());
		}
		log("Delayed startup done for " + startupItems.size() + " items!");
		
		log("Ready!");
	}
	
	@SneakyThrows
	public static List<Class<?>> getAllClasses(JavaPlugin plugin) {
		var method = JavaPlugin.class.getDeclaredMethod("getFile");
		method.setAccessible(true);
		File jarFile = (File) method.invoke(plugin);
		List<Class<?>> names = new ArrayList<>();
		try {
			@Cleanup JarFile file = new JarFile(jarFile);
			for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements();) {
				try {
					JarEntry jarEntry = entry.nextElement();
					String name = jarEntry.getName().replace("/", ".");
					if (name.endsWith(".class")) {
						Class<?> cl = Class.forName(name.substring(0, name.length() - 6));
						names.add(cl);
					}
				} catch (ClassNotFoundException | NoClassDefFoundError | ExceptionInInitializerError ignored) {}
			}
		} catch (IOException ignored) {}
		return names;
	}
	
	public static List<Class<?>> getAllClassesAnnotatedWith(JavaPlugin plugin, Class<? extends Annotation> annotation) {
		return getAllClasses(plugin).stream().filter(cl -> cl.isAnnotationPresent(annotation)).collect(Collectors.toList());
	}
	
	public static List<Class<?>> getAllClasses() {
		var list = new ArrayList<Class<?>>();
		for (var plugin: Bukkit.getPluginManager().getPlugins()) {
			if (plugin instanceof JavaPlugin) {
				var java = (JavaPlugin) plugin;
				list.addAll(getAllClasses(java));
			}
		}
		return list;
	}
	
	public static List<Class<?>> getAllClassesAnnotatedWith(Class<? extends Annotation> annotation) {
		return getAllClasses().stream().filter(cl -> cl.isAnnotationPresent(annotation)).collect(Collectors.toList());
	}
	
	/**
	 * @deprecated Use {@link #enable(JavaPlugin)}
	 */
	@Deprecated
	public static void enable() {
		enable(instance);
	}
	
	public static void log(String msg) {
		instance.getLogger().info("[" + instance.getName() + "/MagicLib] " + msg);
	}
	
}
