package club.mineplay.core;
/*
Created by Sander on 4/23/2021
*/

import club.mineplay.core.cmd.CommandCenter;
import club.mineplay.core.cmd.commands.HelpCMD;
import club.mineplay.core.cmd.commands.admin.*;
import club.mineplay.core.cmd.commands.staff.PunishCMD;
import club.mineplay.core.cmd.commands.staff.PunishCMDTEST;
import club.mineplay.core.events.ChatEvent;
import club.mineplay.core.events.CommandEvent;
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


    public void initSql() {
        this.sql = new SQL("monke", "monke", "localhost", "monke.4994", 3306);
        if (this.sql.testConnection()) { this.getLogger().info("[SQL] Connection Established!"); this.sql.init(); }
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
        new CommandEvent(this);
    }

    public void registerCommands() {
        CommandCenter.registerCommand(new UpdateRankCMD(), this);
        CommandCenter.registerCommand(new HelpCMD(), this);
        CommandCenter.registerCommand(new GameModeCMD(), this);
        CommandCenter.registerCommand(new LevelCMD(), this);
        CommandCenter.registerCommand(new CoinCMD(), this);
        CommandCenter.registerCommand(new PunishCMD(), this);


        //REMEMBER TO DISABLE THESE WHEN DEVELOPMENT IS DONE.
        CommandCenter.registerCommand(new UpdateRankCMDTEST(), this);
        CommandCenter.registerCommand(new PunishCMDTEST(), this);
    }

    @Override
    public void onEnable() {

        instance = this;

        initFiles();
        initSql();
        registerEvents();
        registerCommands();

    }

    @Override
    public void onDisable() { }
}
