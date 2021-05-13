package club.mineplay.core.player.hierarchy;

import club.mineplay.core.player.hierarchy.ranks.*;

public enum Ranks {

    DEFAULT(new Default()),
    VIP(new VIP()),
    CREATOR(new Creator()),
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
