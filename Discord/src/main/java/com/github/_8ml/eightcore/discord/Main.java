/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
