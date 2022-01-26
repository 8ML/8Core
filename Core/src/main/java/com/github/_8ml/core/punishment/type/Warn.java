/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.punishment.type;
/*
Created by @8ML (https://github.com/8ML) on 4/29/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.punishment.Punishment;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

public class Warn extends Punishment {

    private final MPlayer player, executor;
    private final String reason;

    public Warn(MPlayer player, MPlayer executor, String reason) {
        super(PunishType.WARN);

        this.player = player;
        this.executor = executor;
        this.reason = reason;
    }

    public void execute() {
        execute(executor, player, reason, new PunishTime(0));
    }

    @Override
    public void onExecute() {
        if (!player.isOffline()) {
            player.getPlayer().sendMessage(getPunishMessage());
        }
    }

    @Override
    public String getPunishMessage() {

        if (!this.player.isOffline())
            this.player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_CAT_HISS, 1f, 1f);

        return MessageColor.COLOR_ERROR + "You were warned by " + this.executor.getPlayerStr() + " for " + MessageColor.COLOR_MAIN + this.reason;
    }
}
