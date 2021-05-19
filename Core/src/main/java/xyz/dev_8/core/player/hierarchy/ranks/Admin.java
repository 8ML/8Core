package xyz.dev_8.core.player.hierarchy.ranks;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import xyz.dev_8.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class Admin extends Rank {

    public Admin() {
        super("Admin", "Admin", 90.0D);
        setColor(ChatColor.RED, ChatColor.RED);
        setDescription("Administrators are responsible for\n" +
                       "managing their respective teams\n" +
                       "and ongoing projects.");
    }

    @Override
    public void onRegister() {

    }
}
