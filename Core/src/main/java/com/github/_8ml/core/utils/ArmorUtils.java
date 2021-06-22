package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on June 22 2021
*/

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

public class ArmorUtils {

    public static List<ItemStack> createLeatherArmor(Color color) {

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        LeatherArmorMeta chestPlateMeta = (LeatherArmorMeta) chestPlate.getItemMeta();
        LeatherArmorMeta pantsMeta = (LeatherArmorMeta) pants.getItemMeta();
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();

        Assert.assertNotNull("Helmet Meta cannot be null (createLeatherArmor) (ArmorUtils)", helmetMeta);
        Assert.assertNotNull("ChestPlate Meta cannot be null (createLeatherArmor) (ArmorUtils)", chestPlateMeta);
        Assert.assertNotNull("Pants Meta cannot be null (createLeatherArmor) (ArmorUtils)", pantsMeta);
        Assert.assertNotNull("Boots Meta cannot be null (createLeatherArmor) (ArmorUtils)", bootsMeta);

        helmetMeta.setColor(color);
        chestPlateMeta.setColor(color);
        pantsMeta.setColor(color);
        bootsMeta.setColor(color);

        helmet.setItemMeta(helmetMeta);
        chestPlate.setItemMeta(chestPlateMeta);
        pants.setItemMeta(pantsMeta);
        boots.setItemMeta(bootsMeta);

        return Arrays.asList(boots, pants, chestPlate, helmet);
    }

}
