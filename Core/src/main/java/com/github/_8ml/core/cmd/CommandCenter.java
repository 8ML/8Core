package com.github._8ml.core.cmd;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.module.Module;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CommandCenter {

    public static List<CMD> commandList = new ArrayList<>();

    public static void registerCommand(CMD cmd, JavaPlugin plugin) {
        cmd.setMain(plugin).registerMe();
        plugin.getLogger().info("Registering command " + cmd.getLabel() + "...");
    }

    public static void registerCommand(CMD cmd, JavaPlugin plugin, Module module) {
        if (!Module.getEnabledModule().equals(module)) return;
        cmd.setMain(plugin).registerMe();
        plugin.getLogger().info("[" + module.getClass().getSimpleName() + "] " + "Registering command " + cmd.getLabel() + "...");
    }

    public static void registerTestCommand(CMD cmd, JavaPlugin plugin) {
        if (!ServerConfig.developmentMode) return;
        cmd.setMain(plugin).registerMe();
        plugin.getLogger().warning("Registering TEST command " + cmd.getLabel() + "...");
    }

}
