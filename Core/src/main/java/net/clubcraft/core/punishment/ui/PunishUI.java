package net.clubcraft.core.punishment.ui;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.punishment.ui.pages.PunishPage;
import net.clubcraft.core.punishment.ui.pages.history.*;
import net.clubcraft.core.ui.GUI;

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
        addPage(new HistoryBanPage(target));
        addPage(new HistoryMutePage(target));
        addPage(new HistoryWarnPage(target));
        addPage(new HistoryKickPage(target));
        openPage(0);

    }
}
