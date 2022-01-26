/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.hub.commands.admin;
/*
Created by @8ML (https://github.com/8ML) on June 23 2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.config.Messages;
import com.github._8ml.core.module.hub.HubModule;
import com.github._8ml.core.module.hub.news.News;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class NewsCMD extends CMD {

    public NewsCMD() {
        super("news", new String[0], "", "/news <add,remove,list> <news,id>", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        News newsManager = HubModule.instance.newsManager;

        if (paramArrayOfString.length < 2) {

            if (paramArrayOfString.length == 1) {
                if (paramArrayOfString[0].equalsIgnoreCase("list")) {

                    List<String> news = newsManager.getNews();
                    if (news.isEmpty()) {
                        paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "There are no news!");
                        return;
                    }

                    StringBuilder builder = new StringBuilder();
                    for (String str : news) {

                        builder.append("\n").append(MessageColor.COLOR_HIGHLIGHT)
                                .append(newsManager.getID(str))
                                .append(" ")
                                .append(MessageColor.COLOR_MAIN)
                                .append(str);

                    }

                    paramPlayer.sendMessage(builder.toString());

                    return;

                }
            }

            paramPlayer.sendMessage(getUsage());
            return;
        }

        StringBuilder argsBuilder = new StringBuilder();
        for (int i = 1; i < paramArrayOfString.length; i++) {

            argsBuilder.append(paramArrayOfString[i]).append(" ");

        }

        String contentArgs = ChatColor.translateAlternateColorCodes('&',
                argsBuilder.toString());

        if (paramArrayOfString[0].equalsIgnoreCase("add")) {

            newsManager.addNews(contentArgs);
            Messages.sendSuccessMessage(paramPlayer);
            return;
        }

        if (!paramArrayOfString[0].equalsIgnoreCase("remove")) {
            Messages.sendFailedMessage(paramPlayer);
            return;
        }

        try {

            int id = Integer.parseInt(paramArrayOfString[1]);
            newsManager.removeNews(id);
            Messages.sendSuccessMessage(paramPlayer);

        } catch (NumberFormatException e) {

            String str = paramArrayOfString[1];
            newsManager.removeNews(contentArgs);
            Messages.sendSuccessMessage(paramPlayer);

        }

    }

}
