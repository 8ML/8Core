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
