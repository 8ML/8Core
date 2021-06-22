package com.github._8ml.core.module.hub.cosmetic.cosmetics.outfits;
/*
Created by @8ML (https://github.com/8ML) on June 22 2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.utils.ArmorUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AstronautOutfit extends Cosmetic {

    public AstronautOutfit() {
        super("Astronaut Outfit", 5000, new ItemStack(Material.GLASS), null, MessageColor.COLOR_MAIN
                + "I'm on the moon!  oh wait never mind.", CosmeticType.OUTFIT, Ranks.DEFAULT);
    }

    @Override
    protected void onEquip(Player player) {
        ItemStack spaceHelmet = new ItemStack(Material.GLASS);
        List<ItemStack> armor = ArmorUtils.createLeatherArmor(Color.WHITE);

        player.getInventory().setArmorContents(new ItemStack[]{
                armor.get(0), armor.get(1), armor.get(2), spaceHelmet
        });

        player.addPotionEffect(new PotionEffect(
                PotionEffectType.JUMP,999999, 2, false, false, false));
    }

    @Override
    protected void onUnEquip(Player player) {
        player.getInventory().setArmorContents(null);
        player.removePotionEffect(PotionEffectType.JUMP);
    }

    @Override
    protected boolean onUse(UseAction action) {
        return false;
    }

    @Override
    protected void onUpdate() {

    }
}
