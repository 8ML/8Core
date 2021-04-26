package club.mineplay.bungee;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.bungee.events.JoinEvent;
import club.mineplay.bungee.storage.SQL;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

    public static Main instance;

    public SQL sql;

    public void initSQL() {
        this.sql = new SQL("monke", "monke", "localhost", "monke.4994", 3306);
        if (this.sql.testConnection()) { this.getLogger().info("[SQL] Connection Established!"); }
        else this.getLogger().severe("[SQL] Connection could not be established!");
    }

    public void registerEvents() {
        new JoinEvent(this);
    }

    @Override
    public void onEnable() {

        instance = this;

        initSQL();
        registerEvents();

    }

    @Override
    public void onDisable() { }
}
