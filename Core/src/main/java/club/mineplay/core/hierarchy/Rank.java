package club.mineplay.core.hierarchy;
/*
Created by Sander on 4/23/2021
*/

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Rank {

    private final String label;
    private final String prefix;
    private final double permissionLevel;
    private final List<Rank> inherits;

    private Color rankColor;
    private boolean defaultRank;
    private String description;

    public Rank(String label, String prefix, double permissionLevel) {
        this.label = label;
        this.prefix = prefix;
        this.permissionLevel = permissionLevel;
        this.rankColor = Color.gray;
        this.inherits = new ArrayList<>();
        this.register();
    }

    public void setColor(Color color) {
        this.rankColor = color;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFullPrefixWithSpace() {
        if (isDefaultRank()) return "";
        return ChatColor.WHITE + "[" + ChatColor.of(this.rankColor) + prefix + ChatColor.WHITE + "] ";
    }
    public String getFullPrefix() {
        if (isDefaultRank()) return "";
        return ChatColor.WHITE + "[" + ChatColor.of(this.rankColor) + prefix + ChatColor.WHITE + "]";
    }

    public BaseComponent[] getFullPrefixComponent() {
        if (isDefaultRank()) return new ComponentBuilder("").create();

        String description = this.description == null ? "" : this.description;

        ComponentBuilder builder = new ComponentBuilder()
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.DARK_AQUA + description)))
                .append(ChatColor.WHITE + "[").color(ChatColor.WHITE)
                .append(getPrefix()).color(ChatColor.of(getRankColor()))
                .append("] ").color(ChatColor.WHITE);

        return builder.create();

    }

    public ChatColor getChatColor() {
        if (isDefaultRank()) return ChatColor.GRAY;
        else return ChatColor.WHITE;
    }

    public ChatColor getNameColor() {
        if (isDefaultRank()) return ChatColor.GRAY;
        else return ChatColor.WHITE;
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
