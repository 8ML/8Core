package com.github._8ml.core.module.game.manager.map;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Maps {

    private final static List<Map> maps = new ArrayList<>();

    public static void checkForMaps() {
        for (World world : Bukkit.getWorlds()) {

            if (world.getName().equalsIgnoreCase("WaitingLobby")) continue;

            Map map = new Map(world.getName());

            if (maps.contains(map)) continue;

            if (map.load()) {
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
