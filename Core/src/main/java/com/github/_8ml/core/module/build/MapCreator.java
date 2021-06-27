package com.github._8ml.core.module.build;
/*
Created by @8ML (https://github.com/8ML) on June 27 2021
*/

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class MapCreator {


    static class EmptyChunkGenerator extends ChunkGenerator {

        @Override
        @Nonnull
        public ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int x, int z, @Nonnull BiomeGrid biome) {
            return createChunkData(world);
        }
    }

    public static class MapData {

        private final World world;

        private String name;
        private String authors;
        private Properties mapProperties;
        private Map<String, Location> locations = new HashMap<>();
        private Map<String, String> extras = new HashMap<>();

        public MapData(World world) {

            this.world = world;

            load();
        }

        private void load() {

            try {

                File file = new File(this.world.getWorldFolder().getAbsolutePath() + File.separator + "map.properties");
                file.createNewFile();

                FileInputStream stream = new FileInputStream(file);

                this.mapProperties = new Properties();
                this.mapProperties.load(stream);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void save() {
            try {

                File file = new File(this.world.getWorldFolder().getAbsolutePath() + File.separator + "map.properties");
                file.createNewFile();

                FileOutputStream stream = new FileOutputStream(file);

                this.mapProperties.store(stream, null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void addLocation(String key, Location location) {

            String locationBuilder = location.getX() +
                    ";" +
                    location.getY() +
                    ";" +
                    location.getZ() +
                    ";" +
                    location.getYaw() +
                    ";" +
                    location.getPitch();

            this.locations.put(key, location);

            this.mapProperties.setProperty("location." + key, locationBuilder);
            save();

        }

        public void addExtra(String key, String value) {
            this.mapProperties.put("extra." + key, value);
            save();
        }

        public void addAuthor(String author) {

            String authorsBuilder = this.authors + "," +
                    author;
            this.authors = authorsBuilder;

            this.mapProperties.setProperty("map-authors", authorsBuilder);
            save();
        }


        public World getWorld() {
            return world;
        }
    }

    public static List<World> worlds = new ArrayList<>();

    public static World createMap(String name, String authors) {

        WorldCreator wc = new WorldCreator(name);
        wc.generator(new EmptyChunkGenerator());

        World world = wc.createWorld();

        worlds.add(world);

        try {

            assert world != null;
            File file = new File(world.getWorldFolder().getAbsolutePath() + File.separator + "map.properties");
            file.createNewFile();

            Properties prop = new Properties();

            FileInputStream in = new FileInputStream(file);
            FileOutputStream out = new FileOutputStream(file);
            prop.load(in);

            prop.setProperty("map-name", name);
            prop.setProperty("map-authors", authors);

            prop.store(out, null);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return world;


    }

    public static MapData getMap(String name) {

        for (World world : worlds) {

            if (world.getName().equalsIgnoreCase(name)) {

                return new MapData(world);

            }

        }

        return null;

    }

    public static void fetchMaps() {

        List<String> excludedFolders = Arrays.asList("plugins", "logs", "crash-reports");

        for (File file : Objects.requireNonNull(Bukkit.getWorldContainer().listFiles())) {

            if (file.isDirectory() && !excludedFolders.contains(file.getName())) {

                WorldCreator wc = new WorldCreator(file.getName());
                worlds.add(wc.createWorld());

            }

        }

    }

}
