package club.mineplay.core.hierarchy.ranks;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.hierarchy.Rank;

import java.awt.*;

public class Admin extends Rank {

    public Admin() {
        super("Administrator", "Admin", 90.0D);
        setColor(new Color(238, 77, 77));
        setDescription("Admin's are the people that manages the server and makes sure\n" +
                "everything goes as it should, they consist of developers, quality assurance,\n" +
                "build team leaders and more");
    }

    @Override
    public void onRegister() {

    }
}
