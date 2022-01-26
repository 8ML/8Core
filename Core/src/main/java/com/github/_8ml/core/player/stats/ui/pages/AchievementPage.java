/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.player.stats.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on 5/20/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.ui.page.Page;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import com.github._8ml.core.player.achievement.Achievement;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.component.components.Label;

public class AchievementPage extends Page {

    private final MPlayer player;

    public AchievementPage(MPlayer player) {
        super(player.getPlayerStr() + "'s Achievements", 45, true);
        this.player = player;
    }

    @Override
    protected void onPreOpen() {

    }

    @Override
    public void onOpen() {

        int num = 10;

        for (Achievement achievement : Achievement.getAchievementClasses()) {

            if (num >= 25) continue;

            Label ach = new Label(Material.BOOK, getParent());
            ach.setLabel(MessageColor.COLOR_SUCCESS + achievement.getName());
            ach.setLore(new String[]{
                    ChatColor.WHITE + "Game: " + MessageColor.COLOR_MAIN
                            + (achievement.getGame().equals("") ? "None" : achievement.getGame()),
                    "",
                    MessageColor.COLOR_MAIN + achievement.getDescription(),
                    " ",
                    achievement.hasAchievement(player) ? MessageColor.COLOR_SUCCESS + "Completed!"
                            : MessageColor.COLOR_ERROR + "Not Completed!"
            });

            addComponent(ach, num);
            num++;
            if (getFrameSlots().contains(num)) num+=2;

        }

        Button exit = new Button(MessageColor.COLOR_ERROR + "Close", Material.BARRIER, getParent());
        exit.setOnClick(() -> {
            getParent().getPlayer().getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        addComponent(exit, 31);

    }

    @Override
    public void onFrameClick() {

    }
}
