package com.locydragon.lw.cloud;


import com.locydragon.lw.LocyWalker;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WarpSettings {
	public static Location getWarp(String warpName) {
		if (LocyWalker.getByKey(warpName) == null) {
			return null;
		}
		String[] before = LocyWalker.getByKey(warpName).split("\\*");
        return new Location(Bukkit.getWorld(before[0]),
				Integer.valueOf(before[1]), Integer.valueOf(before[2]), Integer.valueOf(before[3]));
	}
	public static boolean setWarp(String warpName, Location warpLoc) {
		if (getWarp(warpName) != null) {
			return false;
		}
		LocyWalker.addLineSave(warpName,
				warpLoc.getWorld().getName()+"*"+warpLoc.getBlockX()+"*"+warpLoc.getBlockY()+"*"+warpLoc.getBlockZ());
		return true;
	}
}
