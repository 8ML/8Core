/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.game.games.platform.kits;
/*
Created by @8ML (https://github.com/8ML) on August 07 2021
*/

import com.github._8ml.core.module.game.manager.kit.Kit;
import com.github._8ml.core.module.game.manager.player.GamePlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class AcrobatKit extends Kit {

    public AcrobatKit() {
        super("Acrobat", "Gives you acrobatic skills! (Double jump)", new ItemStack(Material.LEATHER_BOOTS), 100);
    }

    @Override
    protected Map<ItemStack, ItemInfo> getItems() {
        return null;
    }

    @Override
    protected void onApply(GamePlayer player) {
        player.getPlayer().setAllowFlight(true);
    }
}
