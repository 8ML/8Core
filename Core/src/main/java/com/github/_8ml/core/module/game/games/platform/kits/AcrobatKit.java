package com.github._8ml.core.module.game.games.platform.kits;
/*
Created by @8ML (https://github.com/8ML) on August 07 2021
*/

import com.github._8ml.core.module.game.manager.kit.Kit;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class AcrobatKit extends Kit {

    public AcrobatKit() {
        super("Acrobat", "Gives you acrobatic skills! (Double jump)", new ItemStack(Material.LEATHER_BOOTS), 100);
    }

    @Override
    protected Map<ItemStack, ItemInfo> getItems() {
        return null;
    }

    @Override
    protected void onApply(GamePlayer player) {
        player.getPlayer().setAllowFlight(true);
    }
}
