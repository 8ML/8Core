package com.github._8ml.core.player.achievement;
/*
Created by @8ML (https://github.com/8ML) on 5/20/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.currency.Coin;
import com.github._8ml.core.player.level.Level;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.github._8ml.core.Core;
import com.github._8ml.core.storage.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class Achievement {

    private final static Set<Achievement> achievements = new HashSet<>();
    private final static Map<MPlayer, Set<String>> loadedPlayer = new HashMap<>();

    private final SQL sql = Core.instance.sql;

    private final String name;
    private final String description;
    private final int rewardCoins;
    private final int rewardXP;

    public Achievement(String name, String description, int rewardCoins, int rewardXP) {
        this.name = name;
        this.description = description;
        this.rewardCoins = rewardCoins;
        this.rewardXP = rewardXP;
    }

    protected abstract void onComplete(MPlayer player);

    public void complete(MPlayer player) {
        try {

            if (!hasAchievement(player)) {

                if (loadedPlayer.containsKey(player)) {
                    Set<String> current = loadedPlayer.get(player);
                    current.add(this.name);
                    loadedPlayer.put(player, current);
                } else {
                    Set<String> achievements = new HashSet<>();
                    achievements.add(this.name);
                    loadedPlayer.put(player, achievements);
                }

                PreparedStatement st = sql.preparedStatement("INSERT INTO achievements (`uuid`, `type`, `description`, `when`) VALUES (?,?,?,?)");
                st.setString(1, player.getUUID());
                st.setString(2, this.name);
                st.setString(3, this.description);
                st.setLong(4, System.currentTimeMillis());
                try {
                    st.execute();
                } finally {
                    sql.closeConnection(st);
                }

                Coin.addCoins(player, this.rewardCoins, false);
                Level.addXP(player, this.rewardXP);

                if (!player.isOffline()) {
                    Player p = player.getPlayer();

                    p.sendMessage(" ");
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD
                            + "ACHIEVEMENT GET!   " + ChatColor.DARK_GREEN + this.name + "\n"
                            + ChatColor.GOLD + "   +" + this.rewardCoins + " Coins" + "\n"
                            + ChatColor.AQUA + "   +" + this.rewardXP + " XP");
                    p.sendMessage(ChatColor.GRAY + this.description);
                    p.sendMessage(" ");
                }

                onComplete(player);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasAchievement(MPlayer player) {
        boolean result = false;

        if (loadedPlayer.containsKey(player)) {
            Set<String> achievements = loadedPlayer.get(player);
            if (achievements.contains(this.name)) {
                return true;
            }
        }

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * " +
                    "FROM achievements WHERE `uuid`=? and `type`=?");

            st.setString(1, player.getUUID());
            st.setString(2, this.name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = true;
            }
            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRewardCoins() {
        return rewardCoins;
    }

    public int getRewardXP() {
        return rewardXP;
    }

    public static void fetchAchievements(MPlayer player) {
        if (loadedPlayer.containsKey(player)) return;
        try {
            SQL sql = Core.instance.sql;
            Set<String> current = loadedPlayer.get(player) != null ? loadedPlayer.get(player)
                    : new HashSet<>();

            PreparedStatement st = sql.preparedStatement("SELECT * FROM achievements WHERE `uuid`=?");
            st.setString(1, player.getUUID());

            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                current.add(rs.getString("type"));

            }

            sql.closeConnection(st);

            loadedPlayer.put(player, current);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerAchievement(Achievement achievement) {
        achievements.add(achievement);
        Core.instance.getLogger().info("Registered Achievement " + achievement.name + "....");
    }

    public static Set<Achievement> getAchievementClasses() {
        return achievements;
    }

    public static Achievement getAchievement(Class<?> clazz) {
        for (Achievement ach : achievements) {
            if (ach.getClass().getSimpleName().equals(clazz.getSimpleName())) return ach;
        }
        return null;
    }

}
