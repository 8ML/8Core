/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.game.manager.map;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.utils.DeveloperMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Map {

    private final World world;

    private String name;
    private String[] authors;

    private final java.util.Map<String, Location> locations = new HashMap<>();
    private final java.util.Map<String, String> extras = new HashMap<>();

    public final java.util.Map<Block, Material> blockData = new HashMap<>();

    private Properties mapProperties;
    private boolean loaded = false;

    public Map(World world) {
        this.world = world;
    }

    public boolean load() {

        try {

            File file = new File(world.getWorldFolder().getAbsolutePath()
                    + File.separator + "map.properties");

            if (!file.exists()) {
                DeveloperMode.log(world.getName() + ": does not contain map.properties file!");
                return false;
            }

            FileInputStream stream = new FileInputStream(file);

            mapProperties = new Properties();
            mapProperties.load(stream);

            if (!mapProperties.containsKey("map-name") || !mapProperties.containsKey("map-authors")) {
                DeveloperMode.log(world.getName() + ": Invalid map properties! missing map-name or map-authors");
                return false;
            }

            this.name = mapProperties.getProperty("map-name");
            this.authors = mapProperties.getProperty("map-authors").split(",");

            for (Object key : mapProperties.keySet()) {

                if (key.toString().startsWith("location.")) {

                    String locationString = mapProperties.getProperty(key.toString());
                    String[] locationData = locationString.split(";");

                    Location location = new Location(
                            this.world,
                            Double.parseDouble(locationData[0]),
                            Double.parseDouble(locationData[1]),
                            Double.parseDouble(locationData[2]),
                            Float.parseFloat(locationData[3]),
                            Float.parseFloat(locationData[4])
                    );

                    String k = key.toString().replaceFirst("location.", "");

                    DeveloperMode.log(k + " Location Fetched: " + location);

                    locations.put(k, location);

                }

                if (key.toString().startsWith("extra.")) {

                    extras.put(key.toString().replaceFirst("extra.", ""), mapProperties.getProperty(key.toString()));

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        this.loaded = true;
        return true;
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

    public World getWorld() {
        return world;
    }

    public String[] getAuthors() {
        return authors;
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}
