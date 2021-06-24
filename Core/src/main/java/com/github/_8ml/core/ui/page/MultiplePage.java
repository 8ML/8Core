package com.github._8ml.core.ui.page;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.utils.DeveloperMode;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MultiplePage extends Page {

    private final static int LAST_SLOT = 34;

    private int currentPageIndex = 0;
    private final Map<Integer, Map<Integer, Component>> pages = new HashMap<>();

    private boolean initialized = false;

    /**
     * @param title The title of the page
     * @param frame Set to true if you want a frame
     */
    public MultiplePage(String title, boolean frame) {
        super(title, 54, frame);
    }

    protected abstract List<Component> onOpenMultiple();

    @Override
    protected void onOpen() {

        if (!initialized) {
            initialized = true;

            List<Component> componentList = onOpenMultiple();
            Map<Integer, Component> currentPageBuilderList = new HashMap<>();
            int pageBuilderIndex = 0;

            if (componentList.isEmpty()) return;

            int slot = isFrame() ? 10 : 0;

            for (Component component : componentList) {

                if (slot == LAST_SLOT + 1) {

                    pages.put(pageBuilderIndex, currentPageBuilderList);

                    pageBuilderIndex++;
                    slot = isFrame() ? 10 : 0;

                    currentPageBuilderList = new HashMap<>();

                } else {
                    currentPageBuilderList.put(slot, component);
                    if (componentList.indexOf(component) == componentList.size() - 1)
                        pages.put(pageBuilderIndex, currentPageBuilderList);

                    slot = isFrame() && getFrameSlots().contains(slot + 1) ? slot + 2 : slot + 1;
                }

            }
        }

        if (!this.pages.containsKey(currentPageIndex)) return;
        Map<Integer, Component> page = this.pages.get(currentPageIndex);

        for (int key : page.keySet()) {

            addComponent(page.get(key), key);

        }

        Button backButton = new Button(MessageColor.COLOR_MAIN + "Page " + currentPageIndex + 1, Material.ARROW, getParent());
        Button nextButton = new Button(MessageColor.COLOR_MAIN + "Page" + currentPageIndex + 2, Material.ARROW, getParent());

        backButton.setOnClick(() -> {

            currentPageIndex--;
            refresh();

        });

        nextButton.setOnClick(() -> {

            currentPageIndex++;
            refresh();

        });

        if (pages.size() != 1) {
            if (currentPageIndex > 1) addComponent(backButton, 39);
            if (currentPageIndex < pages.size() - 1) addComponent(nextButton, 41);
        }

    }
}
