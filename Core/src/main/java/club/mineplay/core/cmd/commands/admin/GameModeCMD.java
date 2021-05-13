package club.mineplay.core.cmd.commands.admin;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.player.hierarchy.Ranks;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameModeCMD extends CMD {

    public GameModeCMD() {
        super("gm", new String[0], "", "", Ranks.ADMIN);
    }

    public Map<Player, GameMode> modeMap = new HashMap<>();

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length == 0) {

            if (paramPlayer.getGameMode().equals(GameMode.SURVIVAL)) {
                paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to Creative.");
                paramPlayer.setGameMode(GameMode.CREATIVE);
            } else {
                paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to Survival.");
                paramPlayer.setGameMode(GameMode.SURVIVAL);
            }

        } else {
            if (paramArrayOfString[0].equalsIgnoreCase("s")) {

                if (paramPlayer.getGameMode().equals(GameMode.SPECTATOR)) {
                    if (modeMap.containsKey(paramPlayer)) {

                        paramPlayer.setGameMode(modeMap.get(paramPlayer));

                        StringBuilder b = new StringBuilder();
                        char first = paramPlayer.getGameMode().toString().charAt(0);

                        b.append(first);

                        for (int i = 1; i < paramPlayer.getGameMode().toString().length(); i++) {
                            b.append(String.valueOf(paramPlayer.getGameMode().toString().charAt(i)).toLowerCase());
                        }


                        paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to " + b.toString() + ".");

                    } else {
                        paramPlayer.setGameMode(GameMode.SURVIVAL);
                        paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to Survival.");
                    }
                } else {

                    modeMap.put(paramPlayer, paramPlayer.getGameMode());

                    paramPlayer.setGameMode(GameMode.SPECTATOR);
                    paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to Spectator.");
                }

            }
        }

    }
}
