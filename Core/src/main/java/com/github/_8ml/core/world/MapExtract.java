/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.world;
/*
Created by @mesic (https://github.com/mebsic) on 5/16/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.utils.FileUtils;
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
            player.kickPlayer(MessageColor.COLOR_ERROR + "Server is restarting...\n\n" + ChatColor.WHITE + "Server shutdown at " + format.format(now));
        }
        if (maps.size() <= 0) {
            Core.instance.getLogger().info("No maps where initialized, no maps where loaded");
        } else {
            loadMaps();
        }
    }

    private void loadMaps() {
        for (String map : maps) {
            try {
                final World world = Bukkit.getWorld(map);
                Assert.assertNotNull("World cannot be null! (loadMaps)", world);
                final File active = world.getWorldFolder();
                Bukkit.unloadWorld(world, false);
                final Chunk[] loadedChunks;
                final Chunk[] chunks = loadedChunks = world.getLoadedChunks();
                for (final Chunk chunk : loadedChunks) {
                    chunk.unload(false);
                }
                FileUtils.deleteDirectory(active);
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
            } catch (NoClassDefFoundError e) {
                /*
                For some reason, this sometimes throws classdefnotfound on the FileUtils.deleteDirectory method.
                I have still not figured out why as the class is located in the same project. It also happened
                when using apache FileUtils
                 */
                Core.instance.getLogger().info("Could not load map " + map);
            }

        }
    }
}
