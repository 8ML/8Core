package com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.module.hub.cosmetic.CosmeticManager;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.CosmeticUI;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.purchase.Purchase;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import org.bukkit.ChatColor;
import com.github._8ml.core.module.hub.HubModule;
import com.github._8ml.core.ui.page.MultiplePage;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CosmeticPage extends MultiplePage {

    private final MPlayer player;

    public CosmeticPage(MPlayer player) {
        super("Gadgets", 54, true);
        this.player = player;
    }

    @Override
    protected List<Component> onOpenMultiple() {

        List<Component> components = new ArrayList<>();
        CosmeticManager manager = HubModule.instance.cosmeticManager;
        Map<Player, List<Cosmetic>> cosmeticMap = manager.getCosmeticMap();

        Cosmetic.CosmeticType type = ((CosmeticUI) getParent()).cosmeticMenuSelected;

        for (Cosmetic cosmetic : manager.getCosmetics()) {

            if (!cosmetic.getType().equals(type)) continue;

            String purchaseKey = "cosmetic::" + type.name().toLowerCase() + "::"
                    + cosmetic.getName().toLowerCase();

            boolean owning = Purchase.hasPurchased(player, purchaseKey);
            boolean equipped = owning
                    && (cosmeticMap.containsKey(player.getPlayer())
                    && cosmeticMap.get(player.getPlayer()).contains(cosmetic));

            Button entry = new Button(MessageColor.COLOR_HIGHLIGHT + cosmetic.getName(), cosmetic.getDisplayStack().getType(), getParent());

            entry.setLore(new String[]{
                    cosmetic.getDescription(),
                    "",
                    equipped ? MessageColor.COLOR_SUCCESS + "Equipped"
                            : owning ? MessageColor.COLOR_SUCCESS + "Owned"
                            : MessageColor.COLOR_MAIN + "Price: " + ChatColor.GOLD + cosmetic.getCoins()
            });

            ItemMeta meta = entry.getMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            if (equipped) {
                meta.addEnchant(Enchantment.DAMAGE_ALL, 1,  true);
            }

            entry.setItemMeta(meta);

            entry.setOnClick(() -> {

                if (equipped) {
                    player.getPlayer().closeInventory();
                    manager.unEquipCosmetic(player.getPlayer(), cosmetic);
                    return;
                }

                if (!owning) {
                    player.getPlayer().closeInventory();
                    new Purchase(cosmetic.getName(), purchaseKey, cosmetic.getCoins(), player);
                    return;
                }

                manager.equipCosmetic(this.player.getPlayer(), cosmetic);
                this.player.getPlayer().sendMessage(MessageColor.COLOR_SUCCESS + "Equipped " + cosmetic.getName());
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
