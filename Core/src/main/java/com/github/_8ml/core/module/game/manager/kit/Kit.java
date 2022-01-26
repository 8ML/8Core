/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.game.manager.kit;
/*
Created by @8ML (https://github.com/8ML) on June 26 2021
*/

import com.github._8ml.core.module.game.manager.player.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public abstract class Kit {

    public static class ItemInfo {
        private final int slot;
        private final boolean armor;

        public ItemInfo(int slot, boolean armor) {
            this.slot = slot;
            this.armor = armor;
        }

        int getSlot() {
            return slot;
        }

        boolean isArmor() {
            return armor;
        }
    }

    private final int price;
    private final String name;
    private final String description;
    private final ItemStack display;

    public Kit(String name, String description, ItemStack display, int price) {
        this.name = name;
        this.display = display;
        this.price = price;
        this.description = description;
    }

    protected abstract Map<ItemStack, ItemInfo> getItems();
    protected abstract void onApply(GamePlayer player);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getDisplay() {
        return display;
    }

    public int getPrice() {
        return price;
    }

    public void apply(GamePlayer player) {
        Map<ItemStack, ItemInfo> items = getItems();
        Player bukkitPlayer = player.getPlayer();

        for (ItemStack stack : items.keySet()) {

            ItemInfo info = items.get(stack);


            if (info.isArmor()) {

                switch (info.getSlot()) {
                    case 1:
                        bukkitPlayer.getInventory().setBoots(stack);
                        break;
                    case 2:
                        bukkitPlayer.getInventory().setLeggings(stack);
                        break;
                    case 3:
                        bukkitPlayer.getInventory().setChestplate(stack);
                        break;
                    case 4:
                        bukkitPlayer.getInventory().setHelmet(stack);
                }

                return;

            }
            bukkitPlayer.getInventory().setItem(info.getSlot(), stack);

        }
        onApply(player);
    }

}
