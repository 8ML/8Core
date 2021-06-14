package xyz.dev_8.core.ui;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import xyz.dev_8.core.Core;
import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.ui.component.Component;
import xyz.dev_8.core.ui.component.components.Button;
import xyz.dev_8.core.ui.page.Page;
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
