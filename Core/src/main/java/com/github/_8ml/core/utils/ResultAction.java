/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
