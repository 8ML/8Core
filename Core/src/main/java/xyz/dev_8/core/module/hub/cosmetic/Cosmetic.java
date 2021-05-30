package xyz.dev_8.core.module.hub.cosmetic;
/*
Created by @8ML (https://github.com/8ML) on 5/30/2021
*/

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Assert;
import xyz.dev_8.core.Core;
import xyz.dev_8.core.player.hierarchy.Ranks;

import java.util.Objects;

public abstract class Cosmetic {

    private final String name;
    private final int coins;
    private final Ranks rank;
    private final ItemStack stack;
    private final boolean item;

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

    public static enum UseActionType {
        UNSPECIFIED, LEFT_CLICK, RIGHT_CLICK, LEFT_CLICK_BLOCK, RIGHT_CLICK_BLOCK, RIGHT_CLICK_PLAYER, LEFT_CLICK_PLAYER
    }

    public Cosmetic(String name, int coins, ItemStack stack, Ranks rank) {

        this.name = name;
        this.coins = coins;
        this.rank = rank;
        this.stack = stack;
        this.item = stack != null;

        if (this.item) {
            ItemMeta meta = this.stack.getItemMeta();
            Assert.assertNotNull("Meta cannot be null (Cosmetic constructor)", meta);
            meta.setDisplayName(ChatColor.YELLOW + getName());
            setStackMeta(meta);
        }

    }

    public void equip(Player player) {
        if (item) {
            player.getInventory().setItem(2, this.stack);
        }
        onEquip();
    }

    protected void setStackMeta(ItemMeta meta) {
        this.stackMeta = meta;
    }

    protected void setCoolDown(long seconds) {
        this.coolDown = seconds * 1000;
    }

    protected abstract void onEquip();

    protected abstract void onUse(UseAction action);

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public Ranks getRank() {
        return rank;
    }

    public ItemStack getStack() {
        return stack;
    }

    public ItemMeta getStackMeta() {
        return stackMeta;
    }

    public boolean isItem() {
        return item;
    }

    public long getCoolDown() {
        return this.coolDown;
    }

}
