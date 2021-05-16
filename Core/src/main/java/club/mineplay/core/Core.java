package club.mineplay.core;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import club.mineplay.core.cmd.CommandCenter;
import club.mineplay.core.cmd.commands.HelpCMD;
import club.mineplay.core.cmd.commands.StatsCMD;
import club.mineplay.core.cmd.commands.admin.*;
import club.mineplay.core.cmd.commands.special.TitleCMD;
import club.mineplay.core.cmd.commands.staff.PunishCMD;
import club.mineplay.core.cmd.commands.staff.PunishCMDTEST;
import club.mineplay.core.cmd.commands.staff.StaffChatCMD;
import club.mineplay.core.events.*;
import club.mineplay.core.events.common.JoinEvent;
import club.mineplay.core.events.common.LeaveEvent;
import club.mineplay.core.events.event.UpdateEvent;
import club.mineplay.core.storage.SQL;
import club.mineplay.core.storage.file.PluginFile;
import club.mineplay.core.utils.NameTag;
import club.mineplay.core.utils.PluginMessenger;
import club.mineplay.core.utils.ScoreBoard;
import club.mineplay.core.utils.TabList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Core extends JavaPlugin {

    public static Core instance;

    public SQL sql;

    public PluginFile sqlYML;
    public PluginFile configYML;
    public PluginFile messagesYML;

    public PluginMessenger pluginMessenger;
    public TabList tabList;
    public ScoreBoard scoreBoard;

    public String serverName;


    private void initSql() {
        this.sql = new SQL(sqlYML.getString("database"),
                sqlYML.getString("user"),
                sqlYML.getString("host"), sqlYML.getString("password"),
                sqlYML.getInt("port"));
        if (this.sql.testConnection()) { this.getLogger().info("[SQL] Connection Established!"); this.sql.init(); }
        else this.getLogger().severe("[SQL] Connection could not be established!");
    }

    private void initFiles() {
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

    private void registerEvents() {
        new JoinEvent(this);
        new LeaveEvent(this);
        new ChatEvent(this);
        new CommandEvent(this);
        new FunEvent(this);

        new NameTag(this);
    }

    private final int[] timer = {0};
    private void startEvents() {
        new BukkitRunnable() {
            @Override
            public void run() {
                instance.getServer().getPluginManager().callEvent(new UpdateEvent(UpdateEvent.UpdateType.TICK));
                timer[0]++;
                if (timer[0] >= 20) {
                    instance.getServer().getPluginManager().callEvent(new UpdateEvent(UpdateEvent.UpdateType.SECONDS));
                    timer[0] = 0;
                }

            }
        }.runTaskTimer(this, 1L, 1L);
    }

    private void registerCommands() {
        CommandCenter.registerCommand(new UpdateRankCMD(), this);
        CommandCenter.registerCommand(new HelpCMD(), this);
        CommandCenter.registerCommand(new GameModeCMD(), this);
        CommandCenter.registerCommand(new LevelCMD(), this);
        CommandCenter.registerCommand(new XPCMD(), this);
        CommandCenter.registerCommand(new CoinCMD(), this);
        CommandCenter.registerCommand(new PunishCMD(), this);
        CommandCenter.registerCommand(new StatsCMD(), this);
        CommandCenter.registerCommand(new StaffChatCMD(), this);
        CommandCenter.registerCommand(new TitleCMD(), this);
        CommandCenter.registerCommand(new EACMD(), this);


        //REMEMBER TO DISABLE THESE WHEN DEVELOPMENT IS DONE.
        CommandCenter.registerCommand(new UpdateRankCMDTEST(), this);
        CommandCenter.registerCommand(new PunishCMDTEST(), this);
    }

    @Override
    public void onEnable() {

        instance = this;

        initFiles();
        initSql();
        startEvents();
        registerEvents();
        registerCommands();

        this.pluginMessenger = new PluginMessenger(this);
        this.tabList = new TabList(this);
        this.scoreBoard = new ScoreBoard(this);

        this.serverName = configYML.getString("serverName");

    }

    @Override
    public void onDisable() { }
}
