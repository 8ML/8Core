package com.github._8ml.eightcore.discordcommapi.packet.packets;
/*
Created by @8ML (https://github.com/8ML) on August 20 2021
*/

import com.github._8ml.eightcore.discordcommapi.packet.Packet;
import com.github._8ml.eightcore.discordcommapi.packet.PacketType;

public class DiscordCommandPacket extends Packet {

    public DiscordCommandPacket() {
        super(PacketType.COMMAND);
    }

    @Override
    protected void onSend() {

    }
}
