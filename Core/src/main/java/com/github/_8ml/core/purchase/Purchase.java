package com.github._8ml.core.purchase;
/*
Created by @8ML (https://github.com/8ML) on June 19 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.currency.Coin;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.ui.PromptGUI;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.component.components.Label;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Purchase {

    private final String name;
    private final String key;
    private final int price;
    private final MPlayer player;

    private final static SQL sql = Core.instance.sql;

    public Purchase(String name, String key, int price, MPlayer player) {

        this.name = name;
        this.key = key;
        this.price = price;
        this.player = player;

    }

    private void confirm() {

        new PromptGUI(this.player, "Confirm Purchase") {

            @Override
            protected Map<Integer, Component> content() {
                Map<Integer, Component> componentMap = new HashMap<>();

                //Info

                Label purchaseInfo = new Label(ChatColor.YELLOW + name, Material.BOOK, null);
                purchaseInfo.setLore(new String[]{
                        "",
                        ChatColor.WHITE + "Price: " + ChatColor.GOLD + price
                });

                //Confirm

                Button confirmButton = new Button(ChatColor.GREEN + "Confirm", Material.GREEN_WOOL, null);
                confirmButton.setLore(new String[]{ChatColor.GRAY + "Click to confirm purchase"});
                confirmButton.setOnClick(() -> {

                    if (player.getCoins() >= price) {

                        Coin.removeCoins(player, price);
                        player.getPlayer().sendMessage(ChatColor.GREEN + "Successfully purchased " + ChatColor.GRAY + name);
                        updateDatabase();

                    } else {
                        error("Not enough coins!");
                    }

                });

                //Cancel

                Button cancelButton = new Button(ChatColor.RED + "Cancel", Material.RED_WOOL, null);
                cancelButton.setLore(new String[]{"Click to cancel purchase"});
                cancelButton.setOnClick(() -> {
                    player.getPlayer().closeInventory();
                    HandlerList.unregisterAll(this);
                });


                //Register

                componentMap.put(4, purchaseInfo);
                componentMap.put(11, confirmButton);
                componentMap.put(15, cancelButton);

                return componentMap;
            }

        };

    }

    private void error(String message) {

        new PromptGUI(player, "Purchase Failed") {

            @Override
            protected Map<Integer, Component> content() {
                Map<Integer, Component> componentMap = new HashMap<>();

                Label error = new Label(ChatColor.RED + "Purchase Failed", Material.RED_WOOL, null);
                error.setLore(new String[]{ChatColor.GRAY + message});

                for (int i = 0; i < getInventory().getSize() - 1; i ++) {
                    componentMap.put(i, error);
                }

                return componentMap;
            }
        };

    }


    private void updateDatabase() {
        try {

            PreparedStatement st = sql.preparedStatement("INSERT INTO purchases (uuid, key, price) VALUES (?,?,?)");
            st.setString(1, player.getUUID());
            st.setString(2, key);
            st.setInt(3, price);

            try {
                st.execute();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public int getPrice() {
        return price;
    }

    public MPlayer getPlayer() {
        return player;
    }

    public static boolean hasPurchased(MPlayer player, String key) {

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM purchases WHERE `uuid`=? AND `key`=?");
            st.setString(1, player.getUUID());
            st.setString(2, key);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                sql.closeConnection(st);

                return true;
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }
}
