/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.game.games.slap;
/*
Created by @8ML (https://github.com/8ML) on June 26 2021
*/

import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.module.game.exceptions.GameInitializationException;
import com.github._8ml.core.module.game.games.slap.achievements.SlapNewbieAchievement;
import com.github._8ml.core.module.game.games.slap.kits.DefaultKit;
import com.github._8ml.core.module.game.manager.Game;
import com.github._8ml.core.module.game.manager.kit.Kit;
import com.github._8ml.core.module.game.manager.map.Map;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import com.github._8ml.core.module.game.manager.team.Team;
import com.github._8ml.core.module.game.sfx.SoundEffect;
import com.github._8ml.core.player.achievement.Achievement;
import com.github._8ml.core.utils.DeveloperMode;
import com.github._8ml.core.utils.ScoreBoard;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SlapGame extends Game {

    private static final int killsToWin = 15;

    private final java.util.Map<GamePlayer, Integer> playerKills = new HashMap<>();

    private int blueKills;
    private int redKills;

    public SlapGame() throws GameInitializationException {
        super("slap",
                new Team[]{
                        new Team("Blue", ChatColor.BLUE, 1, 4),
                        new Team("Red", ChatColor.RED, 1, 4)
                },
                new Kit[]{
                        new DefaultKit()
                }, 50, 5);

        //Settings
        canBreakBlocks = false;
        canPlaceBlocks = false;
        allowRain = false;
        allowDayNightCycle = false;
        customKillMessage = ChatColor.YELLOW + " slapped ";
        gameObjective = "First to 15 kills!";

        //SFX
        initializeSFX();
    }

    private void initializeSFX() {

        this.sfx.put("sfx-death", new SoundEffect(Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1f, 1f));
        this.sfx.put("sfx-death-void", new SoundEffect(Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1f, 1f));

    }

    private void teleportPlayer(GamePlayer player) {
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
                new String[]{ChatColor.GREEN + "" + ChatColor.BOLD + "SLAP"},
                new String[]{
                        ChatColor.WHITE + ServerConfig.SERVER_DOMAIN.toString(),
                        ChatColor.WHITE + "",
                        ChatColor.WHITE + "Your Kills: ",
                        ChatColor.YELLOW + "" + ChatColor.BOLD,
                        ChatColor.BLUE + "Blue Team: ",
                        ChatColor.RED + "Red Team: ",
                        ChatColor.WHITE + "Scores:",
                        ChatColor.AQUA + ""
                },
                new String[]{
                        "",
                        "",
                        "%playerKills%",
                        "",
                        "%blueKills%",
                        "%redKills%",
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
            sb.addCustomPlaceholder("%playerKills%", String.valueOf(playerKills.getOrDefault(player, 0)), player.getPlayer());
        }
        sb.addCustomPlaceholder("%blueKills%", String.valueOf(blueKills));
        sb.addCustomPlaceholder("%redKills%", String.valueOf(redKills));
    }

    @Override
    protected void onEnd(Object winner) {
        this.blueKills = 0;
        this.redKills = 0;
        this.playerKills.clear();
    }

    @Override
    protected void onKill(GamePlayer killed, GamePlayer killer) {
        if (!getState().equals(GameState.IN_GAME)) return;
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
        Objects.requireNonNull(Achievement.getAchievement(SlapNewbieAchievement.class)).complete(killer.getMPlayer());
    }

    @Override
    protected void onDeath(GamePlayer player, boolean killedByPlayer) {
        if (!getState().equals(GameState.IN_GAME)) return;
        if (!killedByPlayer) {

            if (player.getTeam().equals(getTeams()[0])) {
                redKills++;
                if (redKills >= killsToWin) {
                    endGame(getTeams()[1]);
                }
            } else {
                blueKills++;
                if (blueKills >= killsToWin) {
                    endGame(getTeams()[0]);
                }
            }
            teleportPlayer(player);

        }
    }

    @Override
    protected void onJoin(GamePlayer player) {


    }

    @Override
    protected void onLeave(GamePlayer player) {

    }
}
