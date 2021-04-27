package club.mineplay.core.cmd;
/*
Created by Sander on 4/23/2021
*/

import club.mineplay.core.config.MessageColor;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
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
        return ChatColor.RED + this.usage;
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
