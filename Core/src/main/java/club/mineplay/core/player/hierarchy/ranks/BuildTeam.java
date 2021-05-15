package club.mineplay.core.player.hierarchy.ranks;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class BuildTeam extends Rank {

    public BuildTeam() {
        super("Build Team", "BUILD TEAM", 30.0D);
        setColor(ChatColor.DARK_GREEN, ChatColor.DARK_GREEN);
        setDescription("Build Team consists of dedicated builders that\n" +
                "make sure all the maps bring satisfaction to our players");
    }

    @Override
    public void onRegister() {

    }
}
