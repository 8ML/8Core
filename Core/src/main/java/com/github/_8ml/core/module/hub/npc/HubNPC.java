package com.github._8ml.core.module.hub.npc;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.npc.NPC;
import com.github._8ml.core.utils.DeveloperMode;
import org.bukkit.Location;

public class HubNPC {

    public void addNPC(String gameKey, String gameDisplay, String uuid, Location location) {

        NPC npc = new NPC(gameKey, gameDisplay, uuid, () -> {
            DeveloperMode.log("Clicked " + gameKey);
            //Will send player to game when Game manager is done
        });

        npc.spawn(location);

    }

}
