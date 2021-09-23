package com.github._8ml.eightcore.discordcommapi.packet;
/*
Created by @8ML (https://github.com/8ML) on August 20 2021
*/

import com.github._8ml.eightcore.discordcommapi.DiscordCommunicationAPI;
import com.github._8ml.eightcore.discordcommapi.packet.listener.PacketListener;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketManager {

    private static DatagramSocket dSocket;

    public static void initialize() {
        connect();
        packetListener();
    }

    public static void connect() {
        try {
            dSocket = new DatagramSocket(DiscordCommunicationAPI.getApi().getCommPort(),
                    InetAddress.getByName(DiscordCommunicationAPI.getApi().getCommIP()));
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void sendPacket(String channel, String[] data) {

        StringBuilder dataBuf = new StringBuilder(data[0]);
        for (int i = 1; i < data.length; i++) {
            dataBuf.append(";").append(data[i]);
        }

        byte[] buf = (channel + ";" + dataBuf).getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        try {
            dSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void packetListener() {
        new Thread(() -> {
            while (true) {

                try {
                    byte[] buf = new byte[1024];

                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    dSocket.receive(packet);

                    String[] dataSpl = new String(packet.getData()).split(";");
                    String type = dataSpl[1];

                    List<Object> data = new ArrayList<>(Arrays.asList(dataSpl).subList(1, dataSpl.length));
                    PacketListener.call(PacketType.valueOf(type), data.toArray());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static DatagramSocket getDatagramSocket() {
        return dSocket;
    }

}
