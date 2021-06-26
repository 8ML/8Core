package com.github._8ml.core.module.game.exceptions;
/*
Created by @8ML (https://github.com/8ML) on June 27 2021
*/

import com.github._8ml.core.Core;

public class GameInitializationException extends Exception {

    public GameInitializationException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        Core.instance.getServer().shutdown();
    }

}
