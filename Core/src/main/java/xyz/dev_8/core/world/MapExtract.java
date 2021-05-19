package xyz.dev_8.core.world;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import xyz.dev_8.core.Core;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MapExtract {

    public static ArrayList<String> maps = new ArrayList<>();

    public void addMap(String name) {
        maps.add(name);
    }

    public void load() {
        final Date now = new Date();
        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        for (Player player : Core.onlinePlayers) {
            player.kickPlayer(ChatColor.RED + "Server is restarting...\n\n" + ChatColor.WHITE + "Server shutdown at " + format.format(now));
        }
        if (maps.size() <= 0) {
            Core.instance.getLogger().info("No maps where initalized, no maps where loaded");
        } else {
            loadMaps();
        }
    }

    private void loadMaps() {
        for (String map : maps) {
            final World world = Bukkit.getWorld(map);
            Assert.assertNotNull("World cannot be null! (loadMaps)", world);
            final File active = world.getWorldFolder();
            Bukkit.unloadWorld(world, false);
            final Chunk[] loadedChunks;
            final Chunk[] chunks = loadedChunks = world.getLoadedChunks();
            for (final Chunk chunk : loadedChunks) {
                chunk.unload(false);
            }
            try {
                FileUtils.deleteDirectory(active);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                final ZipFile zipFile = new ZipFile(map + ".zip");
                final Enumeration<?> enu = zipFile.entries();
                while (enu.hasMoreElements()) {
                    final ZipEntry zipEntry = (ZipEntry)enu.nextElement();
                    final String name = zipEntry.getName();
                    final long size = zipEntry.getSize();
                    final long compressedSize = zipEntry.getCompressedSize();
                    System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n", name, size, compressedSize);
                    final File file = new File(name);
                    if (name.endsWith("/")) {
                        file.mkdirs();
                    }
                    else {
                        final File parent = file.getParentFile();
                        if (parent != null) {
                            parent.mkdirs();
                        }
                        final InputStream is = zipFile.getInputStream(zipEntry);
                        final FileOutputStream fos = new FileOutputStream(file);
                        final byte[] bytes = new byte[1024];
                        int length;
                        while ((length = is.read(bytes)) >= 0) {
                            fos.write(bytes, 0, length);
                        }
                        is.close();
                        fos.close();
                    }
                }
                zipFile.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
