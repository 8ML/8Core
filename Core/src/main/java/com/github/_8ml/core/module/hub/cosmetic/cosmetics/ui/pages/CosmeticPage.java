package com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import org.bukkit.ChatColor;
import com.github._8ml.core.module.hub.HubModule;
import com.github._8ml.core.ui.page.MultiplePage;

import java.util.ArrayList;
import java.util.List;

public class CosmeticPage extends MultiplePage {

    private final MPlayer player;

    public CosmeticPage(MPlayer player) {
        super("Cosmetics", 54, true);
        this.player = player;
    }

    @Override
    protected List<Component> onOpenMultiple() {

        List<Component> components = new ArrayList<>();

        for (Cosmetic cosmetic : HubModule.instance.cosmeticManager.getCosmetics()) {

            Button entry = new Button(ChatColor.YELLOW + cosmetic.getName(), cosmetic.getItem().getType(), getParent());
            entry.setOnClick(() -> {
                cosmetic.equip(this.player.getPlayer());
                this.player.getPlayer().sendMessage(ChatColor.GREEN + "Equipped " + cosmetic.getName());
            });

            components.add(entry);

        }

        return components;
    }

    @Override
    public void onFrameClick() {

    }
}
