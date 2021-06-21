package com.github._8ml.core.module.hub.item;
/*
Created by @8ML (https://github.com/8ML) on June 21 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.CosmeticUI;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.options.ui.OptionsUI;
import com.github._8ml.core.player.stats.ui.StatsUI;
import com.github._8ml.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.junit.Assert;

import java.util.Collections;

public class JoinItems implements Listener {

    public interface InteractRunnable {

        void runEvent(Player player);

    }

    public enum Items {

        STATS("Stats", new ItemStack(Material.PLAYER_HEAD), 8, "%player%'s stats", player -> {
            MPlayer pl = MPlayer.getMPlayer(player.getName());
            new StatsUI(pl, pl);
        }),
        PREFERENCES("Preferences", new ItemStack(Material.BREWING_STAND), 0, "%player%'s preferences", player -> {
            new OptionsUI(MPlayer.getMPlayer(player.getName()));
        }),
        COSMETICS("Cosmetics", new ItemStack(Material.CHEST), 4, "Cosmetics menu full of things you might not need", player -> {
            new CosmeticUI(MPlayer.getMPlayer(player.getName()));
        });

        private final ItemStack stack;
        private final int slot;
        private final String description;
        private final InteractRunnable onInteract;
        private final String displayName;

        Items(String displayName, ItemStack stack, int slot, String description, InteractRunnable runnable) {
            this.stack = stack;
            this.slot = slot;
            this.description = description;
            this.onInteract = runnable;
            this.displayName = displayName;
        }

        public ItemStack getStack(Player player) {
            ItemMeta meta = this.stack.getItemMeta();
            Assert.assertNotNull("Meta cannot be null (Items Enum) (JoinItems)", meta);

            if (this.stack.getType().equals(Material.PLAYER_HEAD)) {
                ((SkullMeta) meta).setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
            }

            meta.setDisplayName(ChatColor.YELLOW + this.displayName);

            meta.setLore(Collections.singletonList(ChatColor.GRAY + StringUtils
                    .getWithPlaceholders(MPlayer.getMPlayer(player.getName()), description)));

            this.stack.setItemMeta(meta);

            return this.stack;
        }

        public void runInteract(Player player) {
            this.onInteract.runEvent(player);
        }

        public int getSlot() {
            return slot;
        }

    }

    public JoinItems() {
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
    }

    public static void giveItems(Player player) {

        for (Items item : Items.values()) {
            player.getInventory().setItem(item.getSlot(), item.getStack(player));

        }

    }

    @EventHandler
    public void inventoryInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)
                || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            for (Items item : Items.values()) {
                if (e.getPlayer().getInventory().getHeldItemSlot() == item.getSlot()) {

                    item.runInteract(e.getPlayer());

                }
            }

        }
    }

}
