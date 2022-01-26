/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
import com.github._8ml.core.utils.StringUtils;
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
        super("", true);
        this.player = player;
    }

    @Override
    protected List<Component> onOpenMultiple() {

        List<Component> components = new ArrayList<>();
        CosmeticManager manager = HubModule.instance.cosmeticManager;
        Map<Player, List<Cosmetic>> cosmeticMap = manager.getCosmeticMap();

        Cosmetic.CosmeticType type = CosmeticUI.cosmeticMenuSelected;

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
                meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
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
    protected void onPreOpen() {

        Cosmetic.CosmeticType type = CosmeticUI.cosmeticMenuSelected;

        setTitle(StringUtils.formatCapitalization(type.name() + "s"));

    }

    @Override
    public void onFrameClick() {

    }
}
