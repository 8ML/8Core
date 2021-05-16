package club.mineplay.core.player.hierarchy.ranks;
/*
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import club.mineplay.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class BuildTeam extends Rank {

    public BuildTeam() {
        super("Build Team", "BUILD TEAM", 30.0D);
        setColor(ChatColor.DARK_GREEN, ChatColor.DARK_GREEN);
        setDescription("Builders are level designers who build\n" +
                       "all of the maps exclusive to Mineplay.");
    }

    @Override
    public void onRegister() {

    }
}
