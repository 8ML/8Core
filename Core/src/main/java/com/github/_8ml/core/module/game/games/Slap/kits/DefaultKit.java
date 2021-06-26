package com.github._8ml.core.module.game.games.Slap.kits;
/*
Created by @8ML (https://github.com/8ML) on June 26 2021
*/

import com.github._8ml.core.module.game.manager.kit.Kit;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
        ItemInfo stickInfo = new ItemInfo(4, false);

        items.put(stick, stickInfo);

        return items;
    }

    @Override
    protected void onApply(GamePlayer player) {

    }
}
