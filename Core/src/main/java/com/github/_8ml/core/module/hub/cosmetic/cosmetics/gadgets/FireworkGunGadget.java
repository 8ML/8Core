/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.hub.cosmetic.cosmetics.gadgets;
/*
Created by @8ML (https://github.com/8ML) on July 17 2021
*/

import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FireworkGunGadget extends Cosmetic {

    private final static List<Arrow> entities = new ArrayList<>();

    public FireworkGunGadget() {
        super("Firework Gun", 2000, new ItemStack(Material.FIREWORK_ROCKET), new ItemStack(Material.FIREWORK_ROCKET),
                "Boom", CosmeticType.GADGET, Ranks.DEFAULT);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnEquip(Player player) {

    }

    @Override
    protected boolean onUse(UseAction action) {

        if (action.getType().equals(UseActionType.RIGHT_CLICK)) {

            Arrow arrow = action.getPlayer().launchProjectile(Arrow.class,
                    action.getPlayer().getEyeLocation().toVector().multiply(3f));

            arrow.setColor(Color.fromRGB(new Random().nextInt(255),
                    new Random().nextInt(255),
                    new Random().nextInt(255)));
            entities.add(arrow);

            return true;
        }

        return false;
    }

    @Override
    protected void onUpdate() {

    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (!e.getEntityType().equals(EntityType.ARROW)) return;
        if (!entities.contains((Arrow) e.getEntity())) return;
        entities.remove((Arrow) e.getEntity());
        e.getEntity().remove();

        Random random = new Random();

        boolean flicker = random.nextBoolean();
        boolean fade = random.nextBoolean();
        boolean trail = random.nextBoolean();
        FireworkEffect.Type[] typeValues = FireworkEffect.Type.values();
        FireworkEffect.Type type = typeValues[random.nextInt(typeValues.length - 1)];

        List<Color> mainColors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mainColors.add(Color.fromRGB(random.nextInt(255),
                    random.nextInt(255), random.nextInt(255)));
        }
        List<Color> fadeColors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            fadeColors.add(Color.fromRGB(random.nextInt(255),
                    random.nextInt(255), random.nextInt(255)));
        }


        FireworkEffect fireworkEffect = FireworkEffect.builder()
                .flicker(flicker)
                .trail(trail)
                .with(type)
                .withColor(mainColors)
                .withFade(fade ? Collections.emptyList() : fadeColors)
                .build();

        Firework firework = (Firework) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.addEffect(fireworkEffect);
        firework.setFireworkMeta(meta);

        firework.detonate();
    }


}
