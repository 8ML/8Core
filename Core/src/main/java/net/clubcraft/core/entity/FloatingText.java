package net.clubcraft.core.entity;
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
