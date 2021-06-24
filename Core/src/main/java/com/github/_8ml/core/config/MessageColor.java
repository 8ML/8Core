package com.github._8ml.core.config;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.storage.file.PluginFile;
import com.github._8ml.core.Core;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.Objects;

public class MessageColor {

    private static final PluginFile file = Core.instance.messagesYML;

    public static ChatColor COLOR_MAIN = ChatColor.of(parseColor(Objects.requireNonNull(file.getString("main"))));
    public static ChatColor COLOR_HIGHLIGHT = ChatColor.of(parseColor(Objects.requireNonNull(file.getString("highlight"))));
    public static ChatColor COLOR_ERROR = ChatColor.of(parseColor(Objects.requireNonNull(file.getString("error"))));
    public static ChatColor COLOR_SUCCESS = ChatColor.of(parseColor(Objects.requireNonNull(file.getString("success"))));
    /*
    //public static ChatColor COLOR_MAIN_GRAY = ChatColor.of(parseColor(Objects.requireNonNull(file.getString("main-gray"))));
    //public static ChatColor COLOR_DARK_GRAY = ChatColor.of(parseColor(Objects.requireNonNull(file.getString("dark-gray"))));
     */

    private static Color parseColor(String configValue) {
        String[] rgbSTR = configValue.split(",");
        if (rgbSTR.length == 0) return new Color(100, 100 ,100);
        int[] rgb = new int[]{Integer.parseInt(rgbSTR[0]),
                Integer.parseInt(rgbSTR[1]), Integer.parseInt(rgbSTR[2])};

        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    public static void refreshColors() {

    }

}
