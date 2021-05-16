package club.mineplay.core.ui;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.ui.component.Component;
import club.mineplay.core.ui.component.components.Button;
import club.mineplay.core.ui.page.Page;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class GUI implements Listener {

    private final String session;

    private final MPlayer player;

    private final List<Page> pages = new ArrayList<>();

    private Page currentPage;

    public GUI(MPlayer player) {

        this.player = player;
        this.session = player.getUUID();
    }

    public abstract void init();

    protected void addPage(Page page) {
        page.setParent(this);
        pages.add(page);
    }

    public void openPage(int index) {
        player.getPlayer().closeInventory();

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);

        this.currentPage = pages.get(index);
        this.currentPage.open();
    }

    protected void initialize() {
        init();
    }

    public void unregisterHandlers() {
        HandlerList.unregisterAll(this);
    }


    public String getSession() {
        return session;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public List<Page> getPages() {
        return pages;
    }

    public MPlayer getPlayer() {
        return player;
    }

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

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        if (e.getPlayer().getUniqueId().toString().equalsIgnoreCase(this.session)) {

            HandlerList.unregisterAll(this);

        }
    }

}
