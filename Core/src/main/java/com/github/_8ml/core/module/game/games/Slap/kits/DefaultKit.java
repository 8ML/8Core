package com.github._8ml.core.module.game.games.Slap.kits;
/*
Created by @8ML (https://github.com/8ML) on June 26 2021
*/

import com.github._8ml.core.module.game.manager.kit.Kit;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class DefaultKit extends Kit {

    public DefaultKit() {
        super("Default", new ItemStack(Material.STICK), 0);
    }

    @Override
    protected Map<ItemStack, ItemInfo> getItems() {
        Map<ItemStack, ItemInfo> items = new HashMap<>();

        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta meta = stick.getItemMeta();

        Assert.assertNotNull("Meta cannot be null! (Kit) (getItems)", meta);
        meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
        meta.setDisplayName(ChatColor.GREEN + "Slap that a**");

        stick.setItemMeta(meta);

        ItemInfo stickInfo = new ItemInfo(4, false);

        items.put(stick, stickInfo);

        return items;
    }

    @Override
    protected void onApply(GamePlayer player) {

    }
}
