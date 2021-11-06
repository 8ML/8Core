package com.github._8ml.eightcore.discordcommapi.packet.packets;
/*
Created by @8ML (https://github.com/8ML) on September 14 2021
*/

import com.github._8ml.eightcore.discordcommapi.packet.Packet;
import com.github._8ml.eightcore.discordcommapi.packet.PacketType;

public class DiscordConnectAccountPacket extends Packet {

    public DiscordConnectAccountPacket() {
        super(PacketType.OTHER);
    }

    public void send(String discordName) {
        super.send(new String[]{"CONNECT", discordName});
    }

    @Override
    protected void onSend() {

    }
}
