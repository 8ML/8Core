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
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.storage.file.PluginFile;
import net.md_5.bungee.api.ChatColor;

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
