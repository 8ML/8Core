package xyz.dev_8.core.ui.component.components;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import xyz.dev_8.core.ui.GUI;
import xyz.dev_8.core.ui.component.Component;
import org.bukkit.Material;

public class Label extends Component {

    public Label(Material material, GUI parent) {
        super(material, parent);
    }

    public Label(String title, Material material, GUI parent) {
        super(title, material, parent);
    }

}
