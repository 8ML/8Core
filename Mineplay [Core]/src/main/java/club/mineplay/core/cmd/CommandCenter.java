package club.mineplay.core.cmd;
/*
Created by Sander on 4/24/2021
*/

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CommandCenter {

    public static List<CMD> commandList = new ArrayList<>();

    public static void registerCommand(CMD cmd, JavaPlugin plugin) {
        cmd.setMain(plugin).registerMe();
        plugin.getLogger().info("[CMD] Registered Command: " + cmd.getLabel());
    }

}
