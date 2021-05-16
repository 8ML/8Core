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
        setDescription("Owners are the founders of Mineplay,\n" +
                       "managing all aspects of the network\n" +
                       "and ensuring its efficient operation.");
    }

    @Override
    public void onRegister() {

    }
}
