package com.magicsweet.lib.magiclib.util;

import com.magicsweet.lib.magiclib.color.Colorizer;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;


@UtilityClass
public final class LocationParser {
    
    public Location parse(World world, String location) {
        var split = location.split("\\s+");
    
        var loc = new Location(
            world,
            Optional.ofNullable(getInt(split[0])).map(i -> i + 0.5).orElseGet(() -> Double.parseDouble(split[0])),
            Double.parseDouble(split[1]),
            Optional.ofNullable(getInt(split[2])).map(i -> i + 0.5).orElseGet(() -> Double.parseDouble(split[2]))
        );
        
        if (split.length > 3) {
            loc.setYaw(Float.parseFloat(split[3]));
            loc.setPitch(Float.parseFloat(split[4]));
        }
        
        return loc;
    }
    
    public Location parse(String location) {
        return parse(Bukkit.getWorld("world"), location);
    }
    
    public Vector vector(String location) {
        return new Vector(Double.parseDouble(location.split("\\s+")[0]), Double.parseDouble(location.split("\\s+")[1]), Double.parseDouble(location.split("\\s+")[2]));
    }
    
    public String asString(@Nullable Location location, boolean inclideWorld, String worldColor) {
        if (location == null) return "null";
        var str = ((int) location.getX()) + " " + ((int) location.getY()) + " " + ((int) location.getZ());
        
        if (inclideWorld) str = Colorizer.format(str + " " + worldColor + "(" + (location.getWorld() != null ? location.getWorld().getName() : "null") + ")");
        return str;
    }
    
    public String asString(@Nullable Location location, boolean inclideWorld) {
        return asString(location, inclideWorld, "&8");
    }
    
    public String asString(@Nullable Location location) {
        return asString(location, false);
    }
    
    private @Nullable Integer getInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception ignored) {
            return null;
        }
    }
}