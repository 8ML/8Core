/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.game.games.slap.kits;
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
        super("Default", "The default kit", new ItemStack(Material.STICK), 0);
    }

    @Override
    protected Map<ItemStack, ItemInfo> getItems() {
        Map<ItemStack, ItemInfo> items = new HashMap<>();

        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta meta = stick.getItemMeta();

        Assert.assertNotNull("Meta cannot be null! (Kit) (getItems)", meta);
        meta.addEnchant(Enchantment.KNOCKBACK, 6, true);
        meta.setDisplayName(ChatColor.GREEN + "slap that a**");

        stick.setItemMeta(meta);

        ItemInfo stickInfo = new ItemInfo(4, false);

        items.put(stick, stickInfo);

        return items;
    }

    @Override
    protected void onApply(GamePlayer player) {

    }
}
