package com.github._8ml.core.module.hub.cosmetic;
/*
Created by @8ML (https://github.com/8ML) on June 19 2021
*/

import com.github._8ml.core.module.hub.cosmetic.cosmetics.StickCosmetic;

public enum Cosmetics {

    STICK(StickCosmetic.class);

    private final Class<?> cosmeticClass;
    private Cosmetic cosmetic;

    Cosmetics(Class<?> cosmeticClass) {
        this.cosmeticClass = cosmeticClass;
    }

    public void register(CosmeticManager instance) {
        try {
            Cosmetic cosmetic = (Cosmetic) cosmeticClass.newInstance();
            instance.registerCosmetic(cosmetic);
            this.cosmetic = cosmetic;

        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public Class<?> getCosmeticClass() {
        return cosmeticClass;
    }

    public Cosmetic getCosmetic() {
        return this.cosmetic;
    }
}
