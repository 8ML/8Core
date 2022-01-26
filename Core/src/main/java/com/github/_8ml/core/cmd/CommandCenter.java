/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
