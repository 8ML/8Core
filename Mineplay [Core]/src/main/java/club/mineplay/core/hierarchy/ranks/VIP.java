package club.mineplay.core.hierarchy.ranks;
/*
Created by Sander on 4/23/2021
*/

import club.mineplay.core.hierarchy.Rank;
import org.bukkit.Color;

public class VIP extends Rank {

    public VIP() {
        super("VIP", "VIP", 1.0D);
        this.setColor(Color.fromRGB(100, 0, 255));
    }

    @Override
    public void onRegister() {

    }
}
