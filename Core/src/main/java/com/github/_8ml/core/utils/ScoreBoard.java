package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 5/8/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.UpdateEvent;
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

    /**
     *
     * @param animationFrames Frames for the animation
     * @param objects - Text and container for values
     * @param values - Text to show in the position corresponding to the index in objects
     */

    public void setScoreboard(String[] animationFrames, String[] objects, String[] values) {
        this.animationFrames = animationFrames;
        this.objects = objects;
        this.values = values;
    }

    public void setScoreboard(Player player) {

        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        Objective objective = board.getObjective("8Core") == null
                ? board.registerNewObjective("8Core", "dummy", "8Core", RenderType.INTEGER)
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

        Objective objective = board.getObjective("8Core");
        Assert.assertNotNull("Objective cannot be null! (updateScoreboard)", objective);
        objective.setDisplayName(frame);

    }

    public static String[] animateString(String str) {
        int[] frames = new int[2];
        List<String> framesList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {

            frames[0]++;
            if (frames[0] > 10) {
                frames[0] = 0;
                frames[1]++;
            }

            ChatColor color;

            try {
                color = frames[0] % frames[1] == 3 ? ChatColor.LIGHT_PURPLE
                        : ChatColor.WHITE;
            } catch (ArithmeticException e) {
                color = ChatColor.LIGHT_PURPLE;
            }

            framesList.add(color + "" + ChatColor.BOLD +  str.toUpperCase());

        }

        return framesList.toArray(new String[framesList.size() - 1]);

    }


    private int ticks = 0;
    @EventHandler
    public void onUpdate(UpdateEvent e) {

        if (e.getType().equals(UpdateEvent.UpdateType.TICK)) {

            ticks++;
            if (ticks < 2) return;

            for (Player player : Core.onlinePlayers) {
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


}
