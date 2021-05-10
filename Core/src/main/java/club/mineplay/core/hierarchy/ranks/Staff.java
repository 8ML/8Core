package club.mineplay.core.hierarchy.ranks;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.core.hierarchy.Rank;

import java.awt.*;

public class Staff extends Rank {

    public Staff() {
        super("Staff", "Staff", 50.0D);
        setColor(new Color(80, 130, 255));
        setDescription("Consists of our well organized staff team\nthat makes your experience satisfactory");
    }

    @Override
    public void onRegister() {

    }
}
