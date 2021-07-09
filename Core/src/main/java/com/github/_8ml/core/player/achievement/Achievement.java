package com.github._8ml.core.player.achievement;
/*
Created by @8ML (https://github.com/8ML) on 5/20/2021
*/

import com.github._8ml.core.config.MessageColor;
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


/**
 * This class manages the achievements.
 * <p>
 * This is abstract and has to be extended by the achievement
 * types.
 * <p>
 * When completing an achievement, you need to call:
 * getAchievement(MyAchievement.class).complete(MPlayer)
 * <p>
 * (MyAchievement.class being the class that extends this class containing rewards, name, game and description)
 */
public abstract class Achievement {

    static {

        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS achievements (`uuid` VARCHAR(255) PRIMARY KEY NOT NULL" +
                    ", `type` VARCHAR(255) NOT NULL" +
                    ", `description` LONGTEXT NOT NULL" +
                    ", `when` BIGINT NOT NULL)");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private final static Set<Achievement> achievements = new HashSet<>();
    private final static Map<MPlayer, Set<String>> loadedPlayer = new HashMap<>();

    private final SQL sql = Core.instance.sql;

    private final String name;
    private final String description;
    private final String game;
    private final int rewardCoins;
    private final int rewardXP;


    /**
     * @param name        The name of the achievement
     * @param description The description of the achievement
     * @param game        The game this belongs to (Set to "" for no game)
     * @param rewardCoins The reward coins on complete
     * @param rewardXP    The reward xp on complete
     */
    public Achievement(String name, String description, String game, int rewardCoins, int rewardXP) {
        this.name = name;
        this.description = description;
        this.game = game;
        this.rewardCoins = rewardCoins;
        this.rewardXP = rewardXP;
    }


    /**
     * This is called when the achievement is completed
     *
     * @param player The player who completed it
     */
    protected abstract void onComplete(MPlayer player);


    /**
     * This will complete this achievement for the specified player and add it to the database
     *
     * @param player The player to complete
     */
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
                    p.sendMessage(MessageColor.COLOR_SUCCESS + "" + ChatColor.BOLD
                            + "ACHIEVEMENT GET!   " + ChatColor.DARK_GREEN + this.name + "\n"
                            + ChatColor.GOLD + "   +" + this.rewardCoins + " Coins" + "\n"
                            + ChatColor.AQUA + "   +" + this.rewardXP + " XP");
                    p.sendMessage(MessageColor.COLOR_MAIN + this.description);
                    p.sendMessage(" ");
                }

                onComplete(player);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This checks if the specified player has completed this achievement
     *
     * @param player The player to check
     * @return Will return true if the specified player has completed this achievement
     */
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

    public String getGame() {
        return game;
    }

    public int getRewardCoins() {
        return rewardCoins;
    }

    public int getRewardXP() {
        return rewardXP;
    }


    /**
     * This will fetch the achievements from the specified player
     *
     * @param player The player to fetch
     */
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


    /**
     * This will register the achievement
     *
     * @param achievement The class extending achievement to register
     */
    public static void registerAchievement(Achievement achievement) {
        achievements.add(achievement);
        Core.instance.getLogger().info("Registered Achievement " + achievement.name + "....");
    }


    /**
     * This will return all the registered achievements
     *
     * @return Set of Achievement
     */
    public static Set<Achievement> getAchievementClasses() {
        return achievements;
    }


    /**
     * This will get the registered achievement from the type class
     *
     * @param clazz The class extending achievement
     * @return Instance of the achievement
     */
    public static Achievement getAchievement(Class<? extends Achievement> clazz) {
        for (Achievement ach : achievements) {
            if (ach.getClass().getSimpleName().equals(clazz.getSimpleName())) return ach;
        }
        return null;
    }

}
