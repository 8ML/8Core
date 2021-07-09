package com.github._8ml.core.module.buildserver.commands;
/*
Created by @8ML (https://github.com/8ML) on June 27 2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.module.buildserver.MapCreator;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MapCMD extends CMD {

    public MapCMD() {
        super("map", new String[0], "", "/map create <name> <author(s)>\n" +
                "/map teleport <mapName>\n" +
                "/map setlocation <key> (extraData)\n" +
                "/map addauthor <author>", Ranks.BUILD_TEAM);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length < 2) {
            paramPlayer.sendMessage(getUsage());
            return;
        }

        switch (paramArrayOfString[0].toUpperCase()) {
            case "CREATE":
                if (paramArrayOfString.length == 3) {

                    String name = paramArrayOfString[1];
                    String authors = paramArrayOfString[2];

                    MapCreator.createMap(name, authors);

                    World world = Objects.requireNonNull(MapCreator.getMap(name)).getWorld();

                    paramPlayer.teleport(world.getSpawnLocation());
                    paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Created map: " + name + "!");

                } else {
                    paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Missing authors!");
                }
                break;
            case "SETLOCATION":
                String key = paramArrayOfString[1];

                MapCreator.MapData data = MapCreator.getMap(paramPlayer.getWorld().getName());
                assert data != null;
                data.addLocation(key, paramPlayer.getLocation());

                if (paramArrayOfString.length == 3) {
                    data.addExtra(key, paramArrayOfString[2]);
                }

                paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Success! Added location with key: "
                        + key);
                break;
            case "ADDAUTHOR":
                if (paramArrayOfString.length != 2) {
                    paramPlayer.sendMessage(getUsage());
                    return;
                }

                MapCreator.MapData mapData = MapCreator.getMap(paramPlayer.getWorld().getName());
                assert mapData != null;
                mapData.addAuthor(paramArrayOfString[1]);

                paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Success! Added author: " + paramArrayOfString[1] + " to this map!");
                break;
            case "TELEPORT":
                if (paramArrayOfString.length != 2) {
                    paramPlayer.sendMessage(getUsage());
                    return;
                }

                MapCreator.MapData mapData1 = MapCreator.getMap(paramArrayOfString[1]);
                if (mapData1 == null) {
                    paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Could not find map!");
                }

                assert mapData1 != null;
                paramPlayer.teleport(mapData1.getWorld().getSpawnLocation());
                break;
            default:
                paramPlayer.sendMessage(getUsage());
                break;
        }

    }
}
