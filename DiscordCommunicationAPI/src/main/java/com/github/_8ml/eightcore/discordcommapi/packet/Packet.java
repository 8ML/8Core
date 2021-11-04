package com.github._8ml.eightcore.discordcommapi.packet;
/*
Created by @8ML (https://github.com/8ML) on August 20 2021
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Packet {

    private final PacketType type;

    public Packet(PacketType type) {
        this.type = type;
    }

    protected abstract void onSend();

    public void send(String[] data) {
        onSend();
        List<String> dataList = new ArrayList<>(Collections.singleton(this.getClass().getSimpleName()));
        dataList.addAll(Arrays.asList(data));
        PacketManager.sendPacket("discord", dataList.toArray(new String[dataList.size() - 1]));
    }


    public PacketType getType() {
        return type;
    }
}
