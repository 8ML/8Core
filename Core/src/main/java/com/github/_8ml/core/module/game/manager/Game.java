package com.github._8ml.core.module.game.manager;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.events.event.ServerShutDownEvent;
import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.module.game.manager.map.Map;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import com.github._8ml.core.module.game.manager.team.Team;
import com.github._8ml.core.player.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public abstract class Game implements Listener {

    public enum GameState {
        WAITING, IN_GAME, NONE
    }

    private final String name;
    private final Team[] teams;
    private final int winningCoins;
    private final int killCoins;
    private final int maxPlayers;
    private final int minPlayers;

    private final List<GamePlayer> players = new LinkedList<>();
    private final java.util.Map<Player, GamePlayer> gamePlayerMap = new HashMap<>();

    private Map map;
    private GameState state;

    public Game(String name, Team[] teams, int winningCoins, int killCoins) {
        this.name = name;
        this.teams = teams;
        this.winningCoins = winningCoins;
        this.killCoins = killCoins;

        int max = 0;
        int min = 0;
        for (Team team : teams) {
            max += team.getMaxPlayers();
            min += team.getMinPlayers();
        }
        this.maxPlayers = max;
        this.minPlayers = min;

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
    }

    public Game(String name, int minPlayers, int maxPlayers, int winningCoins, int killCoins) {
        this.name = name;
        this.teams = new Team[0];
        this.winningCoins = winningCoins;
        this.killCoins = killCoins;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
    }

    protected abstract void onStart();

    protected abstract void onUpdate();

    protected abstract void onEnd();

    protected abstract void onKill(GamePlayer killed, GamePlayer killer);

    protected abstract void onDeath(GamePlayer player);


    public void startGame() {

        state = GameState.IN_GAME;

        for (GamePlayer player : players) {

            player.setStatus(GamePlayer.GameStatus.IN_GAME);

        }

        onStart();

    }

    public void endGame(Team winner) {

        winner.win(this);
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(winner.getName())) continue;
            team.loose(winner);
        }
        endGame();

    }

    public void endGame(GamePlayer winner) {
        if (teams.length == 0) {
            winner.win();
            for (GamePlayer player : players) {
                if (player.getPlayer().getPlayerStr()
                        .equalsIgnoreCase(winner.getPlayer().getPlayerStr())) continue;

                player.loose(winner);
            }
        }
        endGame();
    }

    public void endGame() {
        map.resetBlockData();
        onEnd();
    }


    protected GamePlayer getGamePlayer(Player player) {
        if (gamePlayerMap.containsKey(player)) {
            return gamePlayerMap.get(player);
        }
        return null;
    }

    private Team prevTeamAddedTo;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        if (MPlayer.getMPlayer(e.getPlayer().getName()).isVanished()) return;

        GamePlayer player = new GamePlayer(MPlayer.getMPlayer(e.getPlayer().getName()));
        player.setGame(this);

        Bukkit.broadcastMessage((teams.length == 0 ? ChatColor.GRAY :
                player.getTeam().getColor()) + e.getPlayer().getName() + ChatColor.GOLD + " joined the game!"
                + ChatColor.YELLOW + "("
                + ChatColor.AQUA + players.size() + ChatColor.YELLOW
                + "/" + ChatColor.AQUA + maxPlayers + ChatColor.YELLOW + ")");

        int index = -1;
        List<Team> teamsList = new ArrayList<>();
        for (Team team : teams) {
            if (teamsList.indexOf(team) <= index) continue;
            if (team.getPlayers().size() >= team.getMaxPlayers()) continue;
            team.add(player);
            index = index >= teamsList.size() - 1 ? -1 : index + 1;
        }


    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        GamePlayer player = getGamePlayer(e.getPlayer());

        Bukkit.broadcastMessage((teams.length == 0 ? ChatColor.GRAY :
                player.getTeam().getColor()) + e.getPlayer().getName() + ChatColor.GOLD + " left the game!"
                + (this.state.equals(GameState.WAITING) ? ChatColor.YELLOW + "("
                + ChatColor.AQUA + players.size() + ChatColor.YELLOW
                + "/" + ChatColor.AQUA + maxPlayers + ChatColor.YELLOW + ")" : ""));


        if (teams.length != 0) {
            player.getTeam().remove(player);
        }
        players.remove(player);
        gamePlayerMap.remove(e.getPlayer());

        if (this.state.equals(GameState.IN_GAME)) {
            if (players.size() > 2) {
                if (teams.length == 0) {
                    endGame(players.get(0));
                } else {
                    endGame(players.get(0).getTeam());
                }
            }

            int teamsWithNone = 0;

            for (Team team : teams) {
                if (team.getPlayers().size() == 0) teamsWithNone++;
            }

            if (Math.max(teamsWithNone, teams.length) - Math.min(teamsWithNone, teams.length) == 1) {
                endGame(teams[0]);
            }
        }

    }

    @EventHandler
    public void onConnect(PlayerLoginEvent e) {

        if (MPlayer.getMPlayer(e.getPlayer().getName()).isVanished()) return;

        if (Bukkit.getOnlinePlayers().size() >= maxPlayers) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, MessageColor.COLOR_ERROR + "Game is full!");
        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        onUpdate();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        onDeath(getGamePlayer(e.getEntity()));
        onKill(getGamePlayer(e.getEntity()), getGamePlayer(e.getEntity().getKiller()));
    }

    @EventHandler
    public void onBlockChangePlaced(BlockPlaceEvent e) {
        if (map.blockData.containsKey(e.getBlock())) return;
        map.blockData.put(e.getBlock(), e.getBlock().getType());
    }

    @EventHandler
    public void onBlockChangeBreak(BlockBreakEvent e) {
        if (map.blockData.containsKey(e.getBlock())) return;
        map.blockData.put(e.getBlock(), e.getBlock().getType());
    }

    @EventHandler
    public void onServerShutdown(ServerShutDownEvent e) {
        map.resetBlockData();
    }

    public String getName() {
        return name;
    }

    public Team[] getTeams() {
        return teams;
    }

    public int getWinningCoins() {
        return winningCoins;
    }

    public int getKillCoins() {
        return killCoins;
    }

}
