package club.mineplay.core.utils;
/*
Created by Sander on 4/25/2021
*/

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

public class BookUtil {

    public static void openBook(Player player, ItemStack book) {
        final int slot = player.getInventory().getHeldItemSlot();
        final ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, book);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutOpenBook(EnumHand.MAIN_HAND));
        player.getInventory().setItem(slot, old);
    }

    public static void displayHelpBook(Player player) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        assert bookMeta != null;

        BaseComponent[] cb = new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&0Welcome to the\n" +
                "&6Mineplay Network&0!\n" +
                "\n" +
                "&0To start playing\n" +
                "&0games, click on an NPC\n" +
                "&0in the lobby.\n" +
                "\n" +
                "&0Join our discord for\n" +
                "&0news, updates, and\n" +
                "&0announcements!\n\n")).create();
        BaseComponent[] cl = new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&r       &r&d&l&nCLICK HERE&r"))
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.io/mineplayclub"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Join our discord").create())).create();

        BaseComponent[] comp = new ComponentBuilder().append(cb).append(cl).create();

        bookMeta.spigot().addPage(comp);

        bookMeta.setTitle("Help");
        bookMeta.setAuthor("Mineplay");
        book.setItemMeta(bookMeta);

        openBook(player, book);

    }

}
