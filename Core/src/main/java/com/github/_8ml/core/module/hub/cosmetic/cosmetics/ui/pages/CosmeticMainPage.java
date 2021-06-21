package com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on June 20 2021
*/

import com.github._8ml.core.module.hub.HubModule;
import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.CosmeticUI;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.page.Page;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class CosmeticMainPage extends Page {

    public CosmeticMainPage() {
        super("Cosmetics", 54, true);
    }

    @Override
    protected void onOpen() {

        Button gadgets = new Button(ChatColor.YELLOW + "Gadgets", Material.GOLDEN_HORSE_ARMOR, getParent());

        gadgets.setLore(new String[]{
                ChatColor.GRAY + "Big collection of toys!"
        });

        gadgets.setOnClick(() -> {
            ((CosmeticUI) getParent()).cosmeticMenuSelected = Cosmetic.CosmeticType.GADGET;
            getParent().openPage(1);
        });

        Button outfits = new Button(ChatColor.YELLOW + "Outfits", Material.LEATHER_CHESTPLATE, getParent());

        ItemMeta outfitsMeta = outfits.getMeta();
        outfitsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        outfits.setItemMeta(outfitsMeta);

        outfits.setLore(new String[]{
                ChatColor.GRAY + "Spice up your looks!"
        });

        outfits.setOnClick(() -> {
            ((CosmeticUI) getParent()).cosmeticMenuSelected = Cosmetic.CosmeticType.OUTFIT;
            getParent().openPage(1);
        });

        Button hats = new Button(ChatColor.YELLOW + "Hats", Material.LEATHER_HELMET, getParent());

        ItemMeta hatsMeta = hats.getMeta();
        hatsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        hats.setItemMeta(hatsMeta);

        hats.setLore(new String[]{
                ChatColor.GRAY + "Put something on your head! Might look a bit stupid",
                ChatColor.GRAY + "but you are cooler than everyone else! Right?"
        });

        hats.setOnClick(() -> {
            ((CosmeticUI) getParent()).cosmeticMenuSelected = Cosmetic.CosmeticType.HAT;
            getParent().openPage(1);
        });

        Button clearCosmetics = new Button(ChatColor.YELLOW + "Clear Cosmetics", Material.BARRIER, getParent());
        clearCosmetics.setLore(new String[]{ChatColor.RED + "Clear all equipped cosmetics"});

        clearCosmetics.setOnClick(() -> {
            HubModule.instance.cosmeticManager.unEquipCosmetic(getParent().getPlayer().getPlayer());
            getParent().getPlayer().getPlayer().closeInventory();
        });


        addComponent(gadgets, 20);
        addComponent(outfits, 24);
        addComponent(hats, 22);
        addComponent(clearCosmetics, 40);

    }

    @Override
    public void onFrameClick() {

    }
}
