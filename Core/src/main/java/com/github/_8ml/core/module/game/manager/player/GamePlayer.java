package com.github._8ml.core.module.game.manager.player;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.module.game.manager.Game;
import com.github._8ml.core.module.game.manager.kit.Kit;
import com.github._8ml.core.module.game.manager.team.Team;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.currency.Coin;
import com.github._8ml.core.player.level.Level;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GamePlayer {

    public enum GameStatus {
        WAITING, IN_GAME, NONE
    }

    private final MPlayer mPlayer;
    private final Player player;
    private Game game;
    private GameStatus status;
    private Team team;
    private Kit kit;
    private boolean isSpectating;

    public GamePlayer(MPlayer mPlayer) {
        this.mPlayer = mPlayer;
        this.player = mPlayer.getPlayer();
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

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public void spectate() {
        this.isSpectating = true;
        for (Player player : Core.onlinePlayers) {
            player.hidePlayer(Core.instance, this.player);
        }
        this.player.setAllowFlight(true);
        this.player.setFlying(true);
    }

    public void unSpectate() {
        this.isSpectating = false;
        for (Player player : Core.onlinePlayers) {
            player.showPlayer(Core.instance, this.player);
        }
        this.player.setAllowFlight(false);
        this.player.setFlying(false);
    }

    public void win() {

        if (team != null) return;

        this.player.sendMessage("");
        this.player.sendMessage(
                ChatColor.GREEN + "" + ChatColor.BOLD + "VICTORY!\n\n"
                        + ChatColor.YELLOW + this.mPlayer.getPlayerStr() + " won!"
        );
        this.player.sendMessage("");
        Coin.addCoins(this.mPlayer, getGame().getWinningCoins(), true);
        Level.addLevel(this.mPlayer, (int) (Math.random() * (Game.WIN_XP_MAX - Game.WIN_XP_MIN)) + Game.WIN_XP_MIN);

    }

    public void loose(GamePlayer winning) {

        if (team != null) return;

        this.player.sendMessage("");
        this.player.sendMessage(
                ChatColor.RED + "" + ChatColor.BOLD + "GAME OVER!\n\n"
                        + ChatColor.YELLOW + winning.getPlayer().getName() + " won!"
        );
        this.player.sendMessage("");
        Level.addLevel(this.mPlayer, (int) (Math.random() * (Game.LOOSE_XP_MAX - Game.LOOSE_XP_MIN)) + Game.LOOSE_XP_MIN);
    }

    public MPlayer getMPlayer() {
        return mPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public Team getTeam() {
        return team;
    }

    public Kit getKit() {
        return kit;
    }

    public boolean isSpectating() {
        return isSpectating;
    }

    public GameStatus getStatus() {
        return status;
    }
}
