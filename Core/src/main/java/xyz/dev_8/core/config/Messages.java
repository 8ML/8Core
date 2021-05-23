package xyz.dev_8.core.config;
/*
Created by @8ML (https://github.com/8ML) on 4/27/2021
*/

import org.bukkit.entity.Player;

public class Messages {

    public static void sendFailedMessage(Player player) {
        player.sendMessage(MessageColor.COLOR_ERROR + "Failed!");
    }

    public static void sendSuccessMessage(Player player) {
        player.sendMessage(MessageColor.COLOR_SUCCESS + "Done!");
    }

    public static void sendNotExistsMessage(Player player, String notExists) {
        player.sendMessage(MessageColor.COLOR_ERROR + notExists + " does not exist!");
    }

    public static void sendPlayerIsOfflineMessage(Player player, String isOffline) {
        player.sendMessage(MessageColor.COLOR_ERROR + isOffline + " is offline!");
    }

}
