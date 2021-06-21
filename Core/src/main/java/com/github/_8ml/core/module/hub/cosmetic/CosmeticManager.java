package com.github._8ml.core.module.hub.cosmetic;
/*
Created by @8ML (https://github.com/8ML) on 5/30/2021
*/

import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.utils.DeveloperMode;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;

import java.util.*;

public class CosmeticManager implements Listener {

    private final Set<Cosmetic> cosmetics = new HashSet<>();
    private final Map<Player, List<Cosmetic>> cosmeticMap = new HashMap<>();
    private final Map<Player, Map<Cosmetic, Long>> coolDownMap = new HashMap<>();

    public CosmeticManager() {
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
        registerCosmeticsFromEnum();
    }

    public void registerCosmetic(Cosmetic cosmetic) {
        this.cosmetics.add(cosmetic);
    }

    public void equipCosmetic(Player player, Cosmetic cosmetic) {

        List<Cosmetic> cosmeticsToUnEquip = new ArrayList<>();

        if (cosmeticMap.containsKey(player) && cosmeticMap.get(player) != null) {

            if (!cosmeticMap.get(player).isEmpty()) {


                for (Cosmetic equippedCosmetic : cosmeticMap.get(player)) {

                    if (!equippedCosmetic.getType().equals(cosmetic.getType())) continue;

                    if (!cosmetic.getType().equals(Cosmetic.CosmeticType.HAT)
                            && !cosmetic.getType().equals(Cosmetic.CosmeticType.OUTFIT)) continue;

                    if (!equippedCosmetic.getType().equals(Cosmetic.CosmeticType.OUTFIT)
                            && !equippedCosmetic.getType().equals(Cosmetic.CosmeticType.HAT)) continue;

                    cosmeticsToUnEquip.add(equippedCosmetic);

                }

            }

        }

        for (Cosmetic toUnEquip : cosmeticsToUnEquip) {
            unEquipCosmetic(player, toUnEquip);
        }

        if (cosmeticMap.containsKey(player)) {
            if (cosmeticMap.get(player).isEmpty()) {
                cosmeticMap.put(player, new ArrayList<>());
            }
        }
        List<Cosmetic> cosmetics = cosmeticMap.get(player);
        cosmetics.add(cosmetic);
        cosmeticMap.put(player, cosmetics);
        cosmetic.equip(player);
    }

    public void unEquipCosmetic(Player player, Cosmetic cosmetic) {
        player.sendMessage(ChatColor.RED + "Unequipped " + cosmetic.getName());
        if (cosmeticMap.containsKey(player)) {
            if (cosmeticMap.get(player) != null) {
                List<Cosmetic> cosmetics = cosmeticMap.get(player);
                cosmetics.remove(cosmetic);
                cosmeticMap.put(player, cosmetics);
                cosmetic.unEquip(player);
                return;
            }
        }
        cosmeticMap.put(player, new ArrayList<>());
        cosmetic.unEquip(player);
    }

    public void unEquipCosmetic(Player player) {
        player.sendMessage(ChatColor.RED + "Unequipped all cosmetics");
        if (!cosmeticMap.containsKey(player) || cosmeticMap.get(player) == null) return;
        for (Cosmetic cosmetic : cosmeticMap.get(player)) {
            cosmetic.unEquip(player);
        }
        cosmeticMap.put(player, new ArrayList<>());
    }

    public void addEntry(Player player) {
        cosmeticMap.put(player, new ArrayList<>());
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

    public Map<Player, List<Cosmetic>> getCosmeticMap() {
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
            if (!cosmeticMap.get(e.getPlayer()).contains(cosmetic)) continue;


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
                if (cosmeticMap.get(e.getPlayer()) == null) return;
                if (!cosmeticMap.get(e.getPlayer()).contains(cosmetic)) continue;

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

    @EventHandler(priority = EventPriority.LOW)
    public void onLeftClickPlayer(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player) {

                Player player = (Player) e.getDamager();
                Player target = (Player) e.getEntity();

                for (Cosmetic cosmetic : cosmetics) {

                    if (!cosmeticMap.containsKey(player)) return;
                    if (cosmeticMap.get(player) == null) return;
                    if (!cosmeticMap.get(player).contains(cosmetic)) continue;

                    if (cosmetic.isItem()) {

                        if (player.getInventory().getItemInMainHand().getType().equals(cosmetic.getStack().getType())) {
                            Assert.assertNotNull("Item Meta cannot be null (onLeftClickPlayer - CosmeticManager)", cosmetic.getStackMeta());
                            if (Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta())
                                    .getDisplayName().equals(cosmetic.getStackMeta().getDisplayName())) {

                                if (!checkCoolDown(player, cosmetic)) return;

                                e.setCancelled(false);
                                e.setDamage(0.0D);

                                DeveloperMode.log("LeftClick Fired");

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

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        unEquipCosmetic(e.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        addEntry(e.getPlayer());
    }

}
