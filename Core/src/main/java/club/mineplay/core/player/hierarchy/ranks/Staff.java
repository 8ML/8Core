package club.mineplay.core.player.hierarchy.ranks;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class Staff extends Rank {

    public Staff() {
        super("Staff", "STAFF", 50.0D);
        setColor(ChatColor.BLUE, ChatColor.BLUE);
        setDescription("Consists of our well organized staff team\nthat makes your experience satisfactory");
    }

    @Override
    public void onRegister() {

    }
}
