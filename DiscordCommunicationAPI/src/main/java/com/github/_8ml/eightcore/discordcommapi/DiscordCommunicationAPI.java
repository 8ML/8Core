package com.github._8ml.eightcore.discordcommapi;
/*
Created by @8ML (https://github.com/8ML) on September 12 2021
*/

public class DiscordCommunicationAPI {

    private static DiscordCommunicationAPI api;

    private final String commIP;
    private final int commPort;

    public DiscordCommunicationAPI(String commIP, int commPort) {
        this.commIP = commIP;
        this.commPort = commPort;

        api = this;
    }

    public int getCommPort() {
        return commPort;
    }

    public String getCommIP() {
        return commIP;
    }

    public static DiscordCommunicationAPI getApi() {
        return api;
    }
}
