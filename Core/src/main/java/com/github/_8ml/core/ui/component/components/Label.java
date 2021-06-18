package com.github._8ml.core.ui.component.components;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.ui.GUI;
import com.github._8ml.core.ui.component.Component;
import org.bukkit.Material;

public class Label extends Component {

    public Label(Material material, GUI parent) {
        super(material, parent);
    }

    public Label(String title, Material material, GUI parent) {
        super(title, material, parent);
    }

}
