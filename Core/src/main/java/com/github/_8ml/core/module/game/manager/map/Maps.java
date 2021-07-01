package com.github._8ml.core.module.game.manager.map;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.utils.DeveloperMode;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Maps {

    private final static List<Map> maps = new ArrayList<>();

    public static void checkForMaps() {

        maps.clear();

        List<World> worlds = new ArrayList<>();
        List<String> excludedFolders = Arrays.asList("plugins", "logs", "crash-reports");

        for (File file : Objects.requireNonNull(Core.instance.getServer().getWorldContainer().listFiles())) {
            if (file.isDirectory() && !excludedFolders.contains(file.getName())) {
                DeveloperMode.log("Creating world: " + file.getName());

                if (Bukkit.getWorld(file.getName()) != null) {
                    Bukkit.getServer().unloadWorld(Objects.requireNonNull(Bukkit.getWorld(file.getName())), false);
                }
                World world = Bukkit.getServer().createWorld(new WorldCreator(file.getName()));

                worlds.add(world);
            }
        }


        for (World world : worlds) {
            Map map = new Map(world);

            if (maps.contains(map)) continue;

            if (map.load()) {
                DeveloperMode.log("Loaded map: " + map.getName() + "!");
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
