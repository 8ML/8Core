package com.github._8ml.core.ui.component;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.ui.GUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * This class is to be extended by other classes.
 * The main class of all components, it can be extended to add further functions
 * Note that this is abstract so it cannot be directly added to a page
 */
public abstract class Component {

    private final Material material;
    private final GUI parent;

    private ItemStack stack;
    private ItemMeta meta;
    private int slot;
    private String label;
    private List<String> lore = new ArrayList<>();


    /**
     * @param material The material
     * @param parent   The parent class extending GUI
     */
    public Component(Material material, GUI parent) {
        this.material = material;
        this.parent = parent;
        this.stack = new ItemStack(material);
        this.meta = stack.getItemMeta();
    }


    /**
     * @param label    The label
     * @param material The material
     * @param parent   The parent class extending GUI
     */
    public Component(String label, Material material, GUI parent) {
        this.label = label;
        this.material = material;
        this.parent = parent;
        this.stack = new ItemStack(material);
        this.meta = stack.getItemMeta();
    }


    /**
     * Set the label of this component instance
     *
     * @param label The label as a string
     */
    public void setLabel(String label) {
        this.label = label;
    }


    /**
     * Set the lore of this component instance
     *
     * @param lore The lore as an array
     *             (e.g. new String[]{"This is one line", "This is another"})
     */
    public void setLore(String[] lore) {
        this.lore = new LinkedList<>(Arrays.asList(lore));
        List<String> toRemove = new ArrayList<>();
        for (String l : this.lore) {

            if (l.equals("")) {
                toRemove.add(l);
            }

        }
        for (String remove : toRemove) {
            this.lore.remove(remove);
        }
    }


    /**
     * Set the slot of this component instance
     *
     * @param slot The slot
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }


    /**
     * Set the stack of this component instance
     *
     * @param stack The item stack
     */
    public void setItemStack(ItemStack stack) {
        this.stack = stack;
    }


    /**
     * Set the item meta of this component instance
     *
     * @param meta The item meta
     */
    public void setItemMeta(ItemMeta meta) {
        this.meta = meta;
    }


    /**
     * @return The material of this component
     */
    public Material getMaterial() {
        return material;
    }


    /**
     * @return The label of this component
     */
    public String getLabel() {
        return label;
    }


    /**
     * @return The lore as a list (each entry is one line)
     */
    public List<String> getLore() {
        return lore;
    }


    /**
     * @return The parent class extending GUI
     */
    public GUI getParent() {
        return parent;
    }


    /**
     * @return The item stack of this component
     */
    public ItemStack getStack() {
        return stack;
    }


    /**
     * @return The item meta of this component
     */
    public ItemMeta getMeta() {
        return meta;
    }


    /**
     * @return The slot of this component
     */
    public int getSlot() {
        return slot;
    }

}
