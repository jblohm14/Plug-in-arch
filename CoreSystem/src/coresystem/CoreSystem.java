package coresystem;

import plugininterface.DevicePlugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class CoreSystem {
    
    // List to hold all dynamically loaded plugins
    private static final List<DevicePlugin> plugins = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Dynamic Plugin Assessment System ===");

        // Load all plugins from the plugins directory
        loadPlugins();

        System.out.println("\nAssessing devices:");

        // Test assessment for various device IDs
        assessDevice("iPhone");
        assessDevice("Galaxy");
        assessDevice("Pixel");
        assessDevice("OnePlus");
        assessDevice("Xiaomi");
        assessDevice("Unknown"); 
    }

    /**
     * Loads plugin JARs from the "plugins" directory.
     * Uses a URLClassLoader and ServiceLoader to dynamically discover implementations
     * of the DevicePlugin interface.
     */
    public static void loadPlugins() {
        File pluginsDir = new File("Plugins");

        // Check if plugins directory exists and is valid
        if (!pluginsDir.exists() || !pluginsDir.isDirectory()) {
            System.out.println("Plugins directory not found. Please create a 'plugins' folder with .jar files.");
            return;
        }

        // Filter for .jar files only
        File[] jarFiles = pluginsDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".jar"));

        // Handle case where no JAR files are found
        if (jarFiles == null || jarFiles.length == 0) {
            System.out.println("No .jar files found in plugins directory.");
            return;
        }

        // Convert JAR files to URLs for class loading
        List<URL> jarUrls = new ArrayList<>();
        for (File jar : jarFiles) {
            try {
                jarUrls.add(jar.toURI().toURL());
                System.out.println("Found plugin JAR: " + jar.getName());
            } catch (MalformedURLException e) {
                System.err.println("Failed to load JAR: " + jar.getName());
                e.printStackTrace();
            }
        }

        // Create a custom class loader that includes all plugin JARs
        // Parent class loader ensures access to core system classes
        URLClassLoader pluginLoader = new URLClassLoader(
                jarUrls.toArray(new URL[0]), CoreSystem.class.getClassLoader());

        // ServiceLoader automatically discovers implementations of DevicePlugin
        // inside the provided class loader (plugin JARs)
        for (URL url : jarUrls) {
            try {
                String jarPath = url.getPath();
                java.util.jar.JarFile jarFile = new java.util.jar.JarFile(jarPath);

                java.util.Enumeration<java.util.jar.JarEntry> entries = jarFile.entries();

                while (entries.hasMoreElements()) {
                    java.util.jar.JarEntry entry = entries.nextElement();

                    if (entry.getName().endsWith(".class")) {
                        String className = entry.getName()
                                .replace("/", ".")
                                .replace(".class", "");

                        Class<?> cls = pluginLoader.loadClass(className);

                        if (DevicePlugin.class.isAssignableFrom(cls) && !cls.isInterface()) {
                            DevicePlugin plugin =
                                    (DevicePlugin) cls.getDeclaredConstructor().newInstance();

                            plugins.add(plugin);
                            System.out.println("Loaded plugin: " + className);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Summary of loaded plugins
        System.out.println(plugins.size() + " plugin(s) loaded successfully.");
    }

    /**
     * Attempts to find a plugin that supports the given device ID
     * and runs its assessment.
     *
     * @param deviceID The identifier of the device to assess
     */
    public static void assessDevice(String deviceID) {
        boolean found = false;

        // Iterate through all loaded plugins
        for (DevicePlugin plugin : plugins) {
            // Check if this plugin supports the given device
            if (plugin.supports(deviceID)) {
                // Run the plugin's assessment logic
                plugin.assess();
                found = true;
                break; // Stop after first match
            }
        }

        // If no plugin matched the device
        if (!found) {
            System.out.println("No matching plugin found for device: " + deviceID);
        }
    }
}