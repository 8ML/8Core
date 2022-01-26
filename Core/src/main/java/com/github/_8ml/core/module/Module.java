/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module;
/*
Created by @8ML (https://github.com/8ML) on 5/24/2021
*/

import com.github._8ml.core.config.ConfigurationReload;
import com.github._8ml.core.exceptions.ModuleNotFoundException;
import com.github._8ml.core.Core;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.HashSet;
import java.util.Set;


/**
 * This is the main module class.
 * It is abstract, so it has to be extended by other classes.
 * <p>
 * This is "mini plugins" inside the main core plugin and will
 * provide additional functionality when enabled
 * <p>
 * To create a module, create a class and extend this class
 * The name specified in the main constructor will be used
 * to enable it through the server.yml
 * <p>
 * There has to be a module specified for the plugin to start.
 * If module specified in server.yml does not exist, it will throw
 * a module not found exception followed by a shutdown.
 */
public abstract class Module implements ConfigurationReload {

    private static final Set<Module> modules = new HashSet<>();
    public static Module enabledModule;

    protected Core mainInstance;
    protected String name;


    /**
     * @param name This will be used when enabling this module
     *             through the server.yml
     */
    public Module(String name) {
        this.name = name;
    }

    public void enableModule(Core plugin) {
        ConfigurationReload.registerClass(this);
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

    public static Module getModule(Class<? extends Module> clazz) throws ModuleNotFoundException {
        for (Module mod : modules) {
            if (clazz.getSimpleName().equals(mod.getClass().getSimpleName())) {
                return mod;
            }
        }
        throw new ModuleNotFoundException("Module class " + clazz.getName() + " Does not exist or is not registered");
    }

}
