/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.eightcore.discordcommapi.packet.listener;
/*
Created by @8ML (https://github.com/8ML) on August 20 2021
*/

import com.github._8ml.eightcore.discordcommapi.packet.PacketType;

import java.util.ArrayList;
import java.util.List;

public interface PacketListener {

    List<PacketListener> classes = new ArrayList<>();
    static void register(PacketListener listener) {
        classes.add(listener);
    }

    static void call(PacketType type, Object[] data) {
        for (PacketListener listener : classes) {
            listener.event(type, data);
        }
    }

    void event(PacketType type, Object[] data);

}
