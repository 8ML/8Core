package xyz.dev_8.core.utils;
/*
Created by Sander on 22.05.2021
*/

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import xyz.dev_8.core.Core;

public class ServerUtil {

    public static void sendToServer(Player player, String server) {

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(server);

        player.sendPluginMessage(Core.instance, "BungeeCord", out.toByteArray());

    }

}
