package com.magicsweet.lib.magiclib;

import com.magicsweet.lib.magiclib.annotations.LoadAtStartup;
import com.magicsweet.lib.magiclib.event.AsyncServerTickEvent;
import com.magicsweet.lib.magiclib.event.ServerTickEvent;
import fr.mrmicky.fastinv.FastInvManager;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
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
import java.net.URL;
import java.net.URLClassLoader;
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
	@Getter static List<Object> startupItems = new CopyOnWriteArrayList<>();
	@Getter static Executor executor = Executors.newWorkStealingPool();
	
	@Override
	public void onEnable() {
		enable(this);
		warn("Usage of MagicLib as separate plugin is deprecated! This should work, but this is not how MagicLib is designed to work. Consider shadowing MagicLib directly into your plugin next time.");
	}
	
	@SneakyThrows
	public static void enable(JavaPlugin instance) {
		MagicLib.instance = instance;
		
		FastInvManager.register(instance);

		var tick = new BukkitRunnable() {
			int tick = 0;
			
			@Override
			public void run() {
				Bukkit.getPluginManager().callEvent(new ServerTickEvent(tick));
				tick = tick + 1;
			}
			
		};
		var async = new BukkitRunnable() {
			int tick = 0;
			
			@Override
			public void run() {
				Bukkit.getPluginManager().callEvent(new AsyncServerTickEvent(tick));
				tick = tick + 1;
			}
		};
		
		tick.runTaskTimer(instance, 0, 1);
		async.runTaskTimerAsynchronously(instance, 0, 1);
		
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
				} catch (Exception | Error ignored) {}
			}
		} catch (IOException ignored) {}
		return names;
	}
	
	@SneakyThrows
	public static List<Class<?>> loadJarToClasspath(File jar) {
		var list = new ArrayList<Class<?>>();
		
		JarFile jarFile = new JarFile(jar);
		Enumeration<JarEntry> e = jarFile.entries();
		
		URL[] urls = { new URL("jar:file:" + jar +"!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);
		
		while (e.hasMoreElements()) {
			JarEntry je = e.nextElement();
			if (je.isDirectory() || !je.getName().endsWith(".class")) {
				continue;
			}
			
			String className = je.getName().substring(0,je.getName().length()-6);
			className = className.replace('/', '.');
			try {
				Class<?> c = cl.loadClass(className);
				list.add(c);
			} catch (Error ignored) {}
		}
		return list;
	}
	
	public static List<Class<?>> getAllClassesAnnotatedWith(JavaPlugin plugin, Class<? extends Annotation> annotation) {
		return getAllClasses(plugin).stream().filter(cl -> cl.isAnnotationPresent(annotation)).collect(Collectors.toList());
	}
	
	public static List<Class<?>> getAllClasses() {
		var list = new ArrayList<Class<?>>();
		if (MagicLib.instance != null) {
			var java = (JavaPlugin) MagicLib.instance;
			list.addAll(getAllClasses(java));
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
		Bukkit.getLogger().info("[" + instance.getName() + "/MagicLib] " + msg);
	}
	
	public static void warn(String msg) {
		Bukkit.getLogger().warning("[" + instance.getName() + "/MagicLib] " + msg);
	}
	
}
