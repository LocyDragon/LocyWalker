package com.locydragon.lw.effect;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class PacketEffect {
	public static final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
	public static void sendPacketEffect(EffectTypeEnum type, Player who, Location where) {
		PacketContainer container = manager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
		container.getIntegers().write(0, type.getF());
		container.getIntegers().write(1, 10);
		container.getBooleans().write(0, false);
		container.getFloat().write(0, (float)where.getX());
		container.getFloat().write(1, (float)where.getY());
		container.getFloat().write(2, (float)where.getZ());
		container.getFloat().write(3, (float)(where.getX() + new Random().nextGaussian()));
		container.getFloat().write(4, (float)(where.getY() + new Random().nextGaussian()));
		container.getFloat().write(5, (float)(where.getZ() + new Random().nextGaussian()));
		container.getIntegerArrays().write(0, new int[]{});
		try {
			manager.sendServerPacket(who, container);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	public static void sendPacketEffect(EffectTypeEnum type, Player who, List<Location> where) {
		where.stream().forEach(loc -> sendPacketEffect(type, who, loc));
	}
}
