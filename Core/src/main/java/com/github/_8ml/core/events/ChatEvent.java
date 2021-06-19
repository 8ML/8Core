package com.github._8ml.core.events;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.achievement.Achievement;
import com.github._8ml.core.player.achievement.achievements.ChatAchievement;
import com.github._8ml.core.player.level.Level;
import com.github._8ml.core.punishment.PunishInfo;
import com.github._8ml.core.punishment.Punishment;
import com.github._8ml.core.punishment.type.Mute;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
                .append(": ").color(player.getRankEnum().getRank().isDefaultRank() ? ChatColor.GRAY : ChatColor.WHITE)
                .append(player.getRankEnum().getRank().getChatColor() + e.getMessage());

        BaseComponent[] message = componentBuilder.create();

        Bukkit.spigot().broadcast(message);

        Objects.requireNonNull(Achievement.getAchievement(ChatAchievement.class)).complete(player);
    }
}