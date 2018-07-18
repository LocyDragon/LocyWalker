package com.locydragon.lw.commands;

import com.locydragon.lw.LocyWalker;
import com.locydragon.lw.ai.AIPathMain;
import com.locydragon.lw.cloud.WarpSettings;
import com.locydragon.lw.effect.EffectTypeEnum;
import com.locydragon.lw.effect.PacketEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WalkerCommand implements CommandExecutor {
	HashMap<String,Thread> contains = new HashMap<>();
	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player)sender;
		if (args[0].equalsIgnoreCase("go")) {
			if (args.length == 2) {
				Location me = player.getLocation();
				Location to = WarpSettings.getWarp(args[1]);
				for (Map.Entry<String,Thread> entry : contains.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(player.getName())) {
						entry.getValue().stop();
						contains.remove(entry.getKey());
					}
				}
				if (to == null) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&b&lLocyWalker &7>>>&e传送点不存在"));
					return false;
				}
                if (!me.getWorld().getName().equals(to.getWorld().getName())) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&b&lLocyWalker &7>>>&e找不到传送点."));
					return false;
				}
				Thread newThread = new Thread(new Runnable() {
					@Override
					public void run() {
						List<Location> path = AIPathMain.getPathTo(me, to);
						new BukkitRunnable() {
							@Override
							public void run() {
								if (!player.isOnline()) {
									cancel();
								}
								PacketEffect.sendPacketEffect(EffectTypeEnum.valueOf(LocyWalker.effect), player, path);
							}
						}.runTaskTimer(LocyWalker.instance, 0, 20 * LocyWalker.showTime);
					}
				});
				newThread.start();
				contains.put(player.getName(), newThread);
				player.sendMessage("&b&lLocyWalker &7>>>&e开始导航了");
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&b&lLocyWalker &7>>>&e使用/lw go [地点名称] ——我想要去这里"));
			}
		} else if (args[0].equalsIgnoreCase("setWarp") && sender.isOp()) {
			if (args.length == 2) {
				String name = args[1];
				Location feet = player.getLocation();
				if (WarpSettings.setWarp(name, feet)) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&b&lLocyWalker &7>>>&e设置成功"));
					return false;
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&b&lLocyWalker &7>>>&e传送点存在"));
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&b&lLocyWalker &7>>>&e使用/lw setWarp [传送点名称] ——设置脚下的位置为传送点"));
			}
		}
		return false;
	}
}
