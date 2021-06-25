package com.github._8ml.core.player.hierarchy;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import com.github._8ml.core.player.hierarchy.ranks.*;

public enum Ranks {

    DEFAULT(Default.class),
    VIP(VIP.class),
    YT(YT.class),
    BUILD_TEAM(BuildTeam.class),
    STAFF(Staff.class),
    ADMIN(Admin.class),
    OWNER(Owner.class);

    private final Class<?> rankClass;
    private Rank rank;

    Ranks(Class<?> rank) {
        this.rankClass = rank;
    }

    public void register() {
        try {

            this.rank = (Rank) this.rankClass.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Rank getRank() {
        return rank;
    }

    public static void registerRanks() {

        for (Ranks rank : values()) {

            rank.register();

        }

    }
}
