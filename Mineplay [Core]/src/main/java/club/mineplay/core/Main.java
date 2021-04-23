package club.mineplay.core;
/*
Created by Sander on 4/23/2021
*/

import org.bukkit.plugin.java.JavaPlugin;
import club.mineplay.core.storage.SQL;
import club.mineplay.core.storage.file.PluginFile;

public class Main extends JavaPlugin {

    public static Main instance;

    public SQL sql;

    public PluginFile sqlYML;
    public PluginFile configYML;

    public Main() {

        instance = this;

        initFiles();
        initSql();
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
    }

    @Override
    public void onEnable() { new Main(); }

    @Override
    public void onDisable() { }
}
