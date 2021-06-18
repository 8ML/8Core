package com.github._8ml.core.ui.page;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.ui.GUI;
import com.github._8ml.core.ui.component.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is to be extended by other classes
 * The "graphics" of a gui, contains all the components
 * Needs a parent class extending GUI
 */
public abstract class Page {

    private final int size;
    private final boolean frame;

    private final List<Component> components = new ArrayList<>();
    private final List<Integer> frameSlots = new ArrayList<>();

    private Inventory inventory;
    private String title;
    private String frameLabel;
    private String[] frameLore = new String[0];
    private GUI parent;


    /**
     * @param title The title of the page
     * @param size  The size of the page (9, 18, 27, 36, 45, 54)
     * @param frame Set to true if you want a frame
     */
    public Page(String title, int size, boolean frame) {
        this.title = title;
        this.frame = frame;
        this.size = size;
    }


    /**
     * Open the page
     */
    public void open() {
        this.inventory = Bukkit.createInventory(null, size, title);
        initializeFrame();
        onOpen();
        updateComponents();
        this.parent.getPlayer().getPlayer().openInventory(inventory);
    }


    /**
     * Update the components
     */
    private void updateComponents() {
        for (Component component : components) {

            ItemStack i = new ItemStack(component.getMaterial());
            ItemMeta iM;
            if (component.getMeta() == null) iM = i.getItemMeta();
            else iM = component.getMeta();
            assert iM != null;

            if (!component.getLabel().isEmpty()) iM.setDisplayName(component.getLabel());
            if (!component.getLore().isEmpty()) iM.setLore(component.getLore());

            i.setItemMeta(iM);

            this.inventory.setItem(component.getSlot(), i);
        }
    }


    /**
     * Initialize the frame
     */
    private void initializeFrame() {
        if (this.frame) {
            ItemStack frame = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta frameMeta = frame.getItemMeta();
            assert frameMeta != null;
            frameMeta.setDisplayName(this.frameLabel == null ? " " : this.frameLabel);
            frameMeta.setLore(Arrays.asList(this.frameLore));
            frame.setItemMeta(frameMeta);

            for (int i = 0; i < 9; i++) {
                inventory.setItem(i, frame);
                frameSlots.add(i);
            }
            for (int i = this.size - 9; i < this.size; i++) {
                inventory.setItem(i, frame);
                frameSlots.add(i);
            }

            switch (this.size) {
                case 27:
                    inventory.setItem(9, frame);
                    inventory.setItem(17, frame);

                    frameSlots.addAll(Arrays.asList(9, 17));
                    break;
                case 36:
                    inventory.setItem(9, frame);
                    inventory.setItem(17, frame);
                    inventory.setItem(18, frame);
                    inventory.setItem(26, frame);

                    frameSlots.addAll(Arrays.asList(9, 17, 18, 26));
                    break;
                case 45:
                    inventory.setItem(9, frame);
                    inventory.setItem(17, frame);
                    inventory.setItem(18, frame);
                    inventory.setItem(26, frame);
                    inventory.setItem(27, frame);
                    inventory.setItem(35, frame);

                    frameSlots.addAll(Arrays.asList(9, 17, 18, 26, 27, 35));
                    break;
                case 54:
                    inventory.setItem(9, frame);
                    inventory.setItem(17, frame);
                    inventory.setItem(18, frame);
                    inventory.setItem(26, frame);
                    inventory.setItem(27, frame);
                    inventory.setItem(35, frame);
                    inventory.setItem(36, frame);
                    inventory.setItem(44, frame);

                    frameSlots.addAll(Arrays.asList(9, 17, 18, 26, 27, 35, 36, 44));
                    break;
            }

        }
    }


    /**
     * Called when the page is opened
     */
    protected abstract void onOpen();


    /**
     * Called when any slots of the frame is clicked
     */
    public abstract void onFrameClick();


    /**
     * Add a component to the Page instance
     *
     * @param component The component
     * @param slot      The slot
     */
    protected void addComponent(Component component, int slot) {
        components.add(component);
        component.setSlot(slot);
    }


    /**
     * Refresh the gui (Useful if some info changed while the ui is open, will seamlessly refresh)
     */
    protected void refresh() {
        getInventory().clear();
        components.clear();
        initializeFrame();
        onOpen();
        updateComponents();
    }


    /**
     * Set the label of the frame (If frame is set to true in the constructor)
     *
     * @param frameLabel The label as a string
     */
    protected void setFrameLabel(String frameLabel) {
        this.frameLabel = frameLabel;
    }


    /**
     * Set the lore of the frame (If frame is set to true in the constructor)
     *
     * @param frameLore The lore as an array list
     *                  (e.g. new String[]{"This is one line", "this is another"})
     */
    protected void setFrameLore(String[] frameLore) {
        this.frameLore = frameLore;
    }


    /**
     * Set the title of this Page instance
     *
     * @param title Title string
     */
    protected void setTitle(String title) {
        this.title = title;
    }


    /**
     * Get the parent of this page instance
     *
     * @return class extending GUI
     */
    protected GUI getParent() {
        return parent;
    }


    /**
     * Set the parent of this Page instance
     *
     * @param parent class extending GUI
     */
    public void setParent(GUI parent) {
        this.parent = parent;
    }


    /**
     * @return The inventory of this page instance
     */
    public Inventory getInventory() {
        return inventory;
    }


    /**
     * Get the title of this page instance
     *
     * @return The title string
     */
    public String getTitle() {
        return title;
    }


    /**
     * Get all components registered in this page instance
     *
     * @return List of components
     */
    public List<Component> getComponents() {
        return components;
    }


    /**
     * Get a list of all frame slots
     *
     * @return List of frame slots
     */
    public List<Integer> getFrameSlots() {
        return frameSlots;
    }


    /**
     *
     * @return Will return true if the page has a frame (Specified in constructor)
     */
    public boolean isFrame() {
        return this.frame;
    }
}
