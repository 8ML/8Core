package com.github._8ml.core.npc;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.Core;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * This class handles NPC's
 */
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


    /**
     * @param name        Name of the npc
     * @param displayName Display name of the npc
     * @param uuid        The uuid to fetch skin from
     * @param onInteract  The event to fire when a player interacts with it
     */
    public NPC(String name, String displayName, String uuid, InteractionEvent onInteract) {
        this.name = name;
        this.displayName = displayName;
        this.uuid = uuid;
        this.onInteract = onInteract;
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
        npcList.add(this);
    }


    /**
     * This will spawn the npc at the specified location
     *
     * @param location The location to spawn at.
     */
    public void spawn(Location location) {

        CraftServer server = (CraftServer) Bukkit.getServer();

        EntityPlayer playerEntity = new EntityPlayer(
                ((CraftServer) Bukkit.getServer()).getHandle().getServer(),
                ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle(),
                new GameProfile(UUID.fromString(this.uuid), this.name),
                new PlayerInteractManager(((CraftWorld) location.getWorld()).getHandle())
        );

        playerEntity.setPositionRotation(location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());

        playerEntity.setCustomName(new ChatComponentText(this.displayName));
        playerEntity.setCustomNameVisible(true);
        playerEntity.setNoGravity(true);
        playerEntity.ai = false;
        playerEntity.playerConnection = new PlayerConnection(((CraftServer) Bukkit.getServer()).getServer(),
                new NetworkManager(EnumProtocolDirection.SERVERBOUND), playerEntity);

        this.playerEntity = playerEntity;

        sendSpawnPacket();

        this.location = location;

    }

    public void destroy() {

        for (Player player : Core.onlinePlayers) {

            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(this.playerEntity.getId());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

        }
        npcList.remove(this);
        HandlerList.unregisterAll(this);
    }

    private void sendSpawnPacket() {
        for (Player player : Core.onlinePlayers) {
            sendSpawnPacket(player);
        }
    }

    private void sendSpawnPacket(Player player) {
        PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity(playerEntity);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

        PacketPlayOutPlayerInfo playerInfoPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerEntity);
        //playerInfoPacket.new PlayerInfoData(playerEntity.getProfile(), 1,
        //        EnumGamemode.valueOf(playerEntity.playerConnection.getPlayer().getGameMode().toString()),
        //        IChatBaseComponent.ChatSerializer.b(playerEntity.getName()));
        //((CraftPlayer) player).getHandle().playerConnection.sendPacket(playerInfoPacket);
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

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        sendSpawnPacket(e.getPlayer());

    }
}
