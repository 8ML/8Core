package xyz.dev_8.core.module.hub.cosmetic;
/*
Created by @8ML (https://github.com/8ML) on 5/30/2021
*/

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.junit.Assert;
import xyz.dev_8.core.Core;
import xyz.dev_8.core.config.MessageColor;
import xyz.dev_8.core.module.hub.cosmetic.cosmetics.StickCosmetic;

import java.util.*;

public class CosmeticManager implements Listener {

    private final Set<Cosmetic> cosmetics = new HashSet<>();
    private final Map<Player, Cosmetic> cosmeticMap = new HashMap<>();
    private final Map<Player, Map<Cosmetic, Long>> coolDownMap = new HashMap<>();

    public CosmeticManager() {
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
    }

    private void registerCosmetics() {
        registerCosmetic(new StickCosmetic());
    }

    private void registerCosmetic(Cosmetic cosmetic) {
        this.cosmetics.add(cosmetic);
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
        if (when + cosmetic.getCoolDown() > System.currentTimeMillis()) {
            player.sendMessage(MessageColor.COLOR_ERROR + "Cosmetic is on cooldown!");
            return false;
        }

        return true;

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        for (Cosmetic cosmetic : cosmetics) {

            if (!cosmeticMap.containsKey(e.getPlayer())) return;
            if (!cosmeticMap.get(e.getPlayer()).equals(cosmetic)) continue;

            if (cosmetic.isItem()) {

                if (Objects.requireNonNull(e.getItem()).getType().equals(cosmetic.getStack().getType())) {
                    Assert.assertNotNull("Item Meta cannot be null (onInteract - CosmeticManager)", cosmetic.getStackMeta());
                    if (Objects.requireNonNull(e.getItem().getItemMeta())
                            .getDisplayName().equals(cosmetic.getStackMeta().getDisplayName())) {

                        if (!checkCoolDown(e.getPlayer(), cosmetic)) return;

                        switch (e.getAction()) {
                            case LEFT_CLICK_AIR:
                                cosmetic.onUse(new Cosmetic.UseAction(e.getPlayer(), Cosmetic.UseActionType.LEFT_CLICK));
                                break;
                            case LEFT_CLICK_BLOCK:
                                cosmetic.onUse(new Cosmetic.UseAction(e.getPlayer(), Cosmetic.UseActionType.LEFT_CLICK_BLOCK,
                                        Objects.requireNonNull(e.getClickedBlock())));
                                break;
                            case RIGHT_CLICK_AIR:
                                cosmetic.onUse(new Cosmetic.UseAction(e.getPlayer(), Cosmetic.UseActionType.RIGHT_CLICK));
                                break;
                            case RIGHT_CLICK_BLOCK:
                                cosmetic.onUse(new Cosmetic.UseAction(e.getPlayer(), Cosmetic.UseActionType.RIGHT_CLICK_BLOCK,
                                        Objects.requireNonNull(e.getClickedBlock())));
                                break;
                        }

                        startCoolDown(e.getPlayer(), cosmetic);

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

}
