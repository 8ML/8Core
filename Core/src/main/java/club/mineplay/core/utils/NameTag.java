package club.mineplay.core.utils;
/*
Created by Sander on 5/8/2021
*/

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NameTag {

    public static void changeTag(Player player, String prefix, String suffix) {

        Scoreboard scoreBoard = player.getScoreboard();

        if (scoreBoard.getTeam(player.getName()) == null) {
            scoreBoard.registerNewTeam(player.getName());
        }

        Team team = scoreBoard.getTeam(player.getName());
        assert team != null;
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);

        team.addEntry(player.getName());


    }

}
