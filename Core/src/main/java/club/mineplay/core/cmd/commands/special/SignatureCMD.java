package club.mineplay.core.cmd.commands.special;
/*
Created by Sander on 5/9/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import org.bukkit.entity.Player;

public class SignatureCMD extends CMD {

    public SignatureCMD() {
        super("signature", new String[0], "", "/signature", Ranks.VIP);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        MPlayer player = MPlayer.getMPlayer(paramPlayer.getName());

        if (paramArrayOfString.length == 0) {
            player.setSignature("");
            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Your signature is now removed!");
        } else {

            StringBuilder args = new StringBuilder(paramArrayOfString[0]);
            for (String s : paramArrayOfString) {
                if (s.equals(paramArrayOfString[0])) continue;
                args.append(" ").append(s);
            }

            player.setSignature(args.toString());

            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS
                    + "Success! your signature is now " + MessageColor.COLOR_MAIN + args.toString());

        }

    }
}
