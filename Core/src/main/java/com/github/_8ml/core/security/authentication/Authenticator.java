package com.github._8ml.core.security.authentication;
/*
Created by @8ML (https://github.com/8ML) on August 20 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.discord.DiscordAccount;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.utils.ResultAction;
import com.github._8ml.eightcore.discordcommapi.packet.packets.DiscordCommandPacket;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

public class Authenticator implements Listener {

    private final static Map<MPlayer, Map<String, Boolean>> statusMap = new HashMap<>();

    private final MPlayer player;

    private boolean verified;

    public Authenticator(MPlayer player) {
        this.player = player;
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
    }

    public void verify() {

        if (this.player.isPermissible(Ranks.ADMIN, false)) {

            DiscordAccount account = new DiscordAccount(this.player);
            if (account.isConnected()) {
                DiscordCommandPacket packet = new DiscordCommandPacket();

                packet.send(new String[]{"VERIFY", account.getDiscordID()});
            } else {

                this.player.getPlayer().sendMessage(MessageColor.COLOR_ERROR + "You have no discord account connected!" +
                        "\nAs you are an administrator, Two factor authentication is required!" +
                        "\nTo use Two Factor Authentication, you need a discord account connected." +
                        "\nPlease make sure you have joined the official discord server and type your Discord Name (with id)" +
                        "\nin game. A confirmation message will be sent to your discord dm's");


                new ResultAction(this.player.getPlayerStr() + "::AWAIT_DISCORD_NAME",
                        ((data, dataArray) -> account.connectAccount((String) dataArray[0])));

                Map<String, Boolean> status = statusMap.getOrDefault(this.player, new HashMap<>());
                status.put("await_discord_name", true);
                statusMap.put(this.player, status);

            }
        }


    }

    public boolean isVerified() {
        return verified;
    }

    public MPlayer getPlayer() {
        return player;
    }

    private final Map<Player, Location> lastLocationMap = new HashMap<>();
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!e.getPlayer().equals(this.player.getPlayer())) return;
        if (!this.player.isPermissible(Ranks.ADMIN, false)) return;
        if (isVerified()) return;

        if (lastLocationMap.containsKey(e.getPlayer().getPlayer())) {
            e.getPlayer().teleport(lastLocationMap.get(e.getPlayer()));
        } else {
            lastLocationMap.put(e.getPlayer(), e.getPlayer().getLocation());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        if (!e.getPlayer().equals(this.player.getPlayer())) return;
        if (!this.player.isPermissible(Ranks.ADMIN, false)) return;
        if (isVerified()) return;
        if (!statusMap.getOrDefault(this.player, new HashMap<>())
                .getOrDefault("await_discord_name", false)) return;

        ResultAction.call(this.player.getPlayerStr() + "::AWAIT_DISCORD_NAME", new Object[]{e.getMessage()});

    }
}
