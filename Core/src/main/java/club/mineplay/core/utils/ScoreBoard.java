package club.mineplay.core.utils;
/*
Created by Sander on 5/8/2021
*/

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

public class ScoreBoard {

    private ScoreboardManager scoreboardManager;
    private Scoreboard board;

    public void setScoreboard(String[] animationFrames, String[] objects) {

        scoreboardManager = Bukkit.getScoreboardManager();
        assert scoreboardManager != null;
        board = scoreboardManager.getNewScoreboard();

        Objective obj = board.registerNewObjective("M", "P", "L", RenderType.INTEGER);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

    }

}
