package com.github._8ml.core.module;
/*
Created by @8ML (https://github.com/8ML) on 5/24/2021
*/

import com.github._8ml.core.exceptions.ModuleNotFoundException;
import com.github._8ml.core.Core;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.HashSet;
import java.util.Set;

public abstract class Module {

    private static final Set<Module> modules = new HashSet<>();
    public static Module enabledModule;

    protected Core mainInstance;
    protected String name;

    public Module(String name) {
        this.name = name;
    }

    public void enableModule(Core plugin) {
        mainInstance = plugin;
        onEnable();
    }

    public void disableModule() {
        onDisable();
        enabledModule = null;
    }

    protected abstract void onEnable();
    protected abstract void onDisable();


    public static void registerModule(Module module) {
        modules.add(module);
        Core.instance.getLogger().info("Registered Module " + module.getClass().getSimpleName() + "....");
    }

    public static void setModule(Core plugin, String module) throws ModuleNotFoundException {
        for (Module mod : modules) {
            if (mod.name.equals(module)) {
                enabledModule = mod;
                mod.enableModule(plugin);
            }
        }
        if (enabledModule == null) {
            throw new ModuleNotFoundException("Module " + module + " does not exist!");
        }
    }

    public static Module getEnabledModule() {
        return enabledModule;
    }

    public static Module getModule(Class<?> clazz) {
        for (Module mod : modules) {
            if (clazz.getSimpleName().equals(mod.getClass().getSimpleName())) {
                return mod;
            }
        }
        return null;
    }

}
