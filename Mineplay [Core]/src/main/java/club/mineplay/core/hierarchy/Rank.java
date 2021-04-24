package club.mineplay.core.hierarchy;
/*
Created by Sander on 4/23/2021
*/

import org.bukkit.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Rank {

    private final String label;
    private final String prefix;
    private final double permissionLevel;
    private final List<Rank> inherits;

    private Color rankColor;
    private boolean defaultRank;

    public Rank(String label, String prefix, double permissionLevel) {
        this.label = label;
        this.prefix = prefix;
        this.permissionLevel = permissionLevel;
        this.rankColor = Color.GRAY;
        this.inherits = new ArrayList<>();
        this.register();
    }

    public void setColor(Color color) {
        this.rankColor = color;
    }

    public void setDefault() {
        this.defaultRank = true;
    }

    public void addInherits(Rank rank) {
        this.inherits.add(rank);
    }

    public abstract void onRegister();

    public void register() {
        this.onRegister();
    }

    public String getLabel() {
        return label;
    }

    public String getFullPrefix() {
        if (isDefaultRank()) return "";
        return Color.GRAY + "[" + this.rankColor + prefix + Color.GRAY + "]";
    }

    public Color getRankColor() {
        return rankColor;
    }

    public double getPermissionLevel() {
        return permissionLevel;
    }

    public List<Rank> getInherits() {
        return inherits;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean hasPermissionLevel(Rank rank) {
        return this.getPermissionLevel() >= rank.getPermissionLevel();
    }

    public boolean isDefaultRank() {
        return this.defaultRank;
    }
}
