package club.mineplay.core.player.hierarchy.ranks;
/*
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import club.mineplay.core.player.hierarchy.Rank;
import org.bukkit.ChatColor;

public class YT extends Rank {

    public YT() {
        super("YT", "YT", 10.0D);
        setColor(ChatColor.GOLD, ChatColor.GOLD);
        setDescription("YouTubers are content creators and streamers.");
    }

    @Override
    public void onRegister() {

    }
}
