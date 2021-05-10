package club.mineplay.core.hierarchy.ranks;
/*
Created by Sander on 4/23/2021
*/

import club.mineplay.core.hierarchy.Rank;

import java.awt.*;

public class VIP extends Rank {

    public VIP() {
        super("VIP", "VIP", 1.0D);
        this.setColor(new Color(200, 0, 255));
        this.setDescription("VIP players are our most valuable players,\nwe could not thank them more for their support");
    }

    @Override
    public void onRegister() {

    }
}
