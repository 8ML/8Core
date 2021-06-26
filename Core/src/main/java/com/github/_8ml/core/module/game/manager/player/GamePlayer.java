package com.github._8ml.core.module.game.manager.player;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.module.game.manager.Game;
import com.github._8ml.core.module.game.manager.team.Team;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.currency.Coin;
import org.bukkit.ChatColor;

public class GamePlayer {

    public enum GameStatus {
        WAITING, IN_GAME, NONE
    }

    private final MPlayer player;
    private Game game;
    private GameStatus status;
    private Team team;

    public GamePlayer(MPlayer player) {
        this.player = player;
        this.status = GameStatus.NONE;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void win() {

        if (team != null) return;

        this.player.getPlayer().sendMessage(
                ChatColor.GREEN + "" + ChatColor.BOLD + "VICTORY!\n\n"
                + ChatColor.YELLOW + this.player.getPlayerStr() + " won!"
        );
        Coin.addCoins(this.player, getGame().getWinningCoins(), true);

    }

    public void loose(GamePlayer winning) {

        if (team != null) return;

        this.player.getPlayer().sendMessage(
                ChatColor.RED + "" + ChatColor.BOLD + "GAME OVER!\n\n"
                        + ChatColor.YELLOW + winning.getPlayer().getPlayerStr() + " won!"
        );
    }

    public MPlayer getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public Team getTeam() {
        return team;
    }

    public GameStatus getStatus() {
        return status;
    }
}
