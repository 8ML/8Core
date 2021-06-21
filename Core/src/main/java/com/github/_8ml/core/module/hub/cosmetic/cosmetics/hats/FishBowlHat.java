package com.github._8ml.core.module.hub.cosmetic.cosmetics.hats;
/*
Created by @8ML (https://github.com/8ML) on June 21 2021
*/

import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FishBowlHat extends Cosmetic {

    public FishBowlHat() {
        super("Fish Bowl Hat", 500, new ItemStack(Material.GLASS), null, ChatColor.GRAY
                + "Well, at least I can see wearing this", CosmeticType.HAT, Ranks.DEFAULT);
    }

    @Override
    protected void onEquip(Player player) {
        player.getInventory().setArmorContents(new ItemStack[]{null, null, null, new ItemStack(Material.GLASS)});
    }

    @Override
    protected void onUnEquip(Player player) {
        player.getInventory().setArmorContents(null);
    }

    @Override
    protected boolean onUse(UseAction action) {
        return false;
    }

    @Override
    protected void onUpdate() {

    }
}
