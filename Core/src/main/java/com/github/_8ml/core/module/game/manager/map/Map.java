package com.github._8ml.core.module.game.manager.map;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class Map {

    private final String worldName;

    private String name;
    private String[] authors;

    private final java.util.Map<String, Location> locations = new HashMap<>();
    private final java.util.Map<String, String> extras = new HashMap<>();

    public final java.util.Map<Block, Material> blockData = new HashMap<>();

    private Properties mapProperties;
    private boolean loaded = false;

    public Map(String worldName) {
        this.worldName = worldName;
    }

    public boolean load() {

        for (World world : Bukkit.getWorlds()) {
            if (world.getName().equalsIgnoreCase(worldName)) {

                try {

                    File file = new File(world.getWorldFolder().getAbsolutePath()
                            + File.separator + "map.properties");

                    if (!file.exists()) return false;

                    FileInputStream stream = new FileInputStream(file);

                    mapProperties = new Properties();
                    mapProperties.load(stream);

                    if (!mapProperties.containsKey("map-name") || !mapProperties.containsKey("authors")) return false;

                    this.name = mapProperties.getProperty("map-name");
                    this.authors = mapProperties.getProperty("authors").split(",");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                int radius;

                try {
                    radius = Integer.parseInt(mapProperties.getProperty("map-radius"));
                } catch (NumberFormatException e) {
                    radius = 100;
                }

                for (int x = 0; x < radius << 4; x++) {
                    for (int z = 0; z < radius << 4; z++) {

                        Chunk chunk = world.getChunkAt(x, z);
                        chunk.load();

                        int minX = chunk.getX() << 4;
                        int minZ = chunk.getZ() << 4;

                        int maxX = 15 | minX;
                        int maxY = chunk.getWorld().getMaxHeight();
                        int maxZ = 15 | minZ;

                        for (int xx = minX; xx < maxX; xx++) {
                            for (int yy = 0; yy < maxY; yy++) {
                                for (int zz = minZ; zz < maxZ; zz++) {

                                    Block block = chunk.getBlock(xx, yy, zz);
                                    if (block.getType().equals(Material.OAK_SIGN)) {

                                        Sign sign = (Sign) block;
                                        if (sign.getLine(0).equalsIgnoreCase("[MAP-INFO]")) {

                                            String key = sign.getLine(1);
                                            String extraValue = "";
                                            if (sign.getLines().length == 3) {
                                                extraValue = sign.getLine(2);
                                            }

                                            locations.put(key, sign.getLocation());
                                            if (!extraValue.equals("")) {
                                                extras.put(key, extraValue);
                                            }

                                        }

                                    }

                                }
                            }
                        }

                    }
                }
                this.loaded = true;
                return true;

            }
        }

        return false;
    }

    public void resetBlockData() {
        for (Block block : blockData.keySet()) {

            block.setType(blockData.get(block));

        }
        blockData.clear();
    }

    public Location getLocation(String key) {
        if (locations.containsKey(key)) {
            return locations.get(key);
        }
        return null;
    }

    public String getExtraValue(String key) {
        if (extras.containsKey(key)) {
            return extras.get(key);
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getWorldName() {
        return worldName;
    }

    public String[] getAuthors() {
        return authors;
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}
