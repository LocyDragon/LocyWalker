package com.locydragon.lw.commands;

import com.locydragon.lw.cloud.WarpSettings;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WalkerCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player)sender;
		if (args[0].equalsIgnoreCase("go")) {

		} else if (args[0].equalsIgnoreCase("setWarp") && sender.isOp()) {
			if (args.length == 2) {
				String name = args[1];
				Location feet = player.getLocation();
				if (WarpSettings.setWarp(name, feet)) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&b&lLocyWalker &7>>>&e设置成功"));
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&b&lLocyWalker &7>>>&e传送点存在"));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&b&lLocyWalker &7>>>&e使用/lw setWarp [传送点名称] ——设置脚下的位置为传送点"));
			}
		}
		return false;
	}
}
