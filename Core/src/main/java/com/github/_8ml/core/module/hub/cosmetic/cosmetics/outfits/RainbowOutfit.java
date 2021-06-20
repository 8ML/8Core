package com.github._8ml.core.module.hub.cosmetic.cosmetics.outfits;
/*
Created by @8ML (https://github.com/8ML) on June 20 2021
*/

import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.player.hierarchy.Ranks;
import net.minecraft.server.v1_16_R3.Vector3f;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RainbowOutfit extends Cosmetic {

    private final Map<Player, Vector3f> previousColorMap = new HashMap<>();

    public RainbowOutfit() {
        super("Rainbow Outfit", 10000,
                new ItemStack(Material.LEATHER_CHESTPLATE), null, "This is definitely to many colors",
                CosmeticType.OUTFIT, Ranks.DEFAULT);

        LeatherArmorMeta meta = (LeatherArmorMeta) getDisplayMeta();
        meta.setColor(Color.fromRGB(new Random().nextInt(255),
                new Random().nextInt(255),
                new Random().nextInt(255)));
        setDisplayMeta(meta);
    }

    @Override
    protected void onEquip(Player player) {
        previousColorMap.put(player, new Vector3f(0f, 0f, 0f));
        updateArmor(player, Color.fromRGB(0, 0, 0));
    }

    @Override
    protected void onUnEquip(Player player) {
        previousColorMap.remove(player);
        player.getInventory().setArmorContents(null);
    }

    @Override
    protected boolean onUse(UseAction action) {
        return false;
    }

    @Override
    protected void onUpdate() {

        for (Player player : previousColorMap.keySet()) {

            Vector3f previousColor = previousColorMap.get(player);
            Vector3f newColor = new Vector3f(previousColor.getX() + new Random().nextInt(10),
                    previousColor.getY() + new Random().nextInt(10),
                    previousColor.getZ() + new Random().nextInt(10));

            if (newColor.getX() > 255
                    || newColor.getY() > 255
                    || newColor.getZ() > 255) newColor = new Vector3f(0f, 0f, 0f);

            updateArmor(player, Color.fromRGB(
                    (int) newColor.getX(),
                    (int) newColor.getY(),
                    (int) newColor.getZ()));

            previousColorMap.put(player, newColor);
        }

    }

    private void updateArmor(Player player, Color color) {

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        LeatherArmorMeta chestPlateMeta = (LeatherArmorMeta) chestPlate.getItemMeta();
        LeatherArmorMeta pantsMeta = (LeatherArmorMeta) pants.getItemMeta();
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();

        Assert.assertNotNull("Helmet Meta cannot be null (UpdateArmor) (Rainbow Outfit)", helmetMeta);
        Assert.assertNotNull("ChestPlate Meta cannot be null (UpdateArmor) (Rainbow Outfit)", chestPlateMeta);
        Assert.assertNotNull("Pants Meta cannot be null (UpdateArmor) (Rainbow Outfit)", pantsMeta);
        Assert.assertNotNull("Boots Meta cannot be null (UpdateArmor) (Rainbow Outfit)", bootsMeta);

        helmetMeta.setColor(color);
        chestPlateMeta.setColor(color);
        pantsMeta.setColor(color);
        bootsMeta.setColor(color);

        helmet.setItemMeta(helmetMeta);
        chestPlate.setItemMeta(chestPlateMeta);
        pants.setItemMeta(pantsMeta);
        boots.setItemMeta(bootsMeta);

        player.getInventory().setArmorContents(new ItemStack[]{
                boots, pants, chestPlate, helmet
        });

    }
}
