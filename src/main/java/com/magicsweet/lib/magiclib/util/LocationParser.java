package com.magicsweet.lib.magiclib.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;


public final class LocationParser {
    
    public static Location parse(World world, String location) {
        var split = location.split("\\s+");
        
        if (split.length > 3) {
            var loc = new Location(world, Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
            loc.setYaw(Float.parseFloat(split[3]));
            loc.setPitch(Float.parseFloat(split[4]));
            return loc;
        } else {
            return new Location(world, Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
        }
    }
    
    public static Location parse(String location) {
        return parse(Bukkit.getWorld("world"), location);
    }
    
    public static Vector vector(String location) {
        return new Vector(Double.parseDouble(location.split("\\s+")[0]), Double.parseDouble(location.split("\\s+")[1]), Double.parseDouble(location.split("\\s+")[2]));
    }
}