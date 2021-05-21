package xyz.dev_8.core.player.hierarchy.ranks;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import xyz.dev_8.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class VIP extends Rank {

    public VIP() {
        super("VIP", "VIP", 1.0D);
        this.setColor(ChatColor.LIGHT_PURPLE, ChatColor.LIGHT_PURPLE);
        this.setDescription("The first purchasable rank\n" +
                            "at dev-8.xyz/store");
    }

    @Override
    public void onRegister() {

    }
}