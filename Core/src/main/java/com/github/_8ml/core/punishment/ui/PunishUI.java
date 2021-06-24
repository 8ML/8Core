package com.github._8ml.core.punishment.ui;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.punishment.Punishment;
import com.github._8ml.core.punishment.ui.pages.history.*;
import com.github._8ml.core.ui.GUI;
import com.github._8ml.core.punishment.ui.pages.PunishPage;

public class PunishUI extends GUI {

    private final MPlayer target, executor;
    private final String reason;

    public PunishUI(MPlayer target, MPlayer executor, String reason) {
        super(executor);

        this.target = target;
        this.executor = executor;
        this.reason = reason;

        initialize();
    }

    @Override
    public void init() {
        addPage(new PunishPage(target, executor, reason));
        addPage(new HistoryMenuPage(target));
        addPage(new HistoryPage(target));
        openPage(0);

    }
}
