package club.mineplay.core.config;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.storage.file.PluginFile;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.Objects;

public class MessageColor {

    private static final PluginFile file = Core.instance.messagesYML;

    public static ChatColor COLOR_MAIN = ChatColor.of(parseColor(Objects.requireNonNull(file.getString("main"))));
    public static ChatColor COLOR_HIGHLIGHT = ChatColor.of(parseColor(Objects.requireNonNull(file.getString("highlight"))));
    public static ChatColor COLOR_ERROR = ChatColor.of(parseColor(Objects.requireNonNull(file.getString("error"))));
    public static ChatColor COLOR_SUCCESS = ChatColor.of(parseColor(Objects.requireNonNull(file.getString("success"))));

    private static Color parseColor(String configValue) {
        String[] rgbSTR = configValue.split(",");
        int[] rgb = new int[]{Integer.parseInt(rgbSTR[0]),
                Integer.parseInt(rgbSTR[1]), Integer.parseInt(rgbSTR[2])};

        return new Color(rgb[0], rgb[1], rgb[2]);
    }

}
