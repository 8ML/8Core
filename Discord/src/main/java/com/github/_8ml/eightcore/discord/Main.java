package com.github._8ml.eightcore.discord;
/*
Created by @8ML (https://github.com/8ML) on August 20 2021
*/

import com.github._8ml.eightcore.discord.sql.SQL;
import com.github._8ml.eightcore.discord.storage.file.ConfigFile;
import com.github._8ml.eightcore.discordcommapi.DiscordCommunicationAPI;
import com.github._8ml.eightcore.discordcommapi.packet.PacketManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {

    public static JDA jda;
    public static ConfigFile config;
    public static SQL sql;

    private Main() {

        config = new ConfigFile("config.properties");

        new DiscordCommunicationAPI(config.get("socketCommIP"), Integer.parseInt(config.get("socketCommPort")));
        PacketManager.initialize();

        try {
            jda = JDABuilder.createDefault(config.get("token")).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        jda.addEventListener(this);

        sql = new SQL(config.get("host"), config.get("database"),
                config.get("user"), config.get("password"), Integer.parseInt(config.get("port")));
        sql.connect();
    }


    private void registerListeners() {
    }

    private void registerCommands() {

    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        registerListeners();
        registerCommands();
    }
}
