package com.github._8ml.core.module.game.manager.team;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.module.game.manager.Game;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import com.github._8ml.core.player.currency.Coin;
import org.bukkit.ChatColor;

import java.util.LinkedList;
import java.util.List;

public class Team {

    private final List<GamePlayer> players = new LinkedList<>();

    private final String name;
    private final ChatColor color;
    private final int minPlayers, maxPlayers;

    public Team(String name, ChatColor color, int minPlayers, int maxPlayers) {
        this.name = name;
        this.color = color;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public boolean add(GamePlayer player) {
        if (players.size() >= this.maxPlayers) return false;
        players.add(player);
        return true;
    }

    public void remove(GamePlayer player) {
        players.remove(player);
    }

    public void win(Game game) {

        for (GamePlayer teamPlayer : this.players) {

            teamPlayer.getPlayer().getPlayer().sendMessage(
                    ChatColor.GREEN + "" + ChatColor.BOLD + "VICTORY!\n\n"
                    + this.color + this.name + " Team wins!"
            );
            Coin.addCoins(teamPlayer.getPlayer(), game.getWinningCoins(), true);

        }

    }

    public void loose(Team winner) {

        for (GamePlayer teamPlayer : this.players) {

            teamPlayer.getPlayer().getPlayer().sendMessage(
                    ChatColor.RED + "" + ChatColor.BOLD + "GAME OVER!\n\n"
                            + winner.getColor() + winner.getName() + " Team wins!"
            );

        }

    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }
}
