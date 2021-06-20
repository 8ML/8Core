package com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.module.hub.cosmetic.CosmeticManager;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.CosmeticGUI;
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
        Map<Player, Cosmetic> cosmeticMap = manager.getCosmeticMap();

        Cosmetic.CosmeticType type = ((CosmeticGUI) getParent()).cosmeticMenuSelected;

        for (Cosmetic cosmetic : manager.getCosmetics()) {

            if (!cosmetic.getType().equals(type)) continue;

            String purchaseKey = "cosmetic::" + type.name().toLowerCase() + "::"
                    + cosmetic.getName().toLowerCase();

            boolean owning = Purchase.hasPurchased(player, purchaseKey);
            boolean equipped = owning
                    && (cosmeticMap.containsKey(player.getPlayer())
                    && cosmeticMap.get(player.getPlayer()) == cosmetic);

            Button entry = new Button(ChatColor.YELLOW + cosmetic.getName(), cosmetic.getDisplayStack().getType(), getParent());

            entry.setLore(new String[]{
                    cosmetic.getDescription(),
                    "",
                    equipped ? ChatColor.GREEN + "Equipped"
                            : owning ? ChatColor.GREEN + "Owned"
                            : ChatColor.GRAY + "Price: " + ChatColor.GOLD + cosmetic.getCoins()
            });

            if (equipped) {
                ItemMeta meta = entry.getMeta();
                meta.addEnchant(Enchantment.DAMAGE_ALL, 1,  true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                entry.setItemMeta(meta);
            }

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
