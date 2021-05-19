package xyz.dev_8.hub.game;
/*
Created by @8ML (https://github.com/8ML) on 5/19/2021
*/

import org.bukkit.entity.Player;

public class Selector {

    public enum GameType {
        DEMO_GAME("Demo", "Game");

        private final String gameType;
        private final String gameLabel;

        GameType(String gameLabel, String gameType) {
            this.gameLabel = gameLabel;
            this.gameType = gameType;
        }

        public String getGameLabel() {
            return gameLabel;
        }

        public String getGameType() {
            return gameType;
        }
    }

    public static void sendToGame(GameType game, Player player) {



    }

}
