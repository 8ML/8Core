package club.mineplay.core.cmd;
/*
Created by Sander on 4/23/2021
*/

import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import club.mineplay.core.hierarchy.Rank;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public abstract class CMD extends BukkitCommand {
    private String[] aliases;
    private String label;
    private String aliasUsed;
    private Rank rank;
    private String noPerm = Color.RED + "You do not have permission to execute this command!";
    private JavaPlugin plugin;

    public CMD(String label, String[] aliases, String description, Rank rank) {
        super(label);
        this.aliases = aliases;
        this.rank = rank;
        this.label = label;
        this.description = description;
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

    public Rank getRank() {
        return this.rank;
    }

    public String getAliasUsed() {
        return this.aliasUsed;
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
            if (true) {
                execute(p, args);
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

