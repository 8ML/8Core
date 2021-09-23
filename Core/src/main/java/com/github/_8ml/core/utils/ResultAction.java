package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on September 14 2021
*/

import java.util.HashSet;
import java.util.Set;

public class ResultAction {

    public interface Action {

        void run(Object data, Object[] dataArray);

    }

    private static final Set<ResultAction> actions = new HashSet<>();

    private final String id;
    private final Action action;
    private final Object data;

    public ResultAction(String id, Action action, Object data) {
        this.id = id;
        this.action = action;
        this.data = data;
        actions.add(this);
    }

    public ResultAction(String id, Action action) {
        this.id = id;
        this.action = action;
        this.data = null;
        actions.add(this);
    }

    public String getId() {
        return id;
    }

    public Action getAction() {
        return action;
    }

    public static void call(String id, Object[] dataArray) {
        for (ResultAction action : actions) {

            if (!action.getId().equals(id)) continue;

            action.getAction().run(action.data, dataArray);

        }
    }

}
