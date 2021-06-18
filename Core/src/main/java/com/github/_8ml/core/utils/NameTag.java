package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 5/8/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.UpdateEvent;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.junit.Assert;

import java.util.*;

public class NameTag implements Listener {

    private static final Map<Player, EntityArmorStand> offsetMap = new HashMap<>();
    private static final Map<Player, EntityArmorStand> titleMap = new HashMap<>();
    private static final Set<Integer> entityID = new HashSet<>();

    public NameTag(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void changeTag(Player player, String prefix, String suffix, ChatColor color, String title) {

        player.setPlayerListName(prefix + color + "" + player.getName());

        for (Player p : Core.onlinePlayers) {
            Scoreboard board = p.getScoreboard();

            Team team = board.getTeam(player.getName()) == null ? board.registerNewTeam(player.getName())
                    : board.getTeam(player.getName());

            Assert.assertNotNull("Team cannot be null (changeTag)", team);
            team.setPrefix(prefix);
            team.setSuffix(" " + title);
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            team.setColor(color);
            team.addEntry(player.getName());

        }

    }

    @Deprecated
    public static void changeTitle(Player player, String title) {

        if (titleMap.containsKey(player)) {

            if (!Objects.requireNonNull(titleMap.get(player).getCustomName()).equals(new ChatComponentText(title))) {
                removeTitle(player);
            } else return;

        }

        Location location = player.getLocation();


        EntityArmorStand entity = new EntityArmorStand(((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle(),
                location.getX(), location.getY(), location.getZ());
        entity.setCustomName(new ChatComponentText(title));
        entity.setCustomNameVisible(true);

        entity.setNoGravity(true);

        entity.setInvisible(true);
        entity.setMarker(true);


        EntityArmorStand offset = new EntityArmorStand(((CraftWorld)
                location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ());
        offset.setNoGravity(true);
        offset.setInvisible(true);
        offset.setSmall(true);

        PacketPlayOutSpawnEntity packetOffset = new PacketPlayOutSpawnEntity(offset);
        PacketPlayOutSpawnEntity packetEntity = new PacketPlayOutSpawnEntity(entity);

        for (Player p : Core.onlinePlayers) {
            if (p.equals(player)) continue;
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetOffset);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetEntity);
        }

        offset.passengers.add(entity);
        ((CraftPlayer) player).getHandle().passengers.add(offset);

        entityID.add(entity.getId());
        titleMap.put(player, entity);
        offsetMap.put(player, offset);


    }

    @Deprecated
    public static void removeTitle(Player player) {
        if (titleMap.containsKey(player)) {

            sendDestroyPacket(player);

            entityID.remove(titleMap.get(player).getId());
            titleMap.remove(player);
            offsetMap.remove(player);

        }
    }

    @Deprecated
    private static void sendDestroyPacket(Player player) {
        PacketPlayOutEntityDestroy packetEntity = new PacketPlayOutEntityDestroy(titleMap.get(player).getId());
        PacketPlayOutEntityDestroy packetOffset = new PacketPlayOutEntityDestroy(offsetMap.get(player).getId());

        for (Player p : Core.onlinePlayers) {
            if (p.equals(player)) continue;
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetEntity);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetOffset);
        }
    }


    @EventHandler
    public void onUpdate(UpdateEvent e) {

        if (!e.getType().equals(UpdateEvent.UpdateType.SECONDS)) return;

        for (Player p : Core.onlinePlayers) {

            if (!titleMap.containsKey(p)) continue;

            EntityArmorStand entity = titleMap.get(p);
            EntityArmorStand offset = offsetMap.get(p);
            //Entity offset2 = offset2Map.get(p);

            if (!((CraftPlayer) p).getHandle().passengers.contains(offset)
                    || !offset.passengers.contains(entity)) {

                removeTitle(p);

            }

        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        for (Player p : Core.onlinePlayers) {

            if (titleMap.containsKey(p)) {

                PacketPlayOutSpawnEntity packetOffset = new PacketPlayOutSpawnEntity(offsetMap.get(p));
                PacketPlayOutSpawnEntity packetEntity = new PacketPlayOutSpawnEntity(titleMap.get(p));

                CraftPlayer craftPlayer = ((CraftPlayer) e.getPlayer());
                craftPlayer.getHandle().playerConnection.sendPacket(packetOffset);
                craftPlayer.getHandle().playerConnection.sendPacket(packetEntity);

            }

        }

    }

}
