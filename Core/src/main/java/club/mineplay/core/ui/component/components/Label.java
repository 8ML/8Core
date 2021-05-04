package club.mineplay.core.ui.component.components;
/*
Created by Sander on 5/4/2021
*/

import club.mineplay.core.ui.GUI;
import club.mineplay.core.ui.component.Component;
import org.bukkit.Material;

public class Label extends Component {

    public Label(Material material, GUI parent) {
        super(material, parent);
    }

    public Label(String title, Material material, GUI parent) {
        super(title, material, parent);
    }

}
