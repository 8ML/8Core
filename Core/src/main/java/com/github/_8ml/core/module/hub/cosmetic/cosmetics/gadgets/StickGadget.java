/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.hub.cosmetic.cosmetics.gadgets;
/*
Created by @8ML (https://github.com/8ML) on 5/30/2021
*/

import com.github._8ml.core.config.MessageColor;
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

public class StickGadget extends Cosmetic {

    public StickGadget() {
        super("Stick", 100, new ItemStack(Material.STICK), new ItemStack(Material.STICK), MessageColor.COLOR_MAIN
                + "slap some bi****", CosmeticType.GADGET, Ranks.DEFAULT);

        setCoolDown(10L);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnEquip(Player player) {

    }

    @Override
    protected boolean onUse(UseAction action) {
        if (action.getType().equals(UseActionType.LEFT_CLICK_PLAYER)) {

            Player p = action.getPlayerInteractedAt();

            Vector vec = p.getLocation().getDirection().multiply(1.2);

            p.setVelocity(vec);

            return true;

        }

        return false;
    }

    @Override
    protected void onUpdate() {

    }
}
