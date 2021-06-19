package com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on June 20 2021
*/

import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.page.Page;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class CosmeticsPage extends Page {

    public CosmeticsPage() {
        super("Cosmetics", 54, true);
    }

    @Override
    protected void onOpen() {

        Button gadgets = new Button(ChatColor.YELLOW + "Gadgets", Material.GOLDEN_HORSE_ARMOR, getParent());
        gadgets.setOnClick(() -> getParent().openPage(1));

        addComponent(gadgets, 22);

    }

    @Override
    public void onFrameClick() {

    }
}
