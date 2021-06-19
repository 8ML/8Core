package com.github._8ml.core.module.hub.cosmetic.cosmetics;
/*
Created by @8ML (https://github.com/8ML) on 5/30/2021
*/

import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.utils.DeveloperMode;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.junit.Assert;
import com.github._8ml.core.player.hierarchy.Ranks;

import java.util.Arrays;

public class StickCosmetic extends Cosmetic {

    public StickCosmetic() {
        super("Stick", 100, new ItemStack(Material.STICK), ChatColor.GRAY
                + "Slap some bi****", CosmeticType.GADGET, Ranks.DEFAULT);

        setCoolDown(10L);
    }

    @Override
    protected void onEquip() {

    }

    @Override
    protected void onUse(UseAction action) {
        DeveloperMode.log("OnUse Called (StickCosmetic)");
        if (action.getType().equals(UseActionType.LEFT_CLICK_PLAYER)) {

            DeveloperMode.log("UseActionType Passed (StickCosmetic)");

            Player p = action.getPlayerInteractedAt();

            Vector vec = p.getLocation().getDirection().multiply(1.2);

            p.setVelocity(vec);

        }
    }
}
