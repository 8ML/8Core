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
Created by @8ML (https://github.com/8ML) on 5/19/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.storage.file.PluginFile;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.sql.SQLException;

public enum ServerConfig implements ConfigurationReload {

    SPAWN_POINT(true),
    SERVER_NAME(false),
    SERVER_DOMAIN(false),
    SERVER_STORE_DOMAIN(false),
    SERVER_APPEAL_DOMAIN(false),
    SERVER_DISCORD_LINK(false);

    private static final PluginFile config = Core.instance.configYML;

    private Object value;
    private boolean ignore;

    ServerConfig(boolean ignore) {
        this.ignore = ignore;
    }

    public void init() {
        ConfigurationReload.registerClass(this);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public static void refreshValues() {
        for (ServerConfig conf : ServerConfig.values()) {

            if (conf.ignore) continue;

            conf.value = config.getString("options." + conf.name());

        }
    }

    public static void reloadAllConfigs() {
        for (ConfigurationReload classToReload : ConfigurationReload.classes) {
            classToReload.reloadConfigs();
        }
    }

    public static void initialize() {
        for (ServerConfig conf : values()) {
            conf.init();
        }
    }

    @Override
    public void reloadConfigs() {
        refreshValues();
    }

    /**
     *
     * DEVELOPMENT PURPOSES ONLY!
     *
     * Set this to true to print debug information to console and enable commands registered with CommandCenter.registerTestCommand
     * Its very important to set this to false when using this on a live server as any rank will have access to set their rank.
     */
    public static final boolean developmentMode = true;

}
