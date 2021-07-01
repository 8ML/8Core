package com.github._8ml.core.player.hierarchy.ranks;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class VIP extends Rank {

    public VIP() {
        super("VIP", "VIP", 1.0D);
        this.setColor(ChatColor.LIGHT_PURPLE, ChatColor.LIGHT_PURPLE);
        this.setDescription("The first purchasable rank\n" +
                            "at " + ServerConfig.SERVER_STORE_DOMAIN);
    }

    @Override
    public void onRegister() {

    }
}
