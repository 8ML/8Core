/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.game.games;
/*
Created by @8ML (https://github.com/8ML) on June 26 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.game.GameInfo;
import com.github._8ml.core.module.game.exceptions.GameNotFoundException;
import com.github._8ml.core.module.game.games.slap.SlapGame;
import com.github._8ml.core.module.game.games.platform.PlatformGame;
import com.github._8ml.core.module.game.manager.Game;

public enum GameRegistry {

    SLAP(SlapGame.class),
    PLATFORM(PlatformGame.class);

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
                GameInfo.storeInfo(Core.instance.serverName, enabledGame.getName(),
                        enabledGame.getMaxPlayers(), enabledGame.getMinPlayers(), 0);
            }
        }
        if (enabledGame == null) throw new GameNotFoundException(name);
    }
}
