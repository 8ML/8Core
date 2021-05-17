package net.clubcraft.core.player.hierarchy;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public abstract class Rank {

    private final String label;
    private final String prefix;
    private final double permissionLevel;
    private final List<Rank> inherits;

    private ChatColor rankColor;
    private ChatColor nameColor;
    private boolean defaultRank;
    private String description;

    public Rank(String label, String prefix, double permissionLevel) {
        this.label = label;
        this.prefix = prefix;
        this.permissionLevel = permissionLevel;
        this.rankColor = ChatColor.GRAY;
        this.inherits = new ArrayList<>();
        this.register();
    }

    protected void setColor(ChatColor rankColor, ChatColor nameColor) {
        this.rankColor = rankColor;
        this.nameColor = nameColor;
    }


    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setDefault() {
        this.defaultRank = true;
    }

    public void addInherits(Rank rank) {
        this.inherits.add(rank);
    }

    public abstract void onRegister();

    private void register() {
        this.onRegister();
    }

    public String getLabel() {
        return label;
    }

    public String getFullPrefixWithSpace() {
        return this.isDefaultRank() ? "" : this.rankColor + "[" + this.rankColor + prefix + this.rankColor + "] ";
    }
    public String getFullPrefix() {
        return isDefaultRank() ? "" : this.rankColor + "[" + this.rankColor + prefix + this.rankColor + "]";
    }

    public BaseComponent[] getFullPrefixComponent() {
        if (isDefaultRank()) return new ComponentBuilder("").create();

        String description = this.description == null ? "" : this.description;

        ComponentBuilder builder = new ComponentBuilder()
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + description)))
                .append(getRankColor() + "[")
                .append(getRankColor() + getPrefix())
                .append(getRankColor() +"] ");

        return builder.create();

    }

    public ChatColor getChatColor() {
        return isDefaultRank() ? ChatColor.GRAY : ChatColor.WHITE;
    }

    public ChatColor getNameColor() {
        return isDefaultRank() ? ChatColor.GRAY : this.nameColor;
    }

    public ChatColor getRankColor() {
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
