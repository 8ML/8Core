package club.mineplay.core.utils;
/*
Created by Sander on 5/8/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.events.event.UpdateEvent;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class NameTag implements Listener {

    public static final Map<Player, EntityArmorStand> offsetMap = new HashMap<>();
    public static final Map<Player, Entity> offset2Map = new HashMap<>();
    public static final Map<Player, EntityArmorStand> titleMap = new HashMap<>();
    public static final Set<Integer> entityID = new HashSet<>();

    public NameTag(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void changeTag(Player player, String prefix, String suffix, ChatColor color) {

        Scoreboard scoreBoard = player.getScoreboard();

        Team team = scoreBoard.getTeam(player.getName()) == null
                ? scoreBoard.registerNewTeam(player.getName())
                : scoreBoard.getTeam(player.getName());

        if (scoreBoard.getObjective(player.getName()) != null)
            Objects.requireNonNull(scoreBoard.getObjective(player.getName())).unregister();

        assert team != null;
        team.setDisplayName(color + player.getName());
        team.setColor(color);
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);

        team.addEntry(player.getName());


    }

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

        offset.passengers.add(entity);
        ((CraftPlayer) player).getHandle().passengers.add(offset);

        PacketPlayOutSpawnEntity packetOffset = new PacketPlayOutSpawnEntity(offset);
        PacketPlayOutSpawnEntity packetEntity = new PacketPlayOutSpawnEntity(entity);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.equals(player)) continue;
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetOffset);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetEntity);
        }

        entityID.add(entity.getId());
        titleMap.put(player, entity);
        offsetMap.put(player, offset);


    }

    public static void removeTitle(Player player) {
        if (titleMap.containsKey(player)) {

            sendDestroyPacket(player);

            entityID.remove(titleMap.get(player).getId());
            titleMap.remove(player);
            offsetMap.remove(player);

        }
    }

    private static void sendDestroyPacket(Player player) {
        PacketPlayOutEntityDestroy packetEntity = new PacketPlayOutEntityDestroy(titleMap.get(player).getId());
        PacketPlayOutEntityDestroy packetOffset = new PacketPlayOutEntityDestroy(offsetMap.get(player).getId());

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.equals(player)) continue;
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetEntity);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetOffset);
        }
    }


    @EventHandler
    public void onUpdate(UpdateEvent e) {

        if (!e.getType().equals(UpdateEvent.UpdateType.SECONDS)) return;

        for (Player p : Bukkit.getOnlinePlayers()) {

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

        for (Player p : Bukkit.getOnlinePlayers()) {

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
