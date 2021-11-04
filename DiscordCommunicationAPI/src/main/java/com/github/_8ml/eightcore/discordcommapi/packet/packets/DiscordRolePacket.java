package com.github._8ml.eightcore.discordcommapi.packet.packets;
/*
Created by @8ML (https://github.com/8ML) on September 18 2021
*/

import com.github._8ml.eightcore.discordcommapi.packet.Packet;
import com.github._8ml.eightcore.discordcommapi.packet.PacketType;

public class DiscordRolePacket extends Packet {

    public DiscordRolePacket() {
        super(PacketType.ROLE);
    }

    public void send(String role, boolean override) {
        super.send(new String[]{role, String.valueOf(override)});
    }

    @Override
    protected void onSend() {

    }
}
