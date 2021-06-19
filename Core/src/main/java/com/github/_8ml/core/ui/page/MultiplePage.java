package com.github._8ml.core.ui.page;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.utils.DeveloperMode;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MultiplePage extends Page {

    private final int LAST_SLOT = 34;
    private int currentPageIndex = 0;
    private final Map<Integer, Map<Integer, Component>> pages = new HashMap<>();

    /**
     * @param title The title of the page
     * @param size  The size of the page (9, 18, 27, 36, 45, 54)
     * @param frame Set to true if you want a frame
     */
    public MultiplePage(String title, int size, boolean frame) {
        super(title, size, frame);

        List<Component> componentList = onOpenMultiple();
        DeveloperMode.log(componentList.size() + "");
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

    protected abstract List<Component> onOpenMultiple();

    @Override
    protected void onOpen() {

        DeveloperMode.log("Yes");
        DeveloperMode.log(pages.size() + "");
        DeveloperMode.log(currentPageIndex + "");
        DeveloperMode.log(pages.keySet() + "");

        if (!this.pages.containsKey(currentPageIndex)) return;
        Map<Integer, Component> page = this.pages.get(currentPageIndex);

        DeveloperMode.log("Passed");
        DeveloperMode.log(page.keySet() + "   " + page.get(0));

        for (int key : page.keySet()) {

            addComponent(page.get(key), key);

        }

        Button backButton = new Button(ChatColor.GRAY + "Page " + currentPageIndex + 1, Material.ARROW, getParent());
        Button nextButton = new Button(ChatColor.GRAY + "Page" + currentPageIndex + 2, Material.ARROW, getParent());

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
