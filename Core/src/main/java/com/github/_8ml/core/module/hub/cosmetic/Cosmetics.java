/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.hub.cosmetic;
/*
Created by @8ML (https://github.com/8ML) on June 19 2021
*/

import com.github._8ml.core.module.hub.cosmetic.cosmetics.gadgets.FireworkGunGadget;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.gadgets.StickGadget;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.hats.FishBowlHat;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.hats.GrassHat;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.hats.LeatherHat;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.outfits.AstronautOutfit;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.outfits.RainbowOutfit;

public enum Cosmetics {

    //GADGETS
    STICK_GADGET(StickGadget.class),
    FIREWORK_GUN_GADGET(FireworkGunGadget.class),
    //OUTFITS
    ASTRONAUT_OUTFIT(AstronautOutfit.class),
    RAINBOW_OUTFIT(RainbowOutfit.class),
    //HATS
    LEATHER_HAT(LeatherHat.class),
    GRASS_HAT(GrassHat.class),
    FISH_BOWL_HAT(FishBowlHat.class);


    private final Class<? extends Cosmetic> cosmeticClass;
    private Cosmetic cosmetic;

    Cosmetics(Class<? extends Cosmetic> cosmeticClass) {
        this.cosmeticClass = cosmeticClass;
    }

    public void register(CosmeticManager instance) {
        try {
            Cosmetic cosmetic = cosmeticClass.newInstance();
            instance.registerCosmetic(cosmetic);
            this.cosmetic = cosmetic;

        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public Class<? extends Cosmetic> getCosmeticClass() {
        return cosmeticClass;
    }

    public Cosmetic getCosmetic() {
        return this.cosmetic;
    }
}
