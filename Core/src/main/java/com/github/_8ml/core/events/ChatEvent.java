/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.events;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.achievement.Achievement;
import com.github._8ml.core.player.achievement.achievements.ChatAchievement;
import com.github._8ml.core.player.level.Level;
import com.github._8ml.core.player.options.PlayerOptions;
import com.github._8ml.core.player.social.friend.Friend;
import com.github._8ml.core.punishment.PunishInfo;
import com.github._8ml.core.punishment.Punishment;
import com.github._8ml.core.punishment.type.Mute;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class ChatEvent implements Listener {

    public ChatEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        PunishInfo info = Punishment.getActivePunishment(player, Punishment.PunishType.MUTE);

        if (info.isActive()) {

            player.getPlayer().sendMessage(Mute.getMute(info).getPunishMessage());
            return;

        }

        //Building the Chat message
        int playerXP = player.getXP();
        int nextLevelXP = Level.getXPFromLevel(((int) Level.getLevelFromXP(playerXP, false)) + 1);

        ComponentBuilder level = new ComponentBuilder(ChatColor.GRAY + "["
                + ((int) Level.getLevelFromXP(player.getXP(), false))
                + "] ");

        level.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.YELLOW + "Network Level: "
                + ChatColor.AQUA + ((int) Level.getLevelFromXP(player.getXP(), false)))));

        ComponentBuilder componentBuilder = new ComponentBuilder()
                .append(level.create())
                .append(player.getRankEnum().getRank().getFullPrefixComponent())
                .append(player.getRankEnum().getRank().getNameColor() + player.getPlayer().getName())
                .append(": ").color(player.getRankEnum().getRank().isDefaultRank() ? ChatColor.GRAY : ChatColor.WHITE);


        //Player Mentioning && Message send
        for (Player p : Core.onlinePlayers) {

            String messageString = e.getMessage();

            if (e.getMessage().contains("@" + p.getName())
                    && !p.getName().equals(player.getPlayerStr())) {

                MPlayer pl = MPlayer.getMPlayer(p.getName());
                Friend friendManager = Friend.getFriendManager(pl);

                String mentionPreference = PlayerOptions.getPreference(pl, PlayerOptions.Preference.MENTION);

                boolean mention = false;

                if (mentionPreference.equals("FRIENDS_ONLY")) {
                    if (friendManager.isFriendsWith(player)) mention = true;
                }
                if (mentionPreference.equals("ANYONE")) {
                    mention = true;
                }

                if (mention) {
                    messageString = messageString.replaceAll("@" + p.getName(), ChatColor.YELLOW + p.getName()
                            + player.getRankEnum().getRank().getChatColor());

                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);
                }

            }

            componentBuilder.append(player.getRankEnum().getRank().getChatColor() + messageString);

            p.spigot().sendMessage(componentBuilder.create());


            /*
            Remove the "typed message" part from the componentBuilder so the
            message can be updated again for a different player scenario.
            This prevents the message from sending multiple times to some players.
            */
            List<BaseComponent> parts = componentBuilder.getParts();
            componentBuilder.removeComponent(parts.size() - 1);
        }

        //Complete Chat Achievement
        Objects.requireNonNull(Achievement.getAchievement(ChatAchievement.class)).complete(player);
    }
}
