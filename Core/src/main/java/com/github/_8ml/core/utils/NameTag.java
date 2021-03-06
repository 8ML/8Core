/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 5/8/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.UpdateEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class NameTag implements Listener {

    public static class NameProfile {

        private final Player player;
        private final String prefix;
        private final String suffix;
        private final ChatColor color;
        private final String title;

        public NameProfile(Player player, String prefix, String suffix, ChatColor color, String title) {
            this.player = player;
            this.prefix = prefix;
            this.suffix = suffix;
            this.color = color;
            this.title = title;
        }

        public Player getPlayer() {
            return player;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }

        public ChatColor getColor() {
            return color;
        }

        public String getTitle() {
            return title;
        }
    }

    private static final Map<Player, NameProfile> profileMap = new HashMap<>();

    public NameTag(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void changeTag(Player player, String prefix, String suffix, ChatColor color, String title) {

        NameProfile profile = new NameProfile(player, prefix, suffix, color, title);
        profileMap.put(player, profile);
        updateTag(player, profile);

    }

    private static void updateTag(Player player, NameProfile profile) {
        player.setPlayerListName(profile.prefix + profile.color + "" + player.getName());

        for (Player p : Core.onlinePlayers) {
            Scoreboard board = p.getScoreboard();

            Team team = board.getTeam(player.getName()) == null ? board.registerNewTeam(player.getName())
                    : board.getTeam(player.getName());

            Assert.assertNotNull("Team cannot be null (changeTag)", team);
            team.setPrefix(profile.prefix);
            team.setSuffix(" " + profile.title);
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            team.setColor(profile.color);
            team.addEntry(player.getName());

        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {

        if (e.getType().equals(UpdateEvent.UpdateType.SECONDS)) {

            for (Player player : profileMap.keySet()) {

                updateTag(player, profileMap.get(player));

            }

        }

    }

}


