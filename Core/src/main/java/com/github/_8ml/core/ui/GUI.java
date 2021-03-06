/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.ui;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.ui.page.Page;
import com.github._8ml.core.Core;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is to be extended by other classes.
 * Its the parent of a gui.
 * All GUI instances need at least one page to be able to show anything
 */
public abstract class GUI implements Listener {

    private final String session;
    private final MPlayer player;
    private final List<Page> pages = new ArrayList<>();

    private Page currentPage;


    /**
     *
     * @param player Player to show this gui to
     */
    public GUI(MPlayer player) {

        this.player = player;
        this.session = player.getUUID();
    }


    /**
     * Called on initialization
     */
    public abstract void init();


    /**
     * Will add a page instance to this ui instance
     * @param page The page instance to add
     */
    protected void addPage(Page page) {
        page.setParent(this);
        pages.add(page);
    }


    /**
     * Will initialize the ui
     */
    protected void initialize() {
        init();
    }


    /**
     * Will open a page by its index
     * @param index The index of the page
     */
    public void openPage(int index) {
        player.getPlayer().closeInventory();

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);

        this.currentPage = pages.get(index);
        this.currentPage.open();
    }


    /**
     * Unregister Listener handlers
     */
    public void unregisterHandlers() {
        HandlerList.unregisterAll(this);
    }


    /**
     *
     * @return Get the session id
     * (Just a safety so other gui instances does not interfere with another)
     */
    public String getSession() {
        return session;
    }


    /**
     *
     * @return The current open page
     */
    public Page getCurrentPage() {
        return currentPage;
    }


    /**
     *
     * @return List of all the pages in this GUI instance
     */
    public List<Page> getPages() {
        return pages;
    }


    /**
     *
     * @return The player who has opened the gui
     */
    public MPlayer getPlayer() {
        return player;
    }


    /**
     * Event that handles all the gui interactions
     */
    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getWhoClicked().getUniqueId().toString().equalsIgnoreCase(this.session)
                && e.getView().getTitle().equalsIgnoreCase(currentPage.getTitle())) {

            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType().equals(Material.AIR)) return;

            if (currentPage.getFrameSlots().contains(e.getSlot())) {
                currentPage.onFrameClick();
                return;
            }

            for (Component component : currentPage.getComponents()) {
                if (component.getSlot() == e.getSlot()) {

                    if (component instanceof Button) {
                        ((Button) component).getEventRunnable().run();
                        return;
                    }

                }
            }

        }

    }


    /**
     * Unregisters the listener handlers on close
     */
    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        if (e.getPlayer().getUniqueId().toString().equalsIgnoreCase(this.session)) {

            HandlerList.unregisterAll(this);

        }
    }

}
