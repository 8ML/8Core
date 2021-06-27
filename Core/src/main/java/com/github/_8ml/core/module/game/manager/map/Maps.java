package com.github._8ml.core.module.game.manager.map;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.utils.DeveloperMode;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Maps {

    private final static List<Map> maps = new ArrayList<>();

    public static void checkForMaps() {
        for (File file : Objects.requireNonNull(Core.instance.getServer().getWorldContainer().listFiles())) {
            if (file.isDirectory()) {
                if (file.getName().equalsIgnoreCase("plugins") || file.getName().equalsIgnoreCase("logs")) continue;
                for (World world : Bukkit.getWorlds()) {
                    if (!world.getWorldFolder().getName().equalsIgnoreCase(file.getName())) {

                        World w = new WorldCreator(file.getName()).createWorld();

                    }
                }
            }
        }
        for (World world : Bukkit.getWorlds()) {

            if (world.getName().equalsIgnoreCase("WaitingLobby")) continue;

            Map map = new Map(world.getName());

            if (maps.contains(map)) continue;

            if (map.load()) {
                DeveloperMode.log("Map loaded! " + map.getName());
                maps.add(map);
            }

        }
    }

    public static List<Map> getLoadedMaps() {
        return maps;
    }

    public static Map nextMap(Map previous) {
        int index = maps.indexOf(previous) == maps.size() - 1
                ? 0 : maps.indexOf(previous) + 1;
        return maps.get(index);
    }

}
