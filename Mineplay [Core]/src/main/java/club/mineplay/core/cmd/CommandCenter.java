package club.mineplay.core.cmd;
/*
Created by Sander on 4/24/2021
*/

import java.util.ArrayList;
import java.util.List;

public class CommandCenter {

    public static List<CMD> commandList = new ArrayList<>();

    public static void registerCommand(CMD cmd) {
        commandList.add(cmd);
        cmd.registerMe();
    }

}
