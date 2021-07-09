package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on July 09 2021
*/

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBar {


    /**
     * This will send an action bar message to the specified player
     * @param player Player to send to
     * @param message Message to display
     */
    public static void sendActionBar(Player player, String message) {

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));

    }

}
