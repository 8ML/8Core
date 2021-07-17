package com.github._8ml.core.config;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.storage.file.PluginFile;
import com.github._8ml.core.Core;
import com.sun.istack.internal.NotNull;
import net.md_5.bungee.api.ChatColor;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Objects;

public enum MessageColor implements ConfigurationReload {

    COLOR_MAIN,
    COLOR_HIGHLIGHT,
    COLOR_SUCCESS,
    COLOR_ERROR;

    private static final PluginFile file = Core.instance.messagesYML;

    private ChatColor color;

    @Override
    public String toString() {
        return this.color.toString();
    }

    public void init() {
        ConfigurationReload.registerClass(this);
    }

    private static Color parseColor(String configValue) {
        String[] rgbSTR = configValue.split(",");
        if (rgbSTR.length == 0) return new Color(100, 100 ,100);
        int[] rgb = new int[]{Integer.parseInt(rgbSTR[0]),
                Integer.parseInt(rgbSTR[1]), Integer.parseInt(rgbSTR[2])};

        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    public static void refreshColors() {
        for (MessageColor color : values()) {

            color.color = ChatColor.of(parseColor(Objects.requireNonNull(file.getString(color.name()))));

        }
    }

    public static void initialize() {
        for (MessageColor value : values()) {
            value.init();
        }
    }

    @Override
    public void reloadConfigs() {
        refreshColors();
    }
}
