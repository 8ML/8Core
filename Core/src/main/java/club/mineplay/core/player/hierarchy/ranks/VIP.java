package club.mineplay.core.player.hierarchy.ranks;
/*
Created by Sander on 4/23/2021
*/

import club.mineplay.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class VIP extends Rank {

    public VIP() {
        super("VIP", "VIP", 1.0D);
        this.setColor(ChatColor.LIGHT_PURPLE, ChatColor.LIGHT_PURPLE);
        this.setDescription("The first purchasable rank\n" +
                            "at mineplay.club/store");
    }

    @Override
    public void onRegister() {

    }
}
