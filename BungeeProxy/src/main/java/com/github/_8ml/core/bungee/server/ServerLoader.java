/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.bungee.server;
/*
Created by @8ML (https://github.com/8ML) on October 25 2021
*/

import com.github._8ml.core.bungee.Main;
import com.github._8ml.core.bungee.exceptions.ServerNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ServerLoader {

    private static final String DATA_FOLDER = Main.instance.getDataFolder().getAbsolutePath();
    private static final String SERVER_FOLDER =
            String.join(File.separator, Arrays.copyOfRange(DATA_FOLDER.split(File.separator), 0,
                    DATA_FOLDER.split(File.separator).length - 2))
                    + File.separator + "Servers";


    public Collection<File> getServers() {

        Collection<File> servers = new ArrayList<>();

        File dir = new File(SERVER_FOLDER);
        if (dir.listFiles() == null) return Collections.emptyList();
        for (File s : Objects.requireNonNull(dir.listFiles())) {
            if (!dir.isDirectory()) continue;
            if (s.listFiles() == null ) continue;
            if (!Arrays.asList(Objects.requireNonNull(s.list())).contains("server.properties")) continue;
            servers.add(s);
        }

        return servers;

    }

    public String getServerProperty(String server, String key) throws ServerNotFoundException {

        boolean exists = false;

        for (File s : getServers()) {
            if (!s.getName().equals(server)) continue;
            try {
                FileInputStream stream = new FileInputStream(s.getAbsolutePath() + File.separator + "server.properties");

                Properties properties = new Properties();
                properties.load(stream);

                return properties.getProperty(key);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        throw new ServerNotFoundException("Server '" + server + "' could not be found");

    }

}
