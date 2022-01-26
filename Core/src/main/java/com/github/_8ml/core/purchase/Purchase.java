/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.purchase;
/*
Created by @8ML (https://github.com/8ML) on June 19 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.currency.Coin;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.ui.PromptGUI;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.component.components.Label;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * This class handles purchases
 * <p>
 * A purchase can be made by creating an instance of this class,
 * with the name, key, price and player
 * <p>
 * It will automatically open a UI to confirm.
 */
public class Purchase implements Listener {

    static {

        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS purchases (`id` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL" +
                    ", `uuid` VARCHAR(255) NOT NULL" +
                    ", `key` VARCHAR(255) NOT NULL" +
                    ", `price` INT NOT NULL)");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private final String name;
    private final String key;
    private final int price;
    private final MPlayer player;

    private final static SQL sql = Core.instance.sql;


    /**
     * @param name   Name of the item
     * @param key    Purchase key of the item
     * @param price  Price of the item
     * @param player Player that is purchasing this item
     */
    public Purchase(String name, String key, int price, MPlayer player) {

        this.name = name;
        this.key = key;
        this.price = price;
        this.player = player;

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);

        confirm();
    }


    /**
     * This will open the confirm Prompt UI with a confirm button and a cancel button
     */
    private void confirm() {

        new PromptGUI(this.player, "Confirm Purchase") {

            @Override
            protected Map<Integer, Component> content() {
                Map<Integer, Component> componentMap = new HashMap<>();

                //Info

                Label purchaseInfo = new Label(MessageColor.COLOR_HIGHLIGHT + name, Material.BOOK, null);
                purchaseInfo.setLore(new String[]{
                        "",
                        ChatColor.WHITE + "Price: " + ChatColor.GOLD + price
                });

                //Confirm

                Button confirmButton = new Button(MessageColor.COLOR_SUCCESS + "Confirm", Material.GREEN_WOOL, null);
                confirmButton.setLore(new String[]{MessageColor.COLOR_MAIN + "Click to confirm purchase"});
                confirmButton.setOnClick(() -> {

                    if (player.getCoins() >= price) {

                        Coin.removeCoins(player, price);
                        player.getPlayer().sendMessage(MessageColor.COLOR_SUCCESS + "Successfully purchased " + MessageColor.COLOR_MAIN + name);
                        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);

                        updateDatabase();

                    } else {
                        error("Not enough coins!");
                    }

                });

                //Cancel

                Button cancelButton = new Button(MessageColor.COLOR_ERROR + "Cancel", Material.RED_WOOL, null);
                cancelButton.setLore(new String[]{MessageColor.COLOR_MAIN + "Click to cancel purchase"});
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


    /**
     * This will open a Prompt ui with the error message.
     * Called when purchase was unsuccessful
     *
     * @param message The error message to display
     */
    private void error(String message) {

        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 6f);

        new PromptGUI(player, "Purchase Failed") {

            @Override
            protected Map<Integer, Component> content() {
                Map<Integer, Component> componentMap = new HashMap<>();

                Label error = new Label(MessageColor.COLOR_ERROR + "Purchase Failed", Material.RED_WOOL, null);
                error.setLore(new String[]{MessageColor.COLOR_MAIN + message});

                for (int i = 0; i < getInventory().getSize(); i++) {
                    componentMap.put(i, error);
                }

                return componentMap;
            }
        };

    }


    /**
     * This will add the purchase to the database. This will be called
     * if the purchase was successful
     */
    private void updateDatabase() {
        try {

            PreparedStatement st = sql.preparedStatement("INSERT INTO purchases (`uuid`, `key`, `price`) VALUES (?,?,?)");
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


    /**
     * @return The name of this item
     */
    public String getName() {
        return name;
    }


    /**
     * @return The purchase key of this item
     */
    public String getKey() {
        return key;
    }


    /**
     * @return The price of this item
     */
    public int getPrice() {
        return price;
    }


    /**
     * @return The player that is purchasing this item
     */
    public MPlayer getPlayer() {
        return player;
    }


    /**
     * @param player Player to check
     * @param key    Purchase key to look for
     * @return Will return true if the player has purchased this item.
     */
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
