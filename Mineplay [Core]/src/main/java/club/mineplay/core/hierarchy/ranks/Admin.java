package club.mineplay.core.hierarchy.ranks;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.hierarchy.Rank;
import org.bukkit.Color;

public class Admin extends Rank {

    public Admin() {
        super("Administrator", "Admin", 90.0D);
        setColor(Color.fromRGB(180, 0, 0));
    }

    @Override
    public void onRegister() {

    }
}
