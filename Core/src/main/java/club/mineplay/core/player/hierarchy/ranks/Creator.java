package club.mineplay.core.player.hierarchy.ranks;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class Creator extends Rank {

    public Creator() {
        super("Creator", "CREATOR", 10.0D);
        setColor(ChatColor.GOLD, ChatColor.GOLD);
        setDescription("Creators consists of media related influences\nsuch as Youtubers and Streamers");
    }

    @Override
    public void onRegister() {

    }
}
