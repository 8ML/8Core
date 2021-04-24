package club.mineplay.core;
/*
Created by Sander on 4/23/2021
*/

import club.mineplay.core.cmd.CommandCenter;
import club.mineplay.core.cmd.commands.HelpCMD;
import club.mineplay.core.cmd.commands.admin.UpdateRankCMD;
import club.mineplay.core.events.ChatEvent;
import club.mineplay.core.events.JoinEvent;
import club.mineplay.core.events.LeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import club.mineplay.core.storage.SQL;
import club.mineplay.core.storage.file.PluginFile;

public class Main extends JavaPlugin {

    public static Main instance;

    public SQL sql;

    public PluginFile sqlYML;
    public PluginFile configYML;
    public PluginFile messagesYML;

    public Main() {

        instance = this;

        initFiles();
        initSql();
        registerEvents();
        registerCommands();
    }

    public void initSql() {
        this.sql = new SQL("monkee", "root", "localhost", "yes", 3306);
        if (this.sql.testConnection()) this.getLogger().info("[SQL] Connection Established!");
        else this.getLogger().severe("[SQL] Connection could not be established!");
    }

    public void initFiles() {
        this.sqlYML = new PluginFile(this, "sql.yml", "sql.yml");
        this.sqlYML.options().copyDefaults(true);
        this.sqlYML.save();
        this.configYML = new PluginFile(this, "config.yml", "config.yml");
        this.configYML.options().copyDefaults(true);
        this.configYML.save();
        this.messagesYML = new PluginFile(this, "messages.yml", "messages.yml");
        this.messagesYML.options().copyDefaults(true);
        this.messagesYML.save();
    }

    public void registerEvents() {
        new JoinEvent(this);
        new LeaveEvent(this);
        new ChatEvent(this);
    }

    public void registerCommands() {
        CommandCenter.registerCommand(new UpdateRankCMD());
        CommandCenter.registerCommand(new HelpCMD());
    }

    @Override
    public void onEnable() { new Main(); }

    @Override
    public void onDisable() { }
}
