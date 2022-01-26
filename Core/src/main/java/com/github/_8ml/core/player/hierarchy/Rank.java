/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.player.hierarchy;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import com.github._8ml.core.config.MessageColor;
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
        return this.isDefaultRank() ? "" : ChatColor.DARK_GRAY + "[" + this.rankColor + ChatColor.BOLD + prefix + ChatColor.DARK_GRAY + "] ";
    }
    public String getFullPrefix() {
        return isDefaultRank() ? "" : ChatColor.DARK_GRAY + "[" + this.rankColor + ChatColor.BOLD + prefix + ChatColor.DARK_GRAY + "]";
    }

    public BaseComponent[] getFullPrefixComponent() {
        if (isDefaultRank()) return new ComponentBuilder("").create();

        String description = this.description == null ? "" : this.description;

        ComponentBuilder builder = new ComponentBuilder()
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + description)))
                .append(ChatColor.DARK_GRAY + "[")
                .append(getRankColor() + "" + ChatColor.BOLD + getPrefix())
                .append(ChatColor.RESET + "" + ChatColor.DARK_GRAY +"] ");

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
