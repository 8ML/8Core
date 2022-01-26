/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.eightcore.discord.storage.file;
/*
Created by @8ML (https://github.com/8ML) on August 31 2021
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ConfigFile {

    private final String fileName;
    private final String path;

    private Properties properties;

    public ConfigFile(String fileName) {
        this.fileName = fileName;
        this.path = Objects.requireNonNull(this.getClass().getResource(fileName)).toExternalForm();

        try {

            File file = new File(this.path);
            boolean created = file.createNewFile();

            System.out.println("Initialized configuration file " + fileName + (created ? " | FILE CREATED" : ""));

            this.properties = new Properties();
            this.properties.load(new FileInputStream(this.path));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {

            this.properties.store(new FileOutputStream(this.path), null);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String key, String value) {
        this.properties.setProperty(key, value);
        save();
    }

    public String get(String key) {
        return this.properties.getProperty(key, "");
    }

}
