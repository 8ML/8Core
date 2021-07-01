package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on June 26 2021
*/

import com.github._8ml.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Assert;

import java.util.*;

public class InteractItem implements Listener {

    public interface InteractRunnable {
        void run(Player player);
    }

    private final List<Player> players = new ArrayList<>();
    private final Map<Player, ItemStack> prevItemMap = new HashMap<>();

    private final ItemStack stack;
    private final int slot;
    private final InteractRunnable onInteract;

    public InteractItem(String displayName, ItemStack stack, int slot, InteractRunnable onInteract) {
        ItemMeta meta = stack.getItemMeta();
        Assert.assertNotNull("Meta cannot be null (InteractItem constructor)", meta);
        meta.setDisplayName(displayName);
        stack.setItemMeta(meta);
        this.stack = stack;
        this.slot = slot;
        this.onInteract = onInteract;
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
    }

    public void addToPlayer(Player player) {
        prevItemMap.put(player, player.getInventory().getItem(this.slot));
        player.getInventory().setItem(this.slot, this.stack);
        players.add(player);
    }

    public void removeFromPlayer(Player player) {
        players.remove(player);
        player.getInventory().setItem(this.slot, prevItemMap.get(player));
        prevItemMap.remove(player);
    }

    public void removeFromEveryone() {
        for (Player player : players) {
            player.getInventory().setItem(this.slot, prevItemMap.get(player));
        }
        players.clear();
        prevItemMap.clear();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (players.contains(e.getPlayer())) {
            if (ChatColor.stripColor(Objects.requireNonNull(e.getPlayer().getInventory()
                    .getItemInMainHand().getItemMeta()).getDisplayName())
                    .equalsIgnoreCase(ChatColor.stripColor(Objects.requireNonNull(this.stack.getItemMeta()).getDisplayName()))) {

                this.onInteract.run(e.getPlayer());

            }
        }
    }

    public ItemStack getStack() {
        return stack;
    }

    public int getSlot() {
        return slot;
    }

}
