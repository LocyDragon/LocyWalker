package com.locydragon.lw.util;


import org.bukkit.Location;

public class Data {
	public Location loc;
	public double g;
	public double h;
	public Data last;

	private Data() {
	}

	public Data(Location loc, double g, double h, Data last) {
		this.loc = loc;
		this.g = g;
		this.h = h;
		this.last = last;
	}

	public Location getLoc() {
		return loc;
	}

	public double getG() {
		return g;
	}

	public double getH() {
		return h;
	}

	public Data getLast() {
		return last;
	}
}
