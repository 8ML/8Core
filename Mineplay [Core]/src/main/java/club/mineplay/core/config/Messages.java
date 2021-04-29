package club.mineplay.core.config;
/*
Created by Sander on 4/27/2021
*/

import org.bukkit.entity.Player;

public class Messages {

    public static void sendFailedMessage(Player player) {
        player.sendMessage(MessageColor.COLOR_ERROR + "Failed!");
    }

    public static void sendSuccessMessage(Player player) {
        player.sendMessage(MessageColor.COLOR_SUCCESS + "Done!");
    }

}
