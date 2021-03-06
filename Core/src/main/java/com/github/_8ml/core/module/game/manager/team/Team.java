/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.game.manager.team;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.module.game.manager.Game;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import com.github._8ml.core.player.currency.Coin;
import com.github._8ml.core.player.level.Level;
import org.bukkit.ChatColor;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Team {

    private final List<GamePlayer> players = new LinkedList<>();
    private final List<GamePlayer> playersInGame = new LinkedList<>();

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
        playersInGame.add(player);
        return true;
    }

    public void remove(GamePlayer player) {
        players.remove(player);
        playersInGame.remove(player);
    }

    public void playerOut(GamePlayer player) {
        playersInGame.remove(player);
    }

    public void reset() {
        for (GamePlayer player : players) {
            if (!playersInGame.contains(player)) playersInGame.add(player);
        }
    }

    public void win(Game game) {

        for (GamePlayer teamPlayer : this.players) {

            teamPlayer.getPlayer().sendMessage("");
            teamPlayer.getPlayer().sendMessage(
                    ChatColor.GREEN + "" + ChatColor.BOLD + "VICTORY!\n\n"
                            + this.color + this.name + " Team wins!"
            );
            teamPlayer.getPlayer().sendMessage("");
            Coin.addCoins(teamPlayer.getMPlayer(), game.getWinningCoins(), true);
            Level.addLevel(teamPlayer.getMPlayer(), (int) (Math.random() * (Game.WIN_XP_MAX - Game.WIN_XP_MIN)) + Game.WIN_XP_MIN);
        }

    }

    public void loose(Team winner) {

        for (GamePlayer teamPlayer : this.players) {

            teamPlayer.getPlayer().sendMessage("");
            teamPlayer.getPlayer().sendMessage(
                    ChatColor.RED + "" + ChatColor.BOLD + "GAME OVER!\n\n"
                            + winner.getColor() + winner.getName() + " Team wins!"
            );
            teamPlayer.getPlayer().sendMessage("");
            Level.addLevel(teamPlayer.getMPlayer(), (int) (Math.random() * (Game.LOOSE_XP_MAX - Game.LOOSE_XP_MIN)) + Game.LOOSE_XP_MIN);


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

    public List<GamePlayer> getPlayersInGame() {
        return playersInGame;
    }
}
