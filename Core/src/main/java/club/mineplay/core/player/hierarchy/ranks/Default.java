package club.mineplay.core.player.hierarchy.ranks;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import club.mineplay.core.player.hierarchy.Rank;

public class Default extends Rank {

    public Default() {
        super("", "", 0.0D);
        setDefault();
    }

    @Override
    public void onRegister() {

    }
}
