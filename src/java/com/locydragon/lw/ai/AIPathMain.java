package com.locydragon.lw.ai;

import com.locydragon.lw.util.Data;
import com.locydragon.lw.util.MinHeap;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AIPathMain {
	private static final int[][] meet = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
	public static List<Location> getPathTo(Location AA, Location BB) {
		List<Location> returnType = new ArrayList<>();
		if (!AA.getWorld().getName().equals(BB.getWorld().getName())) {
			return returnType;
		}
		Set<Location> wasVisited = new HashSet<>();
		int time = 1000; //需要修改
		MinHeap heap = new MinHeap();
		heap.add(new Data(AA, 0, 0 , null));
		Data lastOne = null;
		FatherLoop:for (int i = 0; i < time;i++) {
			Data data = heap.getAndRemoveMin();
			Location targetLoc = data.getLoc();
			if (!contains(wasVisited, targetLoc)) {
				wasVisited.add(targetLoc);
			}
			for (int[] dir : meet) {
				int x = dir[0];
				int z = dir[1];
				Location newLocationBottom = new Location(AA.getWorld(), targetLoc.getX() + x, 0 , targetLoc.getZ() + z);
				Location newLoc = getHuManTop(targetLoc, newLocationBottom);
				if (newLoc.getBlockX() == BB.getBlockX() && newLoc.getBlockY() == BB.getBlockY()) {
					lastOne = data;
					break FatherLoop;
				}
				if (isWallTo(targetLoc, newLocationBottom)) {
					continue;
				}
				if (contains(wasVisited, newLoc)) {
					continue;
				}
				Data newData = heap.find(newLoc);
				if (newData == null) {
					double newOne =  Math.abs(newLoc.getBlockX() - BB.getBlockX()) + Math.abs(newLoc.getBlockZ() - BB.getBlockZ());
                    heap.add(new Data(newLoc, data.getG() + 1, newOne, data));
				} else {
					if (newData.g > data.g + 1) {
						newData.g = data.g + 1;
						newData.last = data;
					}
				}
			}
		}
		while (lastOne != null) {
			Location newLoc = lastOne.loc;
			returnType.add(newLoc);
			lastOne = lastOne.last;
		}
		return returnType;
	}
	public static boolean contains(Set<Location> sets, Location loc) {
		for (Location location : sets) {
			if (location.getBlockX() == loc.getBlockX() && location.getBlockZ() == loc.getBlockZ()) {
				return true;
			}
		}
		return false;
	}
	public static boolean isWallTo(Location AA, Location BB) {
		Location CC = BB.clone();
		CC.setY(AA.getY() + 1);
		Location DD = CC.clone();
		DD.setY(DD.getY() + 1);
		Location FF = DD.clone();
		FF.setY(FF.getY() + 1);
		if (CC == null && DD == null) {
			return false;
		}
		if (DD == null && FF == null) {
			return false;
		}
		if (CC.getBlock().getType() == Material.AIR && DD.getBlock().getType() == Material.AIR) {
			return false;
		}
		if (DD.getBlock().getType() == Material.AIR && FF.getBlock().getType() == Material.AIR) {
			return false;
		}
		if (!CC.getBlock().isLiquid() && !DD.getBlock().isLiquid() &&
				CC.getBlock().getType() != Material.AIR && DD.getBlock().getType() != Material.AIR) {
			return true;
		}
		if (CC.getBlock().getType() != Material.AIR && FF.getBlock().getType() != Material.AIR) {
			return true;
		}
		return false;
	}
	public static Location getHuManTop(Location AA, Location BB) {
		Location CC = BB.clone();
		CC.setY(AA.getY());
		if (CC.getBlock() == null || CC.getBlock().getType() == Material.AIR) {
			return CC;
		} else {
			CC.setY(CC.getY() + 1);
			return CC;
		}
	}
}
