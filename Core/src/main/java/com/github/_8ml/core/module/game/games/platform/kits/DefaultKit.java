package com.github._8ml.core.module.game.games.platform.kits;
/*
Created by @8ML (https://github.com/8ML) on August 07 2021
*/

import com.github._8ml.core.module.game.manager.kit.Kit;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Map;

public class DefaultKit extends Kit {

    public DefaultKit() {
        super("Default", "The default kit", new ItemStack(Material.DIRT), 0);
    }

    @Override
    protected Map<ItemStack, ItemInfo> getItems() {
        return Collections.emptyMap();
    }

    @Override
    protected void onApply(GamePlayer player) {

    }
}
