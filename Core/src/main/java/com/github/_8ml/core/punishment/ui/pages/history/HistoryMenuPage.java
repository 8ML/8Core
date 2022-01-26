/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.punishment.ui.pages.history;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.punishment.Punishment;
import com.github._8ml.core.punishment.ui.PunishUI;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.page.Page;
import com.github._8ml.core.config.MessageColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class HistoryMenuPage extends Page {

    static Punishment.PunishType selectedHistoryPage = null;

    private final MPlayer target;

    public HistoryMenuPage(MPlayer target) {
        super("History", 54, true);

        this.target = target;

        setFrameLabel(" ");
    }

    @Override
    protected void onPreOpen() {

    }

    @Override
    public void onOpen() {

        Button bans = new Button(MessageColor.COLOR_HIGHLIGHT + "Bans", Material.IRON_DOOR, getParent());
        bans.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + MessageColor.COLOR_HIGHLIGHT + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " bans"});
        bans.setOnClick(() -> {
            selectedHistoryPage = Punishment.PunishType.BAN;
            getParent().openPage(2);
        });

        Button mutes = new Button(MessageColor.COLOR_HIGHLIGHT + "Mutes", Material.WRITABLE_BOOK, getParent());
        mutes.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + MessageColor.COLOR_HIGHLIGHT + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " mutes"});
        mutes.setOnClick(() -> {
            selectedHistoryPage = Punishment.PunishType.MUTE;
            getParent().openPage(2);
        });

        Button warns = new Button(MessageColor.COLOR_HIGHLIGHT + "Warns", Material.PAPER, getParent());
        warns.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + MessageColor.COLOR_HIGHLIGHT + this.target.getPlayerStr()  + "'s" + MessageColor.COLOR_MAIN + " warns"});
        warns.setOnClick(() -> {
            selectedHistoryPage = Punishment.PunishType.WARN;
            getParent().openPage(2);
        });

        Button kicks = new Button(MessageColor.COLOR_HIGHLIGHT + "Kicks", Material.STICK, getParent());
        kicks.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + MessageColor.COLOR_HIGHLIGHT + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " kicks"});
        kicks.setOnClick(() -> {
            selectedHistoryPage = Punishment.PunishType.KICK;
            getParent().openPage(2);
        });

        Button exit = new Button(MessageColor.COLOR_ERROR + "Go Back", Material.ARROW, getParent());
        exit.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Return to main menu"});
        exit.setOnClick(() -> getParent().openPage(0));

        addComponent(bans, 19);
        addComponent(mutes, 21);
        addComponent(warns, 23);
        addComponent(kicks, 25);
        addComponent(exit, 40);

    }

    @Override
    public void onFrameClick() {

    }
}
