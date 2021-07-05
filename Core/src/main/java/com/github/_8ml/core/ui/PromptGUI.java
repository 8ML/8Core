package com.github._8ml.core.ui;
/*
Created by @8ML (https://github.com/8ML) on June 19 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Assert;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * UI only consisting of one page
 */
public abstract class PromptGUI implements Listener {

    private final MPlayer player;
    private final String title;
    private final Inventory inventory;
    private Map<Integer, Component> content;

    protected PromptGUI(MPlayer player, String title) {

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);

        this.player = player;
        this.title = title;

        this.inventory = Bukkit.createInventory(null, 27, title);

        display();
    }

    protected boolean autoRefresh() {
        return false;
    }

    protected long autoRefreshInterval() {
        return 0L;
    }

    protected abstract Map<Integer, Component> content();

    private void display() {
        content = content();

        for (int key : content.keySet()) {

            Component contentComponent = content.get(key);

            ItemStack contentStack = contentComponent.getStack();
            ItemMeta meta = contentStack.getItemMeta();

            Assert.assertNotNull("Meta cannot be null (PromptGUI constructor)", meta);

            meta.setDisplayName(contentComponent.getLabel());
            meta.setLore(contentComponent.getLore());

            contentStack.setItemMeta(meta);

            this.inventory.setItem(key, contentStack);
        }

        player.getPlayer().openInventory(this.inventory);
    }

    public void refresh() {
        player.getPlayer().getInventory().clear();
        display();
    }

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

                        if (key != e.getSlot()) continue;

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

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e) {
        if (e.getPlayer().getName().equals(this.player.getPlayer().getName())) {

            HandlerList.unregisterAll(this);

        }
    }

    private long refresh = 0L;
    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (this.autoRefresh()) {

            if (refresh + this.autoRefreshInterval() > System.currentTimeMillis()) {
                refresh = System.currentTimeMillis();

                refresh();
            }

        }
    }
}
