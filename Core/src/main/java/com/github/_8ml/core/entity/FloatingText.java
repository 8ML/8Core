/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.entity;
/*
Created by @8ML (https://github.com/8ML) on 5/11/2021
*/

import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

import java.util.Objects;

public class FloatingText extends EntityArmorStand {

    private final Location location;

    public FloatingText(Location location, String str) {
        super(((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle(),
                location.getX(),
                location.getY(),
                location.getZ());

        this.location = location;

        this.setCustomName(new ChatComponentText(str));
        this.setCustomNameVisible(true);
        this.setMarker(true);
        this.setInvisible(true);
        this.setNoGravity(true);
    }

    public void create() {
        ((CraftWorld) Objects.requireNonNull(location.getWorld()))
                .getHandle().addEntity(this);
    }

    public void destroy() {
        ((CraftWorld) Objects.requireNonNull(location.getWorld()))
                .getHandle().removeEntity(this);
    }
}
