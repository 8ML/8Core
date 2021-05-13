package club.mineplay.core.player.hierarchy.ranks;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class Admin extends Rank {

    public Admin() {
        super("Administrator", "ADMIN", 90.0D);
        setColor(ChatColor.RED, ChatColor.RED);
        setDescription("Admin's are the people that manages the server and makes sure\n" +
                "everything goes as it should, they consist of developers, quality assurance,\n" +
                "build team leaders and more");
    }

    @Override
    public void onRegister() {

    }
}
