package xyz.dev_8.core.player.hierarchy.ranks;
/*
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import xyz.dev_8.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class Owner extends Rank {

    public Owner() {
        super("Owner", "Owner", 100.0D);
        setColor(ChatColor.DARK_RED, ChatColor.DARK_RED);
        setDescription("Owners are the founders of 8Core,\n" +
                       "managing all aspects of the network\n" +
                       "and ensuring its efficient operation.");
    }

    @Override
    public void onRegister() {

    }
}
