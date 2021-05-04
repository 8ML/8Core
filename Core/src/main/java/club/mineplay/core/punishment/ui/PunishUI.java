package club.mineplay.core.punishment.ui;
/*
Created by Sander on 5/4/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.ui.pages.PunishPage;
import club.mineplay.core.punishment.ui.pages.history.*;
import club.mineplay.core.ui.GUI;

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
