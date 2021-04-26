package club.mineplay.core.utils;
/*
Created by Sander on 4/25/2021
*/

import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Arrays;
import java.util.List;

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

        List<String> pages = Arrays.asList(ChatColor.translateAlternateColorCodes('&', "\n" +
                "&0Welcome to the\n" +
                "&6Mineplay Network&0!\n" +
                "\n" +
                "&0To start playing\n" +
                "&0games, click on an NPC\n" +
                "&0in the lobby.\n" +
                "\n" +
                "&0Join our discord for\n" +
                "&0news, updates, and\n" +
                "&0announcements!\n" +
                "\n" +
                "&r       &r&d&l&nCLICK HERE&r"));

        bookMeta.setPages(pages);

        bookMeta.setTitle("Help");
        bookMeta.setAuthor("Mineplay");
        book.setItemMeta(bookMeta);

        openBook(player, book);
    }

}
