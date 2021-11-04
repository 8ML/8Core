package com.github._8ml.core.module.game.games.platform;
/*
Created by @8ML (https://github.com/8ML) on August 07 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.module.game.exceptions.GameInitializationException;
import com.github._8ml.core.module.game.games.platform.achievements.PlatformSurvivorAchievement;
import com.github._8ml.core.module.game.games.platform.kits.AcrobatKit;
import com.github._8ml.core.module.game.games.platform.kits.DefaultKit;
import com.github._8ml.core.module.game.manager.Game;
import com.github._8ml.core.module.game.manager.kit.Kit;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import com.github._8ml.core.module.game.sfx.SoundEffect;
import com.github._8ml.core.player.achievement.Achievement;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.*;

public class PlatformGame extends Game {

    private List<Block> floor = new ArrayList<>();
    private Map<Location, BlockState> originalFloor = new HashMap<>();

    public PlatformGame() throws GameInitializationException {
        super("Platform",
                new Kit[]{
                        new DefaultKit(),
                        new AcrobatKit()
                }, 2, 10, 100, 0);

        this.gameObjective = "Survive the longest";
        this.canPlaceBlocks = false;
        this.canBreakBlocks = false;
        this.hunger = false;
        this.allowDayNightCycle = false;
        this.pvp = false;

        sfx();
    }

    private void sfx() {
        this.sfx.put("sfx-death", new SoundEffect(Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1f, 1f));
    }

    private void teleport(GamePlayer player) {

        List<GamePlayer> players = getPlayers();
        Location loc = getMap().getLocation("spawn-" + (players.indexOf(player) -1));

        player.getPlayer().teleport(loc);
    }

    @Override
    protected void onStart() {
        updateFloor();

        for (Block block : floor) {

            originalFloor.put(block.getLocation(), block.getState());

        }
    }

    private int counter = 0;
    @Override
    protected void onUpdate() {

        counter++;
        if (counter > 5) {
            counter = 0;

            decayFloor();
        }

    }

    private void updateFloor() {
        floor.clear();
        for (Chunk chunk : getPlayers().get(0).getPlayer().getWorld().getLoadedChunks()) {

            int x = chunk.getX() << 4;
            int z = chunk.getZ() << 4;

            World world = chunk.getWorld();

            Material[] materials = new Material[]{Material.GREEN_CONCRETE, Material.ORANGE_CONCRETE, Material.RED_CONCRETE};

            for(int xx = x; xx < x + 16; xx++) {
                for (int zz = z; zz < z + 16; zz++) {
                    for (int yy = 0; yy < 256; yy++) {
                        Block block = world.getBlockAt(xx, yy, zz);
                        if (Arrays.asList(materials).contains(block.getType())) {

                            floor.add(block);

                        }
                    }
                }
            }

        }
    }

    private void decayFloor() {
        int rnd = new Random().nextInt(floor.size());
        Block block = floor.get(rnd);

        switch (block.getType()) {
            case GREEN_CONCRETE:
                block.setType(Material.ORANGE_CONCRETE);
                break;
            case ORANGE_CONCRETE:
                block.setType(Material.RED_CONCRETE);
                break;
            case RED_CONCRETE:
                block.setType(Material.RED_CONCRETE_POWDER);
                break;
        }

        updateFloor();
    }

    @Override
    protected void teleport() {
        for (GamePlayer player : getPlayers()) {
            teleport(player);
        }
    }

    @Override
    protected void initScoreboard() {
        getScoreBoard().setScoreboard(
                new String[]{ChatColor.GREEN + "" + ChatColor.BOLD + "SLAP"},
                new String[]{
                        ChatColor.WHITE + ServerConfig.SERVER_DOMAIN.toString(),
                        ChatColor.WHITE + "",
                        ChatColor.WHITE + "Players Left: ",
                        ChatColor.YELLOW + "" + ChatColor.BOLD,
                },
                new String[]{
                        "",
                        "",
                        "%playersLeft%",
                        "",
                }
        );
    }

    @Override
    protected void updateScoreboard() {

    }

    @Override
    protected void updateBoardPlaceholders() {
        getScoreBoard().addCustomPlaceholder("%playersLeft%",
                String.valueOf(this.getPlayers().size()));
    }

    @Override
    protected void onEnd(Object winner) {
        for (Location key : originalFloor.keySet()) {
            getPlayers().get(0).getPlayer().getWorld()
                    .getBlockAt(key).setBlockData(originalFloor.get(key).getBlockData());
        }
        originalFloor.clear();
        floor.clear();
        for (Player player : Core.onlinePlayers) {
            player.setAllowFlight(false);
        }

        Objects.requireNonNull(Achievement.getAchievement(PlatformSurvivorAchievement.class))
                .complete(((GamePlayer) winner).getMPlayer());

    }

    @Override
    protected void onKill(GamePlayer killed, GamePlayer killer) {

    }

    @Override
    protected void onDeath(GamePlayer player, boolean killedByPlayer) {

    }

    @Override
    protected void onJoin(GamePlayer player) {

    }

    @Override
    protected void onLeave(GamePlayer player) {

    }

    @EventHandler
    public void onFlightToggle(PlayerToggleFlightEvent e) {

        if (!this.getState().equals(GameState.IN_GAME)) return;

        Player player = e.getPlayer();
        player.setVelocity(player.getLocation().getDirection().add(new Vector(0, 2, 0)).multiply(2));

        player.setFlying(false);

    }
}
