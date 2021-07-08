package com.github._8ml.core.npc;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.Core;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class NPC implements Listener {

    public interface InteractionEvent {

        void run(Player player);

    }

    private static final List<NPC> npcList = new ArrayList<>();

    private final String name;
    private final String displayName;
    private final String uuid;
    private final InteractionEvent onInteract;

    private EntityPlayer playerEntity;
    private Location location;

    public NPC(String name, String displayName, String uuid, InteractionEvent onInteract) {
        this.name = name;
        this.displayName = displayName;
        this.uuid = uuid;
        this.onInteract = onInteract;
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
        npcList.add(this);
    }

    public void spawn(Location location) {

        CraftServer server = (CraftServer) Bukkit.getServer();

        EntityPlayer playerEntity = new EntityPlayer(
                server.getHandle().getServer(),
                (WorldServer) server.getServer().worldServer,
                new GameProfile(UUID.fromString(this.uuid), this.name),
                new PlayerInteractManager((WorldServer) server.getServer().worldServer)
        );

        playerEntity.setCustomName(new ChatComponentText(this.displayName));
        playerEntity.setCustomNameVisible(true);
        playerEntity.setNoGravity(true);
        playerEntity.ai = false;

        ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle().addEntity(playerEntity);

        this.playerEntity = playerEntity;
        this.location = location;

    }

    public void destroy() {

        ((CraftWorld) Objects.requireNonNull(this.location.getWorld())).getHandle().removeEntity(this.playerEntity);
        npcList.remove(this);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUuid() {
        return uuid;
    }

    public EntityPlayer getPlayerEntity() {
        return playerEntity;
    }

    public Location getLocation() {
        return location;
    }

    public InteractionEvent getOnInteract() {
        return onInteract;
    }

    @EventHandler
    public void interactAtNPC(EntityInteractEvent e) {
        if (e.getEntity().getEntityId() == this.playerEntity.getId()) {

            this.onInteract.run((Player) e.getEntity());

        }
    }
}
