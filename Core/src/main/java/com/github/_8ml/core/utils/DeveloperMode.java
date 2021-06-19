package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on June 19 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.ServerConfig;

public class DeveloperMode {

    public static void log(String str) {
        if (ServerConfig.developmentMode) {

            Core.instance.getLogger().info("DEBUG: " + str);

        }
    }

}
