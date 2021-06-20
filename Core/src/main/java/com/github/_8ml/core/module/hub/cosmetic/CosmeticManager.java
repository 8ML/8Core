package com.github._8ml.core.module.hub.cosmetic;
/*
Created by @8ML (https://github.com/8ML) on 5/30/2021
*/

import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.utils.DeveloperMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;

import java.util.*;

public class CosmeticManager implements Listener {

    private final Set<Cosmetic> cosmetics = new HashSet<>();
    private final Map<Player, Cosmetic> cosmeticMap = new HashMap<>();
    private final Map<Player, Map<Cosmetic, Long>> coolDownMap = new HashMap<>();

    public CosmeticManager() {
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
        registerCosmeticsFromEnum();
    }

    public void registerCosmetic(Cosmetic cosmetic) {
        this.cosmetics.add(cosmetic);
    }

    public void equipCosmetic(Player player, Cosmetic cosmetic) {
        cosmeticMap.put(player, cosmetic);
        cosmetic.equip(player);
    }

    public void unEquipCosmetic(Player player, Cosmetic cosmetic) {
        cosmeticMap.put(player, null);
        cosmetic.unEquip(player);
    }

    public void addEntry(Player player) {
        cosmeticMap.put(player, null);
    }

    private void registerCosmeticsFromEnum() {

        for (Cosmetics cosmeticsEnumEntry : Cosmetics.values()) {
            cosmeticsEnumEntry.register(this);
        }

    }

    private void startCoolDown(Player player, Cosmetic cosmetic) {

        if (!coolDownMap.containsKey(player)) {
            coolDownMap.put(player, new HashMap<>());
        }

        Map<Cosmetic, Long> current = coolDownMap.get(player);
        current.put(cosmetic, System.currentTimeMillis());
        coolDownMap.put(player, current);
    }

    private boolean checkCoolDown(Player player, Cosmetic cosmetic) {
        if (!coolDownMap.containsKey(player)) return true;
        if (!coolDownMap.get(player).containsKey(cosmetic)) return true;

        long when = coolDownMap.get(player).get(cosmetic);
        return when + cosmetic.getCoolDown() <= System.currentTimeMillis();

    }

    public Map<Player, Cosmetic> getCosmeticMap() {
        return this.cosmeticMap;
    }

    public Set<Cosmetic> getCosmetics() {
        return this.cosmetics;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        for (Cosmetic cosmetic : cosmetics) {

            if (!cosmeticMap.containsKey(e.getPlayer())) return;
            if (cosmeticMap.get(e.getPlayer()) == null) return;
            if (!cosmeticMap.get(e.getPlayer()).equals(cosmetic)) continue;


            if (cosmetic.isItem()) {

                ItemStack itemMainHand = e.getPlayer().getInventory().getItemInMainHand();

                if (Objects.requireNonNull(itemMainHand).getType().equals(cosmetic.getStack().getType())) {

                    Assert.assertNotNull("Item Meta cannot be null (onInteract - CosmeticManager)", cosmetic.getStackMeta());
                    if (Objects.requireNonNull(itemMainHand.getItemMeta())
                            .getDisplayName().equals(cosmetic.getStackMeta().getDisplayName())) {

                        if (!checkCoolDown(e.getPlayer(), cosmetic)) return;

                        boolean result = false;

                        switch (e.getAction()) {
                            case LEFT_CLICK_AIR:
                                result = cosmetic.onUse(new Cosmetic.UseAction(e.getPlayer(), Cosmetic.UseActionType.LEFT_CLICK));
                                break;
                            case LEFT_CLICK_BLOCK:
                                result = cosmetic.onUse(new Cosmetic.UseAction(e.getPlayer(), Cosmetic.UseActionType.LEFT_CLICK_BLOCK,
                                        Objects.requireNonNull(e.getClickedBlock())));
                                break;
                            case RIGHT_CLICK_AIR:
                                result = cosmetic.onUse(new Cosmetic.UseAction(e.getPlayer(), Cosmetic.UseActionType.RIGHT_CLICK));
                                break;
                            case RIGHT_CLICK_BLOCK:
                                result = cosmetic.onUse(new Cosmetic.UseAction(e.getPlayer(), Cosmetic.UseActionType.RIGHT_CLICK_BLOCK,
                                        Objects.requireNonNull(e.getClickedBlock())));
                                break;
                        }

                        if (result) {
                            startCoolDown(e.getPlayer(), cosmetic);
                        }

                    }

                }

            }
        }

    }

    @EventHandler
    public void onRightClickPlayer(PlayerInteractAtEntityEvent e) {

        if (e.getRightClicked() instanceof Player) {

            for (Cosmetic cosmetic : cosmetics) {

                if (!cosmeticMap.containsKey(e.getPlayer())) return;
                if (!cosmeticMap.get(e.getPlayer()).equals(cosmetic)) continue;

                if (cosmetic.isItem()) {

                    if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(cosmetic.getStack().getType())) {
                        Assert.assertNotNull("Item Meta cannot be null (onRightClickPlayer - CosmeticManager)", cosmetic.getStackMeta());
                        if (Objects.requireNonNull(e.getPlayer().getInventory().getItemInMainHand().getItemMeta())
                                .getDisplayName().equals(cosmetic.getStackMeta().getDisplayName())) {


                            if (!checkCoolDown(e.getPlayer(), cosmetic)) return;

                            cosmetic.onUse(new Cosmetic.UseAction(e.getPlayer(), Cosmetic.UseActionType.RIGHT_CLICK_PLAYER, (Player) e.getRightClicked()));
                            startCoolDown(e.getPlayer(), cosmetic);

                        }

                    }

                }

            }

        }

    }

    @EventHandler
    public void onLeftClickPlayer(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player) {

                Player player = (Player) e.getDamager();
                Player target = (Player) e.getEntity();

                for (Cosmetic cosmetic : cosmetics) {

                    if (!cosmeticMap.containsKey(player)) return;
                    if (!cosmeticMap.get(player).equals(cosmetic)) continue;

                    if (cosmetic.isItem()) {

                        if (player.getInventory().getItemInMainHand().getType().equals(cosmetic.getStack().getType())) {
                            Assert.assertNotNull("Item Meta cannot be null (onLeftClickPlayer - CosmeticManager)", cosmetic.getStackMeta());
                            if (Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta())
                                    .getDisplayName().equals(cosmetic.getStackMeta().getDisplayName())) {

                                if (!checkCoolDown(player, cosmetic)) return;

                                cosmetic.onUse(new Cosmetic.UseAction(player, Cosmetic.UseActionType.LEFT_CLICK_PLAYER, target));
                                startCoolDown(player, cosmetic);

                            }

                        }

                    }
                }

            }
        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        for (Cosmetic cosmetic : cosmetics) {
            cosmetic.onUpdate();
        }
    }

}
