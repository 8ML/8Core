package club.mineplay.core.hierarchy.ranks;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.core.hierarchy.Rank;

import java.awt.*;

public class Owner extends Rank {

    public Owner() {
        super("Owner", "Owner", 100.0D);
        setColor(new Color(238, 77, 77));
        setDescription("Owner consists of the founders.\nThe people that made Mineplay a reality");
    }

    @Override
    public void onRegister() {

    }
}
