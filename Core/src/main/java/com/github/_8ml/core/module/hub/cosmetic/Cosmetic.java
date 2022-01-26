/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.hub.cosmetic;
/*
Created by @8ML (https://github.com/8ML) on 5/30/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.module.hub.HubModule;
import com.github._8ml.core.player.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Assert;
import com.github._8ml.core.player.hierarchy.Ranks;

import java.util.Collections;

public abstract class Cosmetic implements Listener {

    private final String name;
    private final String description;
    private final int coins;
    private final Ranks rank;
    private final ItemStack stack;
    private final boolean item;
    private final CosmeticType type;
    private final ItemStack displayStack;

    private ItemMeta stackMeta;
    private long coolDown;

    public static class UseAction {

        private final UseActionType type;
        private final Player player;

        private boolean interactedAtIsPlayer;
        private boolean interactedAtIsAir;
        private boolean interactedAtIsBlock;

        private Player playerInteractedAt;
        private Block blockInteractedAt;

        public UseAction(Player player) {
            this.player = player;
            this.type = UseActionType.UNSPECIFIED;
        }

        public UseAction(Player player, UseActionType type) {
            this.player = player;
            this.type = type;
        }

        public UseAction(Player player, UseActionType type, Player playerInteractedAt) {
            this.player = player;
            this.type = type;
            this.playerInteractedAt = playerInteractedAt;
            this.interactedAtIsPlayer = true;
        }

        public UseAction(Player player, UseActionType type, Block blockInteractedAt) {
            this.player = player;
            this.type = type;
            if (blockInteractedAt.getType().equals(Material.AIR)) {
                this.interactedAtIsAir = true;
            } else {
                this.blockInteractedAt = blockInteractedAt;
                this.interactedAtIsBlock = true;
            }
        }

        public UseActionType getType() {
            return this.type;
        }

        public boolean interactedAtIsPlayer() {
            return this.interactedAtIsPlayer;
        }

        public boolean interactedAsIsAir() {
            return this.interactedAtIsAir;
        }

        public boolean interactedAsIsBlock() {
            return this.interactedAtIsBlock;
        }

        public Player getPlayer() {
            return this.player;
        }

        public Player getPlayerInteractedAt() {
            return this.playerInteractedAt;
        }

        public Block getBlockInteractedAt() {
            return this.blockInteractedAt;
        }

    }

    public enum UseActionType {
        UNSPECIFIED, LEFT_CLICK, RIGHT_CLICK, LEFT_CLICK_BLOCK, RIGHT_CLICK_BLOCK, RIGHT_CLICK_PLAYER, LEFT_CLICK_PLAYER
    }

    public enum CosmeticType {
        GADGET, OUTFIT, HAT, TITLE
    }

    public Cosmetic(String name, int coins, ItemStack displayStack, ItemStack stack, String description, CosmeticType type, Ranks rank) {

        this.name = name;
        this.description = description;
        this.coins = coins;
        this.rank = rank;
        this.stack = stack;
        this.item = stack != null;
        this.type = type;
        this.displayStack = displayStack;

        if (this.item) {
            ItemMeta meta = this.stack.getItemMeta();
            Assert.assertNotNull("Meta cannot be null (Cosmetic constructor)", meta);
            meta.setDisplayName(MessageColor.COLOR_HIGHLIGHT + getName());
            meta.setLore(Collections.singletonList(description));
            setStackMeta(meta);
            this.stack.setItemMeta(meta);
        }

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);

    }

    protected void setDisplayMeta(ItemMeta meta) {
        this.displayStack.setItemMeta(meta);
    }

    void equip(Player player) {
        if (item) {
            player.getInventory().setItem(2, this.stack);
        }
        onEquip(player);
    }

    void unEquip(Player player) {
        if (item) {
            player.getInventory().clear(2);
        }
        onUnEquip(player);
    }

    protected abstract void onEquip(Player player);

    protected abstract void onUnEquip(Player player);

    protected abstract boolean onUse(UseAction action);

    protected abstract void onUpdate();

    protected void setStackMeta(ItemMeta meta) {
        this.stackMeta = meta;
        this.stack.setItemMeta(meta);
    }

    protected void setCoolDown(long seconds) {
        this.coolDown = seconds * 1000;
    }

    protected ItemStack getStack() {
        return stack;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getCoins() {
        return coins;
    }

    public Ranks getRank() {
        return rank;
    }

    public ItemStack getItem() {
        return this.stack;
    }

    public ItemMeta getStackMeta() {
        return stackMeta;
    }

    public ItemMeta getDisplayMeta() {
        return this.displayStack.getItemMeta();
    }

    public boolean isItem() {
        return item;
    }

    public long getCoolDown() {
        return this.coolDown;
    }

    public CosmeticType getType() {
        return type;
    }

    public ItemStack getDisplayStack() {
        return this.displayStack;
    }
}
