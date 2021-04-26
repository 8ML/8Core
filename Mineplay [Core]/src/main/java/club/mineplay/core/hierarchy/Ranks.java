package club.mineplay.core.hierarchy;

import club.mineplay.core.hierarchy.ranks.Admin;
import club.mineplay.core.hierarchy.ranks.Default;
import club.mineplay.core.hierarchy.ranks.VIP;

public enum Ranks {

    DEFAULT(new Default()),
    VIP(new VIP()),
    ADMIN(new Admin());

    private final Rank rank;

    Ranks(Rank rank) {
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }
}
