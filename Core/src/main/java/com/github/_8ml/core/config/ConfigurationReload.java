package com.github._8ml.core.config;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import java.util.ArrayList;
import java.util.List;


/**
 * This interface can be implemented to provide the
 * reloadConfigs() method
 *
 * The reloadConfigs() method is called on all classes implementing
 * this interface when /reloadconfigs command or ServerConfig.reloadAllConfigs() is called
 */
public interface ConfigurationReload {

    List<ConfigurationReload> classes = new ArrayList<>();
    static void registerClass(ConfigurationReload clazz) {
        classes.add(clazz);
    }

    void reloadConfigs();

}
