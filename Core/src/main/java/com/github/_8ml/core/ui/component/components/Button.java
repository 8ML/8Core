package com.github._8ml.core.ui.component.components;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.ui.GUI;
import com.github._8ml.core.ui.component.Component;
import org.bukkit.Material;

public class Button extends Component {

    private Runnable event;

    public Button(Material material, GUI parent) {
        super(material, parent);
    }

    public Button(String label, Material material, GUI parent) {
        super(label, material, parent);
    }

    public void setOnClick(Runnable r) {
        this.event = r;
    }

    public Runnable getEventRunnable() {
        return this.event;
    }

}
