/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
