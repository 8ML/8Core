/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.discord;
/*
Created by @8ML (https://github.com/8ML) on September 14 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.utils.ResultAction;
import com.github._8ml.eightcore.discordcommapi.packet.PacketType;
import com.github._8ml.eightcore.discordcommapi.packet.listener.PacketListener;
import com.github._8ml.eightcore.discordcommapi.packet.packets.DiscordConnectAccountPacket;
import com.github._8ml.eightcore.discordcommapi.packet.packets.DiscordRolePacket;
import com.github._8ml.eightcore.discordcommapi.packet.packets.Packets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscordAccount implements PacketListener {

    private final static String TABLE = "discordAccount";

    static {
        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                    "uuid VARCHAR(255)" +
                    ", discordID VARCHAR(255)" +
                    ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static SQL sql = Core.instance.sql;

    private final MPlayer player;

    public DiscordAccount(MPlayer player) {
        this.player = player;
    }

    public DiscordAccount(String discordID) {

        MPlayer player = null;

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM " + TABLE + " WHERE discordID=?");
            st.setString(1, discordID);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                player = MPlayer.getMPlayer(rs.getString("uuid"));

            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.player = player;
    }

    private void addToDatabase(String discordID) {
        try {

            PreparedStatement st = sql.preparedStatement("INSERT INTO " + TABLE + " (`uuid`,`discordID`) VALUES (?,?)");
            st.setString(1, this.player.getUUID());
            st.setString(2, discordID);

            st.execute();

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connectAccount(String discordName) {

        if (isConnected()) return;

        DiscordConnectAccountPacket packet = new DiscordConnectAccountPacket();
        packet.send(discordName);

        player.getPlayer().sendMessage(MessageColor.COLOR_SUCCESS + "Sending request... Please check your discord!");

        new ResultAction(discordName + "::result", (data, dataArray) -> {
            try {

                MPlayer player = (MPlayer) data;
                player.getPlayer().sendMessage(String.valueOf(dataArray[0]));

                if (dataArray.length > 1) {

                    switch (((String) dataArray[1]).toUpperCase()) {
                        case "SUCCESS":
                            addToDatabase((String) dataArray[2]);
                            break;
                        case "DENIED":

                    }

                }

            } catch (ClassCastException e) {
                e.printStackTrace();
            }

        }, this.player);

    }

    public void updateRole(Ranks rank) {

        DiscordRolePacket packet = new DiscordRolePacket();
        packet.send(rank.getRank().getLabel(), true);

    }

    public void addRole(Ranks rank) {

        DiscordRolePacket packet = new DiscordRolePacket();
        packet.send(rank.getRank().getLabel(), false);

    }

    public boolean isConnected() {

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM " + TABLE + " WHERE uuid=?");
            st.setString(1, this.player.getUUID());

            ResultSet rs = st.executeQuery();
            boolean result = rs.next();

            sql.closeConnection(st);

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public MPlayer getPlayer() {
        return player;
    }

    public String getDiscordID() {
        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM " + TABLE + " WHERE uuid=?");
            st.setString(1, this.player.getUUID());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                String id = rs.getString("discordID");
                sql.closeConnection(st);
                return id;
            }

            return "err";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "err";
    }

    @Override
    public void event(PacketType type, Object[] data) {

        if (!type.equals(PacketType.OTHER)) return;
        if (String.valueOf(data[1]).equalsIgnoreCase(Packets.DiscordConnectAccountPacket.toString())) {

            if (data.length < 5) return;

            String discordName = String.valueOf(data[3]);
            String result = String.valueOf(data[4]).replaceAll("&error", MessageColor.COLOR_ERROR.toString())
                    .replaceAll("&success", MessageColor.COLOR_SUCCESS.toString());

            ResultAction.call(discordName + "::result", result.split(","));

        }

    }
}
