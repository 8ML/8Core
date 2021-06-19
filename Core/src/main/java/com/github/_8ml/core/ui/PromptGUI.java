package com.github._8ml.core.ui;
/*
Created by @8ML (https://github.com/8ML) on June 19 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.component.components.Label;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;


/**
 * UI only consisting of one page
 */
public abstract class PromptGUI implements Listener {

    private final MPlayer player;
    private final String title;
    private final Inventory inventory;
    private final Map<Integer, Component> content;

    public PromptGUI(MPlayer player, String title) {

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);

        this.player = player;
        this.title = title;

        this.inventory = Bukkit.createInventory(null, 27, title);

        content = content();

        for (int key : content.keySet()) {

            Component contentLabel = content.get(key);

            ItemStack contentStack = contentLabel.getStack();

            this.inventory.setItem(key, contentStack);
        }

        player.getPlayer().openInventory(this.inventory);
    }

    protected abstract Map<Integer, Component> content();

    public MPlayer getPlayer() {
        return player;
    }

    public String getTitle() {
        return title;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(this.title)) {
            if (e.getWhoClicked() instanceof Player)  {

                if (e.getWhoClicked().getName().equals(player.getPlayer().getName())) {

                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();

                    for (int key : this.content.keySet()) {

                        Component component = this.content.get(key);
                        if (component instanceof Button) {

                            ((Button) component).getEventRunnable().run();

                        }

                    }

                    HandlerList.unregisterAll(this);

                }

            }
        }
    }
}
