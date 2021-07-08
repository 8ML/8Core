package com.github._8ml.core.player.hierarchy;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import com.github._8ml.core.player.hierarchy.ranks.*;

import java.util.Arrays;

public enum Ranks {

    DEFAULT(Default.class),
    VIP(VIP.class),
    YT(YT.class),
    BUILD_TEAM(BuildTeam.class),
    STAFF(Staff.class),
    ADMIN(Admin.class),
    OWNER(Owner.class);

    private final Class<? extends Rank> rankClass;
    private Rank rank;

    Ranks(Class<? extends Rank> rank) {
        this.rankClass = rank;
    }

    public void register() {
        try {

            this.rank = this.rankClass.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Rank getRank() {
        return rank;
    }

    public static void registerRanks() {

        Arrays.stream(values()).forEach(Ranks::register);

    }
}
