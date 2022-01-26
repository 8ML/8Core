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
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public abstract class CMD extends BukkitCommand {
    private String[] aliases;
    private String label;
    private String aliasUsed;
    private Ranks rank;
    private String noPerm = MessageColor.COLOR_ERROR + "You are not allowed to do this!";
    private JavaPlugin plugin;
    private String usage;

    public CMD(String label, String[] aliases, String description, String usage, Ranks rank) {
        super(label);
        this.aliases = aliases;
        this.rank = rank;
        this.label = label;
        this.description = description;
        this.usage = usage;
        setAliases(Arrays.asList(aliases));
    }

    public String getLabel() {
        return this.label;
    }

    public String[] getCommandAliases() {
        return this.aliases;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    public Ranks getRank() {
        return this.rank;
    }

    public String getAliasUsed() {
        return this.aliasUsed;
    }

    public String getUsage() {
        return MessageColor.COLOR_ERROR + this.usage;
    }

    public void setAliasUsed(String alias) {
        this.aliasUsed = alias;
    }

    public void setNoPermMessage(String permissionMessage) {
        this.noPerm = permissionMessage;
    }

    public CMD setMain(JavaPlugin main) {
        this.plugin = main;
        return this;
    }

    public abstract void execute(Player paramPlayer, String[] paramArrayOfString);

    public boolean execute(CommandSender sender, String alias, String[] args) {
        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            if (MPlayer.getMPlayer(p.getName()).getRankEnum().getRank().hasPermissionLevel(this.rank.getRank())) {
                execute(p, args);
            } else {
                p.sendMessage(noPerm);
            }
        }
        return false;
    }

    public void registerMe() {
        String version = plugin.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        String name = "org.bukkit.craftbukkit." + version + ".CraftServer";
        Class<?> craftserver = null;
        try {
            craftserver = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert craftserver != null;
        try {
            SimpleCommandMap scm = (SimpleCommandMap) craftserver.cast(plugin.getServer()).getClass().getMethod("getCommandMap")
                    .invoke(plugin.getServer());
            scm.register(plugin.getName(), this);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        CommandCenter.commandList.add(this);
    }
}
