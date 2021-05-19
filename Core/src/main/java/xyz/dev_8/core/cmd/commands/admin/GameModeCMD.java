package xyz.dev_8.core.cmd.commands.admin;
/*
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import xyz.dev_8.core.cmd.CMD;
import xyz.dev_8.core.config.MessageColor;
import xyz.dev_8.core.player.hierarchy.Ranks;
import xyz.dev_8.core.utils.StringUtils;
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



                        paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to "
                                + StringUtils.formatCapitalization(paramPlayer.getGameMode().toString()) + ".");

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
