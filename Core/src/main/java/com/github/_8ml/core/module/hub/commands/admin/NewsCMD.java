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
import org.bukkit.entity.Player;

import java.util.List;

public class NewsCMD extends CMD {

    public NewsCMD() {
        super("news", new String[0], "", "/news <add,remove, list> <news,id>", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        News newsManager = HubModule.instance.newsManager;

        if (paramArrayOfString.length <= 2) {

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
                            .append(MessageColor.COLOR_MAIN)
                            .append(str);

                }

                paramPlayer.sendMessage(builder.toString());

                return;

            }

            paramPlayer.sendMessage(getUsage());
            return;
        }

        if (paramArrayOfString[0].equalsIgnoreCase("add")) {

            newsManager.addNews(paramArrayOfString[1]);
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
            newsManager.removeNews(str);
            Messages.sendSuccessMessage(paramPlayer);

        }

    }

}
