package com.github._8ml.core.module.game.manager.map;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.utils.DeveloperMode;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class Map {

    static {
        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS mapInfo (`id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL" +
                    ", `name` VARCHAR(255) NOT NULL" +
                    ", `authors` TEXT NOT NULL" +
                    ", `locations` TEXT NOT NULL" +
                    ", `extras` TEXT NOT NULL)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static SQL sql = Core.instance.sql;

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

                    if (!file.exists()) {
                        DeveloperMode.log(world.getName() + ": does not contain map.properties file!");
                        return false;
                    }

                    FileInputStream stream = new FileInputStream(file);

                    mapProperties = new Properties();
                    mapProperties.load(stream);

                    if (!mapProperties.containsKey("map-name") || !mapProperties.containsKey("authors")) {
                        DeveloperMode.log(world.getName() + ": Invalid map properties! missing map-name or authors");
                        return false;
                    }

                    this.name = mapProperties.getProperty("map-name");
                    this.authors = mapProperties.getProperty("authors").split(",");

                    getLocationsFromDB();
                    getExtrasFromDB();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!exists()) {
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

                            int maxX = 15;
                            int maxY = chunk.getWorld().getMaxHeight();
                            int maxZ = 15;

                            for (int xx = minX; xx < maxX; xx++) {
                                for (int yy = 0; yy < maxY; yy++) {
                                    for (int zz = minZ; zz < maxZ; zz++) {

                                        Block block = chunk.getBlock(xx, yy, zz);
                                        if (block.getType().equals(Material.OAK_SIGN)) {

                                            Sign sign = (Sign) block;
                                            if (sign.getLine(0).equalsIgnoreCase("[GAME-INFO]")) {

                                                String key = sign.getLine(1);
                                                String extraValue = "";
                                                if (sign.getLines().length == 3) {
                                                    extraValue = sign.getLine(2);
                                                }

                                                locations.put(key, sign.getLocation());
                                                if (!extraValue.equals("")) {
                                                    extras.put(key, extraValue);
                                                }

                                                sign.setType(Material.AIR);

                                                DeveloperMode.log("Found sign with game-info!");

                                            }

                                        }

                                    }
                                }
                            }



                        }
                    }
                    addMapToDB();
                }

                this.loaded = true;
                return true;

            }
        }

        return false;
    }

    private boolean exists() {
        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM mapInfo WHERE `name`=?");
            st.setString(1, this.name);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                sql.closeConnection(st);
                return true;
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void addMapToDB() {

        if (exists()) return;

        try {

            StringBuilder authorsBuilder = new StringBuilder(this.authors[0]);
            for (String author : this.authors) {
                if (author.equalsIgnoreCase(this.authors[0])) continue;
                authorsBuilder.append(",").append(author);
            }

            StringBuilder locationsBuilder = new StringBuilder();
            for (String key : locations.keySet()) {

                Location location = locations.get(key);
                String locationData = location.getX() + ";"
                        + location.getY() + ";"
                        + location.getZ() + ";"
                        + location.getYaw() + ";"
                        + location.getPitch() + ";";

                locationsBuilder.append(":")
                        .append(key)
                        .append(",")
                        .append(locationData);

            }

            StringBuilder extrasBuilder = new StringBuilder();
            for (String key : extras.keySet()) {
                String value = extras.get(key);

                extrasBuilder.append(":")
                        .append(key)
                        .append(",")
                        .append(value);

            }

            PreparedStatement st = sql.preparedStatement("INSERT INTO mapInfo (name, authors, locations, extras) VALUES (?,?,?,?)");
            st.setString(1, this.name);
            st.setString(2, authorsBuilder.toString());
            st.setString(3, locationsBuilder.toString());
            st.setString(4, extrasBuilder.toString());

            try {
                st.execute();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getLocationsFromDB() {
        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM mapInfo WHERE `name`=?");
            st.setString(1, this.name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                String[] values = rs.getString("locations").split(":");
                for (String value : values) {

                    String[] map = value.split(",");
                    String[] locationData = map[1].split(";");
                    locations.put(map[0], new Location(
                       Bukkit.getWorld(this.worldName),
                       Double.parseDouble(locationData[0]),
                       Double.parseDouble(locationData[1]),
                       Double.parseDouble(locationData[2]),
                       Float.parseFloat(locationData[3]),
                       Float.parseFloat(locationData[4])
                    ));

                }

            }

            sql.closeConnection(st);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void getExtrasFromDB() {
        try {

            java.util.Map<String, String> extrasMap = new HashMap<>();

            PreparedStatement st = sql.preparedStatement("SELECT * FROM mapInfo WHERE `name`=?");
            st.setString(1, this.name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                String[] values = rs.getString("locations").split(":");
                for (String value : values) {

                    String[] map = value.split(",");
                    extras.put(map[0], map[1]);

                }

            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
