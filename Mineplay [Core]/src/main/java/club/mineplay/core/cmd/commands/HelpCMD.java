package club.mineplay.core.cmd.commands;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.utils.BookUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Arrays;
import java.util.List;

public class HelpCMD extends CMD {
    public HelpCMD() {
        super("help", new String[]{"h"}, "", "", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

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

        book.setItemMeta(bookMeta);

        BookUtil.openBook(book, paramPlayer);

    }
}
