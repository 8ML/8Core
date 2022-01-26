/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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


    /**
     *
     * @param color The color of the leather armor
     * @return A full set of leather armor colored in the specified color.
     */
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
