/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.storage.file;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class PluginFile extends YamlConfiguration {

    private File file;
    private String defaults;
    private JavaPlugin plugin;

    /**
     * Creates new PluginFile, without defaults
     *
     * @param plugin   Your plugin
     * @param fileName Name of the file
     */
    public PluginFile(JavaPlugin plugin, String fileName) {
        this(plugin, null, fileName, null);
    }

    /**
     * Creates new PluginFile, with defaults
     *
     * @param plugin       Your plugin
     * @param folder       Folder where the file should be stored
     * @param fileName     Name of the file
     * @param defaultsName Name of the defaults
     */
    public PluginFile(JavaPlugin plugin, String folder, String fileName, String defaultsName) {
        this.plugin = plugin;
        this.defaults = defaultsName;
        this.file = new File(plugin.getDataFolder() + (folder == null ? "" : "/" + folder), fileName);
        reload();
    }

    /**
     * Reload configuration
     */
    public void reload() {

        if (!file.exists()) {

            try {
                file.getParentFile().mkdirs();
                file.createNewFile();

            } catch (IOException exception) {
                exception.printStackTrace();
                plugin.getLogger().severe("Error while creating file " + file.getName());
            }

        }

        try {
            load(file);

            InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(plugin.getResource(defaults)));
            FileConfiguration defaultsConfig = YamlConfiguration.loadConfiguration(reader);

            setDefaults(defaultsConfig);
            options().copyDefaults(true);

            reader.close();
            save();

        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
            plugin.getLogger().severe("Error while loading file " + file.getName());

        }

    }

    /**
     * Save configuration
     */
    public void save() {

        try {
            options().indent(2);
            save(file);

        } catch (IOException exception) {
            exception.printStackTrace();
            plugin.getLogger().severe("Error while saving file " + file.getName());
        }

    }

}

