package club.mineplay.core.utils;
/*
Created by Sander on 5/8/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.events.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class NameTag implements Listener {

    public static final Map<Player, Entity> offsetMap = new HashMap<>();
    public static final Map<Player, Entity> titleMap = new HashMap<>();
    public static final Set<Integer> entityID = new HashSet<>();

    public NameTag(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void changeTag(Player player, String prefix, String suffix, ChatColor color) {

        Scoreboard scoreBoard = player.getScoreboard();

        if (scoreBoard.getTeam(player.getName()) == null) {
            scoreBoard.registerNewTeam(player.getName());
        }

        if (scoreBoard.getObjective(player.getName()) != null)
            Objects.requireNonNull(scoreBoard.getObjective(player.getName())).unregister();

        Team team = scoreBoard.getTeam(player.getName());
        assert team != null;
        team.setColor(color);
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);

        team.addEntry(player.getName());


    }

    public static void changeTitle(Player player, String title) {

        if (titleMap.containsKey(player)) {

            if (!Objects.requireNonNull(titleMap.get(player).getCustomName()).equalsIgnoreCase(title)) {
                entityID.remove(titleMap.get(player).getEntityId());
                titleMap.get(player).remove();
                titleMap.remove(player);
                offsetMap.get(player).remove();
                offsetMap.remove(player);
            } else return;

        }

        Location location = player.getLocation();


        Entity entity = Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.ARMOR_STAND);
        entity.setCustomName(title);
        entity.setCustomNameVisible(true);

        entity.setGravity(false);

        ((ArmorStand) entity).setVisible(false);
        ((ArmorStand) entity).setMarker(true);

        Entity offset = location.getWorld().spawnEntity(location, EntityType.SLIME);
        offset.setGravity(false);

        ((Slime) offset).setInvisible(true);
        ((Slime) offset).setSize(3);

        offset.addPassenger(entity);
        player.addPassenger(offset);

        entityID.add(entity.getEntityId());
        titleMap.put(player, entity);
        offsetMap.put(player, offset);


    }

    public static void removeTitle(Player player) {
        if (titleMap.containsKey(player)) {

            entityID.remove(titleMap.get(player).getEntityId());
            titleMap.get(player).remove();
            titleMap.remove(player);
            offsetMap.get(player).remove();
            offsetMap.remove(player);

        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {

        if (!e.getType().equals(UpdateEvent.UpdateType.SECONDS)) return;

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (!titleMap.containsKey(p)) continue;

            Entity entity = titleMap.get(p);
            Entity offset = offsetMap.get(p);
            if (!p.getPassengers().contains(offset) || !offset.getPassengers().contains(entity)) {

                removeTitle(p);

            }

        }

    }

}
