package club.mineplay.core.ui.page;
/*
Created by Sander on 5/4/2021
*/

import club.mineplay.core.ui.GUI;
import club.mineplay.core.ui.component.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Page {

    private final int size;
    private Inventory inventory;
    private final boolean frame;

    private final List<Component> components = new ArrayList<>();
    private final List<Integer> frameSlots = new ArrayList<>();

    private String title;
    private String frameLabel;
    private String[] frameLore = new String[0];

    private GUI parent;

    public Page(String title, int size, boolean frame) {
        this.title = title;
        this.frame = frame;
        this.size = size;
    }

    public void open() {
        this.inventory = Bukkit.createInventory(null, size, title);
        onOpen();

        if (this.frame) {
            ItemStack frame = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta frameMeta = frame.getItemMeta();
            assert frameMeta != null;
            frameMeta.setDisplayName(this.frameLabel);
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

        this.parent.getPlayer().getPlayer().openInventory(inventory);
    }

    public abstract void onOpen();
    public abstract void onFrameClick();


    protected void addComponent(Component component, int slot) {
        components.add(component);
        component.setSlot(slot);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParent(GUI parent) {
        this.parent = parent;
    }

    public void setFrameLabel(String frameLabel) {
        this.frameLabel = frameLabel;
    }

    public void setFrameLore(String[] frameLore) {
        this.frameLore = frameLore;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getTitle() {
        return title;
    }

    public GUI getParent() {
        return parent;
    }

    public List<Component> getComponents() {
        return components;
    }

    public List<Integer> getFrameSlots() {
        return frameSlots;
    }
}
