package club.mineplay.core.utils;
/*
Created by Sander on 5/8/2021
*/

import club.mineplay.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
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
            } else return;

        }

        Location location = player.getLocation();


        Entity entity = Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.ARMOR_STAND);
        entity.setCustomName(title);
        entity.setCustomNameVisible(true);

        entity.setGravity(false);

        ((ArmorStand) entity).setVisible(false);

        entityID.add(entity.getEntityId());
        titleMap.put(player, entity);


    }

    public static void removeTitle(Player player) {
        if (titleMap.containsKey(player)) {

            entityID.remove(titleMap.get(player).getEntityId());
            titleMap.get(player).remove();
            titleMap.remove(player);

        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (titleMap.containsKey(e.getPlayer())) {

            Entity entity = titleMap.get(e.getPlayer());
            for (int i = 0; i < 10; i++) {
                entity.teleport(e.getPlayer().getLocation().subtract(0, 0.35, 0));
            }


        }
    }

}
