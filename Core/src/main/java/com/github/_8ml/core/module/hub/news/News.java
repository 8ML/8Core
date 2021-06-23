package com.github._8ml.core.module.hub.news;
/*
Created by @8ML (https://github.com/8ML) on June 23 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.utils.BossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class News implements Listener {

    static {

        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS news (`id` INT PRIMARY KEY AUTO_INCREMENT" +
                    ", `content` TEXT)");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private SQL sql = Core.instance.sql;

    public News() {

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);

    }

    public void addNews(String news) {


        try {

            PreparedStatement st = sql.preparedStatement("INSERT INTO news (`content`) VALUES (?)");
            st.setString(1, news);

            try {
                st.execute();
            } finally {
                sql.closeConnection(st);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeNews(Object news) {

        try {

            String where = (news instanceof Integer) ? "id"
                    : "content";

            PreparedStatement st = sql.preparedStatement("DELETE FROM news WHERE `" + where + "`=?");
            if (news instanceof Integer) st.setInt(1, (int) news);
            else st.setString(1, (String) news);

            try {
                st.execute();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<String> getNews() {
        try {

            List<String> news = new ArrayList<>();

            PreparedStatement st = sql.preparedStatement("SELECT * FROM news");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                news.add(rs.getString("content"));

            }

            sql.closeConnection(st);

            return news;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public int getID(String news) {
        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM news WHERE `content`=?");
            st.setString(1, news);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                return rs.getInt("id");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private int counter = 0;
    private int newsIndex = 0;

    @EventHandler
    public void onUpdate(UpdateEvent e) {

        if (e.getType().equals(UpdateEvent.UpdateType.SECONDS)) {

            if (counter >= 10) {

                List<String> news = getNews();
                BossBar.sendMessage(news.get(newsIndex), counter % 10 == 0 ? BarColor.WHITE : BarColor.PURPLE);

                counter = 0;
                newsIndex = newsIndex == news.size() - 1 ? 0 : newsIndex + 1;
            }

            counter++;
        }

    }

}
