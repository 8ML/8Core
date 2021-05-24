package xyz.dev_8.core.module;
/*
Created by @8ML (https://github.com/8ML) on 5/24/2021
*/

import xyz.dev_8.core.Core;
import xyz.dev_8.core.exceptions.ModuleNotFoundException;

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
                mod.enableModule(plugin);
                enabledModule = mod;
            }
        }
        if (enabledModule == null) {
            throw new ModuleNotFoundException("Module " + module + " does not exist!");
        }
    }

    public static Module getEnabledModule() {
        return enabledModule;
    }

}
