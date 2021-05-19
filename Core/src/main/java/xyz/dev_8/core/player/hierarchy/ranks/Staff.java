package xyz.dev_8.core.player.hierarchy.ranks;
/*
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import xyz.dev_8.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class Staff extends Rank {

    public Staff() {
        super("Staff", "Staff", 50.0D);
        setColor(ChatColor.DARK_AQUA, ChatColor.DARK_AQUA);
        setDescription("Staff enforce rules and provide help to\n" +
                       "anyone with questions or concerns.");
    }

    @Override
    public void onRegister() {

    }
}
