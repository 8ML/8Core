package club.mineplay.core.hierarchy.ranks;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.core.hierarchy.Rank;

import java.awt.*;

public class BuildTeam extends Rank {

    public BuildTeam() {
        super("BuildTeam", "Build Team", 30.0D);
        setColor(new Color(30, 160, 150));
    }

    @Override
    public void onRegister() {

    }
}
