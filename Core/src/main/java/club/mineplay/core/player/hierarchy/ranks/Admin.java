package club.mineplay.core.player.hierarchy.ranks;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class Admin extends Rank {

    public Admin() {
        super("Admin", "ADMIN", 90.0D);
        setColor(ChatColor.RED, ChatColor.RED);
        setDescription("Administrators are responsible for\n" +
                       "managing their respective teams\n" +
                       "and ongoing projects.");
    }

    @Override
    public void onRegister() {

    }
}
