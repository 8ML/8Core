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
