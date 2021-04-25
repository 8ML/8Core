package club.mineplay.core.hierarchy.ranks;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.hierarchy.Rank;

import java.awt.*;

public class Admin extends Rank {

    public Admin() {
        super("Administrator", "Admin", 90.0D);
        setColor(new Color(255, 0, 60));
    }

    @Override
    public void onRegister() {

    }
}
