/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
import com.github._8ml.core.cmd.commands.staff.StaffChatCMD;
import com.github._8ml.core.cmd.commands.staff.TPCMD;
import com.github._8ml.core.cmd.commands.staff.VanishCMD;
import com.github._8ml.core.cmd.commands.test.PunishCMDTEST;
import com.github._8ml.core.cmd.commands.test.UpdateRankCMDTEST;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.events.ChatEvent;
import com.github._8ml.core.events.CommandEvent;
import com.github._8ml.core.events.FunEvent;
import com.github._8ml.core.events.common.JoinEvent;
import com.github._8ml.core.events.common.LeaveEvent;
import com.github._8ml.core.events.event.ServerReadyEvent;
import com.github._8ml.core.events.event.ServerShutDownEvent;
import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.exceptions.ModuleNotFoundException;
import com.github._8ml.core.game.GameInfo;
import com.github._8ml.core.module.Module;
import com.github._8ml.core.module.buildserver.BuildModule;
import com.github._8ml.core.module.game.GameModule;
import com.github._8ml.core.module.game.games.platform.achievements.PlatformSurvivorAchievement;
import com.github._8ml.core.module.game.games.slap.achievements.SlapNewbieAchievement;
import com.github._8ml.core.module.hub.HubModule;
import com.github._8ml.core.player.VanishManager;
import com.github._8ml.core.player.achievement.Achievement;
import com.github._8ml.core.player.achievement.achievements.ChatAchievement;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.security.OnlyProxyJoin;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.storage.file.PluginFile;
import com.github._8ml.core.utils.*;
import com.github._8ml.core.world.MapExtract;
import com.github._8ml.eightcore.discordcommapi.DiscordCommunicationAPI;
import com.github._8ml.eightcore.discordcommapi.packet.PacketManager;
import org.bukkit.Bukkit;
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
    public PluginFile serverYML;

    public PluginMessenger pluginMessenger;
    public TabList tabList;
    public ScoreBoard scoreBoard;

    public String serverName;
    public MapExtract mapExtractor;

    private OnlyProxyJoin proxyJoin;



    private void initSql() {
        this.sql = new SQL(sqlYML.getString("database"),
                sqlYML.getString("user"),
                sqlYML.getString("host"), sqlYML.getString("password"),
                sqlYML.getInt("port"));
        if (this.sql.testConnection()) this.getLogger().info("[SQL] Connection Established!");
        else this.getLogger().severe("[SQL] Connection could not be established!");
    }

    private void initFiles() {
        this.sqlYML = new PluginFile(this, null, "sql.yml", "sql.yml");

        this.configYML = new PluginFile(this, null, "config.yml", "config.yml");

        this.messagesYML = new PluginFile(this, null, "messages.yml", "messages.yml");

        this.serverYML = new PluginFile(this, null, "server.yml", "server.yml");

        ServerConfig.initialize();
        MessageColor.initialize();
        ServerConfig.reloadAllConfigs();
    }

    private void registerEvents() {
        new JoinEvent(this);
        new LeaveEvent(this);
        new ChatEvent(this);
        new CommandEvent(this);
        new FunEvent(this);

        new NameTag(this);
        new BossBar(this);
        new VanishManager(this);
    }

    private void registerAchievements() {
        Achievement.registerAchievement(new ChatAchievement());
        Achievement.registerAchievement(new SlapNewbieAchievement());
        Achievement.registerAchievement(new PlatformSurvivorAchievement());
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
        CommandCenter.registerCommand(new ReloadConfigCMD(), this);
        CommandCenter.registerCommand(new VanishCMD(), this);
        CommandCenter.registerCommand(new TPCMD(), this);
        CommandCenter.registerCommand(new HubCMD(), this);


        //TEST COMMANDS (if development mode is on)
        CommandCenter.registerTestCommand(new UpdateRankCMDTEST(), this);
        CommandCenter.registerTestCommand(new PunishCMDTEST(), this);
    }

    private void registerModules() {
        Module.registerModule(new HubModule());
        Module.registerModule(new GameModule());
        Module.registerModule(new BuildModule());
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

        this.proxyJoin = new OnlyProxyJoin();

        this.pluginMessenger = new PluginMessenger(this);
        this.tabList = new TabList(this);
        this.scoreBoard = new ScoreBoard(this);

        this.serverName = serverYML.getString("serverName");
        this.mapExtractor = new MapExtract();


        try {

            Module.setModule(this, serverYML.getString("module"));

        } catch (ModuleNotFoundException e) {
            e.printStackTrace();
            this.getServer().shutdown();
        }


        if (ServerConfig.developmentMode) {
            for (int i = 0; i < 10; i++) {
                this.getLogger().warning("DEVELOPMENT MODE IS SET TO TRUE! IF THIS IS RUNNING ON A LIVE SERVER" +
                        "\nPLEASE SET developmentMode TO false IN ServerConfig class");
            }
        }

        //Initialize Discord Communication
        new DiscordCommunicationAPI(configYML.getString("discord.packetCommIP"),
                configYML.getInt("discord.socketCommPort"));
        PacketManager.initialize();


        Ranks.registerRanks();

        this.getServer().getPluginManager().callEvent(new ServerReadyEvent());

        //init game info
        GameInfo.init();
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getPluginManager().callEvent(new ServerShutDownEvent());
        mapExtractor.load();
        if (Module.getEnabledModule() != null)
            Module.getEnabledModule().disableModule();
    }

}
