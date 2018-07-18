package com.locydragon.lw;

import com.locydragon.lw.commands.WalkerCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class LocyWalker extends JavaPlugin {
	public static int walkTime = 0;
	public static int showTime = 0;
	public static String effect = null;
	public static LocyWalker instance;
	@Override
	public void onEnable(){
		song("========[LocyWalker]=======",
				"伪AI自动寻路机制",
				"作者: LocyDragon",
				"作者QQ: 2424441676 支持赞助!"
		);
        Bukkit.getPluginCommand("lw").setExecutor(new WalkerCommand());
        instance = this;
        walkTime = instance.getConfig().getInt("MaxFindTime");
		showTime = instance.getConfig().getInt("ShowTime");
		effect = instance.getConfig().getString("EffectType");
	}
	public static void song(String... info) {
		Arrays.stream(info).forEach(each -> Bukkit.getLogger().info(each));
	}
	public static void addLineSave(String key, String value) {
		List<String> arrayList = instance.getConfig().getStringList("warps");
		arrayList.add(key+"灩"+value);
		instance.getConfig().set("warps", arrayList);
		instance.saveConfig();
		instance.reloadConfig();
	}
	public static String getByKey(String key) {
		List<String> arrayList = instance.getConfig().getStringList("warps");
		for (String obj : arrayList) {
			return obj.split("灩")[1];
		}
		return null;
	}
}
