package club.mineplay.core.config;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.storage.file.PluginFile;
import org.bukkit.Color;

import java.util.Objects;

public class MessageColor {

    private static final PluginFile file = Main.instance.messagesYML;

    public static Color COLOR_MAIN = parseColor(Objects.requireNonNull(file.getString("main")));
    public static Color COLOR_HIGHLIGHT = parseColor(Objects.requireNonNull(file.getString("highlight")));
    public static Color COLOR_ERROR = parseColor(Objects.requireNonNull(file.getString("error")));
    public static Color COLOR_SUCCESS = parseColor(Objects.requireNonNull(file.getString("success")));

    private static Color parseColor(String configValue) {
        String[] rgbSTR = configValue.split(",");
        int[] rgb = new int[]{Integer.parseInt(rgbSTR[0]),
                Integer.parseInt(rgbSTR[1]), Integer.parseInt(rgbSTR[2])};

        return Color.fromRGB(rgb[0], rgb[1], rgb[2]);
    }

}
