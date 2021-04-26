package club.mineplay.core.hierarchy.ranks;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.core.hierarchy.Rank;

import java.awt.*;

public class Creator extends Rank {

    public Creator() {
        super("Creator", "Creator", 10.0D);
        setColor(new Color(252, 42, 93));
    }

    @Override
    public void onRegister() {

    }
}
