package com.github._8ml.core.module.game.manager;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.events.event.ServerShutDownEvent;
import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.module.game.exceptions.GameInitializationException;
import com.github._8ml.core.module.game.manager.kit.Kit;
import com.github._8ml.core.module.game.manager.map.Map;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import com.github._8ml.core.module.game.manager.team.Team;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.currency.Coin;
import com.github._8ml.core.purchase.Purchase;
import com.github._8ml.core.ui.PromptGUI;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.utils.InteractItem;
import com.github._8ml.core.utils.ScoreBoard;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public abstract class Game implements Listener {

    public enum GameState {
        WAITING, IN_GAME, ENDING, NONE
    }

    private final String name;
    private final Team[] teams;
    private final Kit[] kits;
    private final int winningCoins;
    private final int killCoins;
    private final int maxPlayers;
    private final int minPlayers;

    private final List<GamePlayer> players = new LinkedList<>();
    private final java.util.Map<Player, GamePlayer> gamePlayerMap = new HashMap<>();

    private Map map;
    private GameState state;
    private ScoreBoard scoreBoard;
    private boolean isStarting;
    private int startingCountdown;
    private String customKillMessage;

    private InteractItem[] item = new InteractItem[1];

    public Game(String name, Team[] teams, Kit[] kits, int winningCoins, int killCoins)
            throws GameInitializationException {
        this.name = name;
        this.teams = teams;
        this.kits = kits;
        this.winningCoins = winningCoins;
        this.killCoins = killCoins;

        if (kits.length == 0)
            throw new GameInitializationException("Could not initialize game: " + name + "! "
                    + "There needs to be at least one kit (Default kit)");

        int max = 0;
        int min = 0;
        for (Team team : teams) {
            max += team.getMaxPlayers();
            min += team.getMinPlayers();
        }
        this.maxPlayers = max;
        this.minPlayers = min;

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);

        setScoreBoard();
        kitSelector();
    }

    public Game(String name, Kit[] kits, int minPlayers, int maxPlayers, int winningCoins, int killCoins)
            throws GameInitializationException {
        this.name = name;
        this.teams = new Team[0];
        this.kits = kits;
        this.winningCoins = winningCoins;
        this.killCoins = killCoins;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;

        if (kits.length == 0)
            throw new GameInitializationException("Could not initialize game: " + name + "! "
                    + "There needs to be at least one kit (Default kit)");

        this.scoreBoard = Core.instance.scoreBoard;

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);

        setScoreBoard();
        kitSelector();
    }

    protected abstract void onStart();

    protected abstract void onUpdate();

    protected abstract void teleport();

    protected abstract void initScoreboard();

    protected abstract void updateScoreboard();

    protected abstract void updateBoardPlaceholders();

    protected abstract void onEnd();

    protected abstract void onKill(GamePlayer killed, GamePlayer killer);

    protected abstract void onDeath(GamePlayer player);

    protected abstract void onJoin(GamePlayer player);

    protected abstract void onLeave(GamePlayer player);


    private void setScoreBoard() {
        scoreBoard.setScoreboard(
                new String[]{this.name.toUpperCase()},
                new String[]{
                        ChatColor.GRAY +"",
                        ChatColor.WHITE + "Players: ",
                        ChatColor.GRAY + "" + ChatColor.BOLD,
                        ChatColor.AQUA + "",
                        ChatColor.AQUA + "" + ChatColor.BOLD,
                        ChatColor.WHITE + "Map: ",
                        ChatColor.YELLOW + "",
                        ChatColor.WHITE + ServerConfig.SERVER_DOMAIN.toString()

                },
                new String[]{
                        "",
                        ChatColor.AQUA + "%players%" + ChatColor.GRAY + "/%maxPlayers%",
                        "",
                        "%startingInfo%",
                        "",
                        ChatColor.GRAY + "%mapName%",
                        "",
                        ""


                }
        );
    }

    protected void setCustomKillMsg(String msg) {
        this.customKillMessage = msg;
    }

    private void updateScoreboardPlaceholders() {
        updateBoardPlaceholders();
        scoreBoard.addCustomPlaceholder("%players%", String.valueOf(this.players.size()));
        scoreBoard.addCustomPlaceholder("%maxPlayers%", String.valueOf(this.maxPlayers));
        scoreBoard.addCustomPlaceholder("%startingIn%", String.valueOf(this.startingCountdown));
        scoreBoard.addCustomPlaceholder("%mapName%", map.getName());
        scoreBoard.addCustomPlaceholder("%startingInfo%", isStarting
                ? "Starting in " + ChatColor.GREEN + "0:%startingIn%" + ChatColor.WHITE + " to"
                : "Not enough players to start");
    }

    protected void kitSelector() {
        if (kits.length < 2) return;
        item[0] = new InteractItem(
                ChatColor.YELLOW + "Kits",
                new ItemStack(Material.CHEST),
                4,
                player -> new PromptGUI(MPlayer.getMPlayer(player.getName()), "Kits") {

                    @Override
                    protected java.util.Map<Integer, Component> content() {
                        java.util.Map<Integer, Component> components = new HashMap<>();

                        int slot = 0;

                        for (Kit kit : kits) {

                            String purchaseKey = "game::"
                                    + getName().toLowerCase()
                                    + "::kit::" + kit.getName().toLowerCase();

                            boolean purchased =
                                    (Purchase.hasPurchased(getPlayer(), purchaseKey));

                            Button button = new Button(ChatColor.YELLOW + kit.getName(), kit.getDisplay().getType(), null);
                            button.setLore(new String[]{
                                    " ",
                                    purchased ? ChatColor.GREEN + "Owned"
                                            : ChatColor.WHITE + "Price: " + ChatColor.GOLD +  kit.getPrice() + " coins"
                            });

                            button.setOnClick(() -> {
                                if (purchased) {
                                    getGamePlayer(player).setKit(kit);
                                    player.closeInventory();
                                    item[0].removeFromPlayer(player);
                                } else {
                                    player.closeInventory();
                                    new Purchase(kit.getName() + " Kit", purchaseKey, kit.getPrice(), getPlayer());
                                }
                            });

                        }

                        return components;
                    }

                }
        );
    }

    private void giveKitSelector(GamePlayer player) {
        if (this.kits.length < 2) return;
        item[0].addToPlayer(player.getPlayer());
    }

    public void startGame() {

        state = GameState.IN_GAME;

        initScoreboard();

        for (GamePlayer player : players) {

            player.getPlayer().closeInventory();
            player.setStatus(GamePlayer.GameStatus.IN_GAME);
            if (player.getKit() == null) {
                player.setKit(kits[0]);
            }
            player.getKit().apply(player);
        }

        if (this.kits.length > 1) {
            item[0].removeFromEveryone();
        }

        onStart();

    }

    public void endGame(Team winner) {

        winner.win(this);
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(winner.getName())) continue;
            team.loose(winner);
        }
        endGame();

    }

    public void endGame(GamePlayer winner) {
        if (teams.length == 0) {
            winner.win();
            for (GamePlayer player : players) {
                if (player.getPlayer().getName()
                        .equalsIgnoreCase(winner.getPlayer().getName())) continue;

                player.loose(winner);
            }
        }
        endGame();
    }

    public void endGame() {
        map.resetBlockData();
        this.state = GameState.ENDING;

        onEnd();

        ComponentBuilder returnToLobby = new ComponentBuilder()
                .append(ChatColor.GREEN + "" + ChatColor.BOLD + "CLICK HERE!")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "hub"));
        BaseComponent[] msg = new ComponentBuilder()
                .append(ChatColor.GREEN + "" + ChatColor.BOLD + "NEW GAME IN 10 SECONDS! Return to hub? " + returnToLobby)
                .create();

        Bukkit.spigot().broadcast(msg);

        for (Player player : Core.onlinePlayers) {
            if (MPlayer.getMPlayer(player.getName()).isVanished()) continue;
            GamePlayer gPlayer = getGamePlayer(player);

            if (!players.contains(gPlayer)) players.add(gPlayer);
        }

        for (Team team : teams) {
            team.reset();
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                state = GameState.WAITING;
                teleport();
                for (GamePlayer player : players) {
                    giveKitSelector(player);
                }
            }
        }.runTaskLater(Core.instance, 20 * 10);
    }

    private void startCountdown() {
        this.startingCountdown = players.size() > maxPlayers / 2 ? 30 : 60;
        this.isStarting = true;
    }

    private void cancelCountdown() {
        this.startingCountdown = 0;
        this.isStarting = false;
        Bukkit.broadcastMessage(MessageColor.COLOR_ERROR + "Countdown cancelled due to not enough players");
    }

    protected void playerOut(GamePlayer player) {
        player.spectate();
        player.getTeam().playerOut(player);
        players.remove(player);
        player.getPlayer().sendTitle(MessageColor.COLOR_ERROR + "" + ChatColor.BOLD + "YOU DIED!",
                ChatColor.WHITE+ "You are now a spectator!",
                1, 60, 1);
    }

    private void resetSpectating() {
        for (GamePlayer player : players) {
            if (player.isSpectating()) {
                player.unSpectate();
            }
        }
    }

    /*
    Events
     */

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        if (MPlayer.getMPlayer(e.getPlayer().getName()).isVanished()) return;

        scoreBoard.setScoreboard(e.getPlayer());

        GamePlayer player = new GamePlayer(MPlayer.getMPlayer(e.getPlayer().getName()));
        player.setGame(this);
        giveKitSelector(player);

        Bukkit.broadcastMessage((teams.length == 0 ? ChatColor.GRAY :
                player.getTeam().getColor()) + e.getPlayer().getName() + ChatColor.GOLD + " joined the game!"
                + ChatColor.YELLOW + "("
                + ChatColor.AQUA + players.size() + ChatColor.YELLOW
                + "/" + ChatColor.AQUA + maxPlayers + ChatColor.YELLOW + ")");

        int index = -1;
        List<Team> teamsList = new ArrayList<>();
        for (Team team : teams) {
            if (teamsList.indexOf(team) <= index) continue;
            if (team.getPlayers().size() >= team.getMaxPlayers()) continue;
            team.add(player);
            index = index >= teamsList.size() - 1 ? -1 : index + 1;
        }


        onJoin(player);

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        GamePlayer player = getGamePlayer(e.getPlayer());

        Bukkit.broadcastMessage((teams.length == 0 ? ChatColor.GRAY :
                player.getTeam().getColor()) + e.getPlayer().getName() + ChatColor.GOLD + " left the game!"
                + (this.state.equals(GameState.WAITING) ? ChatColor.YELLOW + "("
                + ChatColor.AQUA + players.size() + ChatColor.YELLOW
                + "/" + ChatColor.AQUA + maxPlayers + ChatColor.YELLOW + ")" : ""));


        if (teams.length != 0) {
            player.getTeam().remove(player);
        }
        players.remove(player);
        gamePlayerMap.remove(e.getPlayer());

        if (this.state.equals(GameState.IN_GAME)) {
            if (players.size() > 2) {
                if (teams.length == 0) {
                    endGame(players.get(0));
                } else {
                    endGame(players.get(0).getTeam());
                }
            }

            int teamsWithNone = 0;

            for (Team team : teams) {
                if (team.getPlayers().size() == 0) teamsWithNone++;
            }

            if (Math.max(teamsWithNone, teams.length) - Math.min(teamsWithNone, teams.length) == 1) {
                endGame(teams[0]);
            }
        }

        onLeave(player);

    }

    @EventHandler
    public void onConnect(PlayerLoginEvent e) {

        if (MPlayer.getMPlayer(e.getPlayer().getName()).isVanished()) return;

        if (Bukkit.getOnlinePlayers().size() >= maxPlayers) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, MessageColor.COLOR_ERROR + "Game is full!");
        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getType().equals(UpdateEvent.UpdateType.TICK)) {
            onUpdate();
        }
        if (e.getType().equals(UpdateEvent.UpdateType.SECONDS)) {

            if (this.state.equals(GameState.WAITING)) {

                boolean canStart = false;

                for (Team team : teams) {
                    canStart = team.getPlayers().size() >= team.getMinPlayers();
                }

                if (canStart) {
                    startCountdown();
                } else {
                    cancelCountdown();
                }

                if (this.isStarting) {
                    startingCountdown--;
                    if (startingCountdown <= 0) {
                        startingCountdown = 0;
                        isStarting = false;
                        startGame();
                    }
                }

            }

            if (this.state.equals(GameState.IN_GAME)) {
                updateScoreboardPlaceholders();
                updateScoreboard();
            }


        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        GamePlayer killed = getGamePlayer(e.getEntity());
        GamePlayer killer = getGamePlayer(e.getEntity().getKiller());

        onDeath(killed);
        onKill(killed, killer);

        Bukkit.broadcastMessage((teams.length == 0 ? ChatColor.GRAY : killer.getTeam().getColor())
                + "" + (customKillMessage != null ? customKillMessage : ChatColor.YELLOW + " killed ")
                + (teams.length == 0 ? ChatColor.GRAY : killed.getTeam().getColor()));
        Coin.addCoins(killer.getMPlayer(), this.killCoins, true);
    }

    @EventHandler
    public void onBlockChangePlaced(BlockPlaceEvent e) {
        if (map.blockData.containsKey(e.getBlock())) return;
        map.blockData.put(e.getBlock(), e.getBlock().getType());
    }

    @EventHandler
    public void onBlockChangeBreak(BlockBreakEvent e) {
        if (map.blockData.containsKey(e.getBlock())) return;
        map.blockData.put(e.getBlock(), e.getBlock().getType());
    }

    @EventHandler
    public void onServerShutdown(ServerShutDownEvent e) {
        map.resetBlockData();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (MPlayer.getMPlayer(e.getPlayer().getName()).isVanished()) return;

        if (this.state.equals(GameState.WAITING)) {
            e.getPlayer().teleport(e.getFrom());
        }
    }

    /*
    Getters
     */

    protected GamePlayer getGamePlayer(Player player) {
        if (gamePlayerMap.containsKey(player)) {
            return gamePlayerMap.get(player);
        }
        return null;
    }

    protected Map getMap() {
        return this.map;
    }

    protected GameState getState() {
        return this.state;
    }

    protected ScoreBoard getScoreBoard() {
        return this.scoreBoard;
    }

    protected List<GamePlayer> getPlayers() {
        return this.players;
    }

    public String getName() {
        return name;
    }

    public Team[] getTeams() {
        return teams;
    }

    public Kit[] getKits() {
        return this.kits;
    }

    public int getWinningCoins() {
        return winningCoins;
    }

    public int getKillCoins() {
        return killCoins;
    }

}