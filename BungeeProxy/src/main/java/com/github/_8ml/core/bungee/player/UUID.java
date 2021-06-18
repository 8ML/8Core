package com.github._8ml.core.bungee.player;
/*
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;

public class UUID {

    public static String getUUID(String playerName) {

        try {
            String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;

            String UUIDJson = IOUtils.toString(new URL(url));

            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);

            String uuid = UUIDObject.get("id").toString();
            return uuid;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getName(String uuid) {
        String url = "https://api.mojang.com/user/profiles/"+uuid.replace("-", "")+"/names";
        try {
            @SuppressWarnings("deprecation")
            String nameJson = IOUtils.toString(new URL(url));
            JSONArray nameValue = (JSONArray) JSONValue.parseWithException(nameJson);
            String playerSlot = nameValue.get(nameValue.size()-1).toString();
            JSONObject nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);
            return nameObject.get("name").toString();
        } catch (IOException | ParseException e) {
            return "error";
        }
    }

    public static String getRawUUID(ProxiedPlayer player) {
        String rUUID = player.getUniqueId().toString().replaceAll("-", "");
        return rUUID;
    }

}

