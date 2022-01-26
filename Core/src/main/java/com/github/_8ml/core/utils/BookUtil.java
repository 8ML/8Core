/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 4/25/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.config.ServerConfig;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

@SuppressWarnings("deprecation")
public class BookUtil {


    /**
     * This will open a book to the player.
     *
     * @param player Player to display book to
     * @param book   The book to open
     */
    public static void openBook(Player player, ItemStack book) {
        final int slot = player.getInventory().getHeldItemSlot();
        final ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, book);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutOpenBook(EnumHand.MAIN_HAND));
        player.getInventory().setItem(slot, old);
    }


    /**
     * This will display the help book to the specified player
     *
     * @param player The player to display this book to
     */
    public static void displayHelpBook(Player player) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        assert bookMeta != null;

        BaseComponent[] cb = new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "\n&0Welcome to\n" +
                "&d8" + ServerConfig.SERVER_NAME + "&0!\n" +
                "\n" +
                "&0To start playing\n" +
                "&0games, click on an NPC\n" +
                "&0in the lobby.\n" +
                "\n" +
                "&0Join our discord for\n" +
                "&0news, updates, and\n" +
                "&0announcements!\n\n")).create();
        BaseComponent[] cl = new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&r     &r&d&l&nCLICK HERE&r"))
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, ServerConfig.SERVER_DISCORD_LINK + ""))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Click for link!").create())).create();

        BaseComponent[] comp = new ComponentBuilder().append(cb).append(cl).create();

        bookMeta.spigot().addPage(comp);

        bookMeta.setTitle("Help");
        bookMeta.setAuthor("8Core");
        book.setItemMeta(bookMeta);

        openBook(player, book);

    }

}
