package com.github._8ml.core.config;
/*
Created by @8ML (https://github.com/8ML) on 5/19/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.storage.file.PluginFile;
import org.bukkit.Location;

public class ServerConfig {

    private static final PluginFile config = Core.instance.configYML;

    /**
     *
     * DEVELOPMENT PURPOSES ONLY!
     *
     * Set this to true to print debug information to console and enable commands registered with CommandCenter.registerTestCommand
     * Its very important to set this to false when using this on a live server as any rank will have access to set their rank.
     */
    public static final boolean developmentMode = true;

    public static Location spawnPoint;

    public static String serverName = config.getString("options.servername");

    public static String serverDomain = config.getString("options.serverDomain");

    public static String serverStoreDomain = config.getString("options.ServerStoreDomain");

    public static String serverAppealDomain = config.getString("options.ServerAppealDomain");

    public static String serverDiscordLink = config.getString("options.serverDiscordLink");

    public static void refreshValues() {

    }

    public static void reloadAllConfigs() {
        for (ConfigurationReload classToReload : ConfigurationReload.classes) {
            classToReload.reloadConfigs();
        }
    }

}
