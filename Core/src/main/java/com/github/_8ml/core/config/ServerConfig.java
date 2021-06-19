package com.github._8ml.core.config;
/*
Created by @8ML (https://github.com/8ML) on 5/19/2021
*/

import org.bukkit.Location;

public class ServerConfig {

    /**
     *
     * DEVELOPMENT PURPOSES ONLY!
     *
     * Set this to true to print debug information to console and enable commands registered with CommandCenter.registerTestCommand
     * Its very important to set this to false when using this on a live server as any rank will have access to set their rank.
     */
    public static final boolean developmentMode = true;

    public static Location spawnPoint;

}
