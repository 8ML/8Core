/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.config;
/*
Created by @8ML (https://github.com/8ML) on 4/27/2021
*/

import org.bukkit.entity.Player;

public class Messages {

    public static void sendFailedMessage(Player player) {
        player.sendMessage(MessageColor.COLOR_ERROR + "Failed!");
    }

    public static void sendSuccessMessage(Player player) {
        player.sendMessage(MessageColor.COLOR_SUCCESS + "Done!");
    }

    public static void sendNotExistsMessage(Player player, String notExists) {
        player.sendMessage(MessageColor.COLOR_ERROR + notExists + " does not exist!");
    }

    public static void sendPlayerIsOfflineMessage(Player player, String isOffline) {
        player.sendMessage(MessageColor.COLOR_ERROR + isOffline + " is offline!");
    }

}
