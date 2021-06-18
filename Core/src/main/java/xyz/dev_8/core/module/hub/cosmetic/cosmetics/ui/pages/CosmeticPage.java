package xyz.dev_8.core.module.hub.cosmetic.cosmetics.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import org.bukkit.ChatColor;
import xyz.dev_8.core.module.hub.HubModule;
import xyz.dev_8.core.module.hub.cosmetic.Cosmetic;
import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.ui.component.Component;
import xyz.dev_8.core.ui.component.components.Button;
import xyz.dev_8.core.ui.page.MultiplePage;
import xyz.dev_8.core.ui.page.Page;

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
