package com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.purchase.Purchase;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import org.bukkit.ChatColor;
import com.github._8ml.core.module.hub.HubModule;
import com.github._8ml.core.ui.page.MultiplePage;

import java.util.ArrayList;
import java.util.List;

public class GadgetsPage extends MultiplePage {

    private final MPlayer player;

    public GadgetsPage(MPlayer player) {
        super("Gadgets", 54, true);
        this.player = player;
    }

    @Override
    protected List<Component> onOpenMultiple() {

        List<Component> components = new ArrayList<>();

        for (Cosmetic cosmetic : HubModule.instance.cosmeticManager.getCosmetics()) {

            boolean owning = Purchase.hasPurchased(player, "cosmetic::"
                    + cosmetic.getType().name().toLowerCase() + "::"
                    + cosmetic.getName());

            Button entry = new Button(ChatColor.YELLOW + cosmetic.getName(), cosmetic.getItem().getType(), getParent());

            entry.setLore(new String[]{
                    cosmetic.getDescription(),
                    "",
                    owning ? ChatColor.GREEN + "Owned"
                            : ChatColor.GRAY + "Price: " + ChatColor.GOLD + cosmetic.getCoins()
            });

            entry.setOnClick(() -> {

                if (!owning) {
                    new Purchase(cosmetic.getName(), cosmetic.getName(), cosmetic.getCoins(), player);
                    player.getPlayer().closeInventory();
                    return;
                }

                cosmetic.equip(this.player.getPlayer());
                this.player.getPlayer().sendMessage(ChatColor.GREEN + "Equipped " + cosmetic.getName());
                this.player.getPlayer().closeInventory();
            });

            components.add(entry);

        }

        return components;
    }

    @Override
    public void onFrameClick() {

    }
}
