package club.mineplay.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 5/8/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.events.event.UpdateEvent;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.level.Level;
import com.google.common.annotations.Beta;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;
import org.junit.Assert;

import java.util.*;

public class ScoreBoard implements Listener {

    private String[] animationFrames;
    private String[] objects;
    private String[] values;

    private final Set<Player> scoreboardSet = new HashSet<>();

    public ScoreBoard(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void setScoreboard(String[] animationFrames, String[] objects, String[] values) {
        this.animationFrames = animationFrames;
        this.objects = objects;
        this.values = values;
    }

    public void setScoreboard(Player player) {

        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        Objective objective = board.getObjective("Mineplay") == null
                ? board.registerNewObjective("Mineplay", "dummy", "Mineplay", RenderType.INTEGER)
                : board.getObjective(DisplaySlot.SIDEBAR);

        Assert.assertNotNull("Objective cannot be null! (setScoreboard)", objective);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(animationFrames[0]);

        int s = 0;
        for (String obj : objects) {

            String value = values[s];

            Team team = board.getTeam("board::" + s) == null
                    ? board.registerNewTeam("board::" + s)
                    : board.getTeam("board::" + s);

            Assert.assertNotNull("Team cannot be null! (setScoreboard)", team);

            team.addEntry(obj);
            team.setSuffix(StringUtils.getWithPlaceholders(MPlayer.getMPlayer(player.getName()), value));
            objective.getScore(obj).setScore(s);

            s++;

        }

        Team title = board.getTeam("board::title") == null
                ? board.registerNewTeam("board::title") : board.getTeam("board::title");

        Assert.assertNotNull("Title team cannot be null (setScoreboard)", title);

        player.setScoreboard(board);
        this.scoreboardSet.add(player);


    }

    private final Map<Player, Integer> previousFrameIndex = new HashMap<>();

    private void updateScoreboard(Player player) {

        Scoreboard board = player.getScoreboard();

        for (String obj : objects) {

            String value = values[Arrays.asList(objects).indexOf(obj)];

            Assert.assertNotNull("Team cannot be null! (updateScoreboard)", board.getTeam("board::" + Arrays.asList(objects).indexOf(obj)));
            Objects.requireNonNull(board.getTeam("board::" + Arrays.asList(objects).indexOf(obj)))
                    .setSuffix(StringUtils.getWithPlaceholders(MPlayer.getMPlayer(player.getName()), value));

        }

        int index = previousFrameIndex.get(player) == null ? 0 : previousFrameIndex.get(player);
        index = index >= this.animationFrames.length - 1 ? 0 : index + 1;
        String frame = animationFrames[index];
        previousFrameIndex.put(player, index);

        Objective objective = board.getObjective("Mineplay");
        Assert.assertNotNull("Objective cannot be null! (updateScoreboard)", objective);
        objective.setDisplayName(frame);

    }


    private int ticks = 0;
    @EventHandler
    public void onUpdate(UpdateEvent e) {

        if (e.getType().equals(UpdateEvent.UpdateType.TICK)) {

            ticks++;
            if (ticks < 2) return;

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!scoreboardSet.contains(player)) continue;
                updateScoreboard(player);
            }
            ticks = 0;

        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Scoreboard board = e.getPlayer().getScoreboard();
        for (Team team : board.getTeams()) {
            team.unregister();
        }
        for (Objective objective : board.getObjectives()) {
            objective.unregister();
        }
        board.clearSlot(DisplaySlot.SIDEBAR);
        scoreboardSet.remove(e.getPlayer());
    }


    //PLEASE DO NOT USE THIS METHOD AS IT DOES NOT WORK AT ALL
    @Deprecated
    public static String[] animateString(String str) {
        int frame = 0;
        int totalFrames = str.toCharArray().length * 2;
        int indexOfLastChar = str.indexOf(str.toCharArray()[str.length() -1]);
        int frames = indexOfLastChar;

        char[] charArray = str.toCharArray();

        List<String> frameBuilder = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            frameBuilder.add(ChatColor.GREEN + "" + ChatColor.BOLD + str);
            frame++;
        }

        for (int i = indexOfLastChar; i < totalFrames -1; i++) {

            StringBuilder frameStrBuilder = new StringBuilder();

            String[] chars = frames == indexOfLastChar ?
                    new String[] {ChatColor.GOLD + String.valueOf(charArray[indexOfLastChar])} :
                    new String[] {ChatColor.GOLD + String.valueOf(charArray[frames]), String.valueOf(charArray[frames + 1])};


            for (int f = 0; f < str.indexOf(chars[0]); f++) {
                frameStrBuilder.append(ChatColor.GREEN).append(ChatColor.BOLD).append(charArray[f]);
            }
            frameStrBuilder.append(chars[0]);
            if (chars.length == 2) {
                frameStrBuilder.append(chars[1]);

                for (int ff = str.indexOf(chars[1]); ff < indexOfLastChar; ff++) {
                    frameStrBuilder.append(ChatColor.WHITE).append(ChatColor.BOLD).append(charArray[ff]);
                }
            }

            frameBuilder.add(frameStrBuilder.toString());

            frames--;
            frame++;

        }

        return frameBuilder.toArray(new String[frameBuilder.size() - 1]);

    }

}
