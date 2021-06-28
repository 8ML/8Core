package com.github._8ml.core.module.game.games.Slap;
/*
Created by @8ML (https://github.com/8ML) on June 26 2021
*/

import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.module.game.exceptions.GameInitializationException;
import com.github._8ml.core.module.game.games.Slap.kits.DefaultKit;
import com.github._8ml.core.module.game.manager.Game;
import com.github._8ml.core.module.game.manager.kit.Kit;
import com.github._8ml.core.module.game.manager.map.Map;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import com.github._8ml.core.module.game.manager.team.Team;
import com.github._8ml.core.utils.DeveloperMode;
import com.github._8ml.core.utils.ScoreBoard;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;

public class SlapGame extends Game {

    private static final int killsToWin = 15;

    private final java.util.Map<GamePlayer, Integer> playerKills = new HashMap<>();

    private int blueKills;
    private int redKills;

    public SlapGame() throws GameInitializationException {
        super("Slap",
                new Team[]{
                        new Team("Blue", ChatColor.BLUE, 1, 4),
                        new Team("Red", ChatColor.RED, 1, 4)
                },
                new Kit[]{
                        new DefaultKit()
                }, 50, 5);
        setCustomKillMsg(ChatColor.YELLOW + " slapped ");
        gameObjective = "First to 15 kills!";
    }

    public void teleportPlayer(GamePlayer player) {
        List<GamePlayer> players = player.getTeam().getPlayers();
        Map map = getMap();

        String key = player.getTeam().getName().toLowerCase() + "-spawn-" + (players.indexOf(player) + 1);

        DeveloperMode.log("Location Key: " + key);

        Location location = map.getLocation(key);

        player.getPlayer().teleport(location);
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void teleport() {
        for (GamePlayer player : getPlayers()) {
            teleportPlayer(player);
        }
    }

    @Override
    protected void initScoreboard() {

        getScoreBoard().setScoreboard(
                new String[]{ChatColor.GREEN + "SLAP"},
                new String[]{
                        ChatColor.GRAY + "",
                        ChatColor.WHITE + "Scores:",
                        ChatColor.BLUE + "Blue Team: ",
                        ChatColor.RED + "Red Team: ",
                        ChatColor.GREEN + "",
                        ChatColor.WHITE + "Your Kills: ",
                        ChatColor.WHITE + "",
                        ChatColor.WHITE + ServerConfig.SERVER_DOMAIN.toString()
                },
                new String[]{
                        "",
                        "",
                        ChatColor.GRAY + "%blueKills%",
                        ChatColor.GRAY + "%redKills%",
                        "",
                        ChatColor.GRAY + "%playerKills%",
                        "",
                        ""
                }

        );

    }

    @Override
    protected void updateScoreboard() {

    }

    @Override
    protected void updateBoardPlaceholders() {
        ScoreBoard sb = getScoreBoard();
        for (GamePlayer player : getPlayers()) {
            sb.addCustomPlaceholder("%playerKills%", String.valueOf(playerKills.get(player)));
        }
        sb.addCustomPlaceholder("%blueKills%", String.valueOf(blueKills));
        sb.addCustomPlaceholder("%redKills%", String.valueOf(redKills));
    }

    @Override
    protected void onEnd() {

    }

    @Override
    protected void onKill(GamePlayer killed, GamePlayer killer) {
        if (killer.getTeam().equals(getTeams()[0])) {
            blueKills++;
            if (blueKills >= killsToWin) {
                endGame(getTeams()[0]);
            }
        } else {
            redKills++;
            if (redKills >= killsToWin) {
                endGame(getTeams()[1]);
            }
        }
        int currentPlayerKills = playerKills.getOrDefault(killer, 0);
        playerKills.put(killer, currentPlayerKills + 1);
        teleportPlayer(killed);
    }

    @Override
    protected void onDeath(GamePlayer player) {

    }

    @Override
    protected void onJoin(GamePlayer player) {


    }

    @Override
    protected void onLeave(GamePlayer player) {

    }
}
