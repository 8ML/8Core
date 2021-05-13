package club.mineplay.core.player.hierarchy.ranks;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class Owner extends Rank {

    public Owner() {
        super("Owner", "OWNER", 100.0D);
        setColor(ChatColor.RED, ChatColor.RED);
        setDescription("Owner consists of the founders.\nThe people that made Mineplay a reality");
    }

    @Override
    public void onRegister() {

    }
}
