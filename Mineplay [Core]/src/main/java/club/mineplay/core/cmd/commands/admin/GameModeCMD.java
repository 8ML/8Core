package club.mineplay.core.cmd.commands.admin;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.hierarchy.Ranks;
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
                paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to CREATIVE.");
                paramPlayer.setGameMode(GameMode.CREATIVE);
            } else {
                paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to SURVIVAL.");
                paramPlayer.setGameMode(GameMode.SURVIVAL);
            }

        } else {
            if (paramArrayOfString[0].equalsIgnoreCase("s")) {

                if (paramPlayer.getGameMode().equals(GameMode.SPECTATOR)) {
                    if (modeMap.containsKey(paramPlayer)) {

                        paramPlayer.setGameMode(modeMap.get(paramPlayer));
                        paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to " + paramPlayer.getGameMode() + ".");

                    } else {
                        paramPlayer.setGameMode(GameMode.SURVIVAL);
                        paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to SURVIVAL.");
                    }
                } else {

                    modeMap.put(paramPlayer, paramPlayer.getGameMode());

                    paramPlayer.setGameMode(GameMode.SPECTATOR);
                    paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to SPECTATOR.");
                }

            }
        }

    }
}
