package com.github._8ml.core;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import com.github._8ml.core.cmd.CommandCenter;
import com.github._8ml.core.cmd.commands.*;
import com.github._8ml.core.cmd.commands.admin.*;
import com.github._8ml.core.cmd.commands.social.FriendCMD;
import com.github._8ml.core.cmd.commands.social.MessageCMD;
import com.github._8ml.core.cmd.commands.social.ReplyCMD;
import com.github._8ml.core.cmd.commands.special.TitleCMD;
import com.github._8ml.core.cmd.commands.staff.PunishCMD;
import com.github._8ml.core.cmd.commands.test.PunishCMDTEST;
import com.github._8ml.core.cmd.commands.staff.StaffChatCMD;
import com.github._8ml.core.cmd.commands.test.UpdateRankCMDTEST;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.events.ChatEvent;
import com.github._8ml.core.events.CommandEvent;
import com.github._8ml.core.events.FunEvent;
import com.github._8ml.core.events.common.JoinEvent;
import com.github._8ml.core.events.common.LeaveEvent;
import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.exceptions.ModuleNotFoundException;
import com.github._8ml.core.module.Module;
import com.github._8ml.core.module.game.GameModule;
import com.github._8ml.core.player.achievement.Achievement;
import com.github._8ml.core.player.achievement.achievements.ChatAchievement;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.storage.file.PluginFile;
import com.github._8ml.core.utils.NameTag;
import com.github._8ml.core.utils.PluginMessenger;
import com.github._8ml.core.utils.ScoreBoard;
import com.github._8ml.core.utils.TabList;
import com.github._8ml.core.world.MapExtract;
import com.github._8ml.core.module.hub.HubModule;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class Core extends JavaPlugin {

    public static Core instance;
    public static final Set<Player> onlinePlayers = new HashSet<>();

    public SQL sql;

    public PluginFile sqlYML;
    public PluginFile configYML;
    public PluginFile messagesYML;

    public PluginMessenger pluginMessenger;
    public TabList tabList;
    public ScoreBoard scoreBoard;

    public String serverName;
    public MapExtract mapExtractor;



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

    private void registerAchievements() {
        Achievement.registerAchievement(new ChatAchievement());
    }

    private final int[] timer = {0};
    private void startEvents() {
        new BukkitRunnable() {
            @Override
            public void run() {
                instance.getServer().getPluginManager().callEvent(new UpdateEvent(UpdateEvent.UpdateType.TICK));
                timer[0]++;
                if (timer[0] % 20 == 0) {
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
        CommandCenter.registerCommand(new OptionsCMD(), this);
        CommandCenter.registerCommand(new FriendCMD(), this);
        CommandCenter.registerCommand(new MessageCMD(), this);
        CommandCenter.registerCommand(new ReplyCMD(), this);
        CommandCenter.registerCommand(new ServerCMD(), this);
        CommandCenter.registerCommand(new ReportCMD(), this);
        CommandCenter.registerCommand(new PingCMD(), this);


        //TEST COMMANDS (if development mode is on)
        CommandCenter.registerTestCommand(new UpdateRankCMDTEST(), this);
        CommandCenter.registerTestCommand(new PunishCMDTEST(), this);
    }

    private void registerModules() {
        Module.registerModule(new HubModule());
        Module.registerModule(new GameModule());
    }

    @Override
    public void onEnable() {

        instance = this;

        initFiles();
        initSql();
        startEvents();
        registerEvents();
        registerCommands();
        registerAchievements();
        registerModules();

        this.pluginMessenger = new PluginMessenger(this);
        this.tabList = new TabList(this);
        this.scoreBoard = new ScoreBoard(this);

        this.serverName = configYML.getString("serverName");
        this.mapExtractor = new MapExtract();


        try {

            Module.setModule(this, configYML.getString("module"));

        } catch (ModuleNotFoundException e) {
            e.printStackTrace();
            this.getServer().shutdown();
        }


        if (ServerConfig.developmentMode) {
            for (int i = 0; i < 50; i++) {
                this.getLogger().warning("DEVELOPMENT MODE IS SET TO TRUE! IF THIS IS RUNNING ON A LIVE SERVER" +
                        "\nPLEASE SET developmentMode TO false IN ServerConfig class");
            }
        }

    }

    @Override
    public void onDisable() {
        mapExtractor.load();
        Module.getEnabledModule().disableModule();
    }
}
