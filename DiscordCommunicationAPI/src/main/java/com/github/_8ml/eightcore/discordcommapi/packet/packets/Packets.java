package com.github._8ml.eightcore.discordcommapi.packet.packets;
/*
Created by @8ML (https://github.com/8ML) on September 14 2021
*/

public enum Packets {

    DiscordCommandPacket,
    DiscordConnectAccountPacket,
    DiscordRolePacket;

    @Override
    public String toString() {
        return name();
    }
}
