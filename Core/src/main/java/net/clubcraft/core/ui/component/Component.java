package net.clubcraft.core.ui.component;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import net.clubcraft.core.ui.GUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Component {

    private final Material material;
    private final GUI parent;

    private ItemStack stack;
    private ItemMeta meta;
    private int slot;
    private String label;
    private List<String> lore = new ArrayList<>();

    public Component(Material material, GUI parent) {
        this.material = material;
        this.parent = parent;
        this.stack = new ItemStack(material);
        this.meta = stack.getItemMeta();
    }

    public Component(String label, Material material, GUI parent) {
        this.label = label;
        this.material = material;
        this.parent = parent;
        this.stack = new ItemStack(material);
        this.meta = stack.getItemMeta();
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLore(String[] lore) {
        this.lore = Arrays.asList(lore);
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setItemStack(ItemStack stack) {
        this.stack = stack;
    }

    public void setItemMeta(ItemMeta meta) {
        this.meta = meta;
    }

    public Material getMaterial() {
        return material;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getLore() {
        return lore;
    }

    public GUI getParent() {
        return parent;
    }

    public ItemStack getStack() {
        return stack;
    }

    public ItemMeta getMeta() {
        return meta;
    }

    public int getSlot() {
        return slot;
    }

}
