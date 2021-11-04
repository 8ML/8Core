package com.github._8ml.eightcore.discordcommapi.packet.listener;
/*
Created by @8ML (https://github.com/8ML) on August 20 2021
*/

import com.github._8ml.eightcore.discordcommapi.packet.PacketType;

import java.util.ArrayList;
import java.util.List;

public interface PacketListener {

    List<PacketListener> classes = new ArrayList<>();
    static void register(PacketListener listener) {
        classes.add(listener);
    }

    static void call(PacketType type, Object[] data) {
        for (PacketListener listener : classes) {
            listener.event(type, data);
        }
    }

    void event(PacketType type, Object[] data);

}
