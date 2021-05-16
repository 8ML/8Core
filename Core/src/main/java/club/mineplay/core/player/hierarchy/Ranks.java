package club.mineplay.core.player.hierarchy;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import club.mineplay.core.player.hierarchy.ranks.*;

public enum Ranks {

    DEFAULT(new Default()),
    VIP(new VIP()),
    YT(new YT()),
    BUILD_TEAM(new BuildTeam()),
    STAFF(new Staff()),
    ADMIN(new Admin()),
    OWNER(new Owner());

    private final Rank rank;

    Ranks(Rank rank) {
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }
}
