package com.github._8ml.core.module.game.games;
/*
Created by @8ML (https://github.com/8ML) on June 26 2021
*/

import com.github._8ml.core.module.game.exceptions.GameNotFoundException;
import com.github._8ml.core.module.game.games.Slap.SlapGame;
import com.github._8ml.core.module.game.manager.Game;

public enum GameRegistry {

    SLAP(SlapGame.class);

    private final Class<? extends Game> gameClass;
    private Game game;

    GameRegistry(Class<? extends Game> gameClass) {
        this.gameClass = gameClass;
    }

    public void register() {
        try {
            this.game = this.gameClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Game getGame() {
        return game;
    }

    public Class<? extends Game> getGameClass() {
        return gameClass;
    }


    private static Game enabledGame;

    public static Game getEnabledGame() {
        return enabledGame;
    }

    public static void setGame(String name) throws GameNotFoundException {
        for (GameRegistry registry : values()) {
            if (registry.name().equalsIgnoreCase(name)) {
                registry.register();
                enabledGame = registry.getGame();
            }
        }
        if (enabledGame == null) throw new GameNotFoundException(name);
    }
}
