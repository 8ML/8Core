/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
