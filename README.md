# Plug-in-arch

## 1. CoreSystem.java
```
// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package coresystem;

import java.util.HashMap;
import java.util.Map;
import plugininterface.DevicePlugin;

public class CoreSystem {
   private static final Map<String, String> pluginRegistry = new HashMap();

   static {
      pluginRegistry.put("iPhone", "pluginimpl.iPhonePlugin");
      pluginRegistry.put("Galaxy", "pluginimpl.GalaxyPlugin");
   }

   public CoreSystem() {
   }

   public static void main(String[] args) {
      assessDevice("iPhone");
      assessDevice("Galaxy");
      assessDevice("Unknown");
   }

   public static void assessDevice(String deviceID) {
      try {
         String pluginClassName = (String)pluginRegistry.get(deviceID);
         if (pluginClassName == null) {
            System.out.println("No plugin found for device: " + deviceID);
            return;
         }

         Class<?> pluginClass = Class.forName(pluginClassName);
         DevicePlugin plugin = (DevicePlugin)pluginClass.getDeclaredConstructor().newInstance();
         plugin.assess();
      } catch (ClassNotFoundException e) {
         System.err.println("Plugin class not found for device: " + deviceID);
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }
}

```
## 2. The source code of one Plugin.
```
package pluginimpl;

import plugininterface.DevicePlugin;

/**
 * Plugin implementation for handling Google Pixel devices.
 * This class is discovered at runtime via ServiceLoader.
 */
public class PixelPlugin implements DevicePlugin {

    /**
     * Determines whether this plugin supports the given device name.
     *
     * @param deviceName The name/ID of the device to check
     * @return true if this plugin supports the device, false otherwise
     */
    @Override
    public boolean supports(String deviceName) { 
        // Case-insensitive comparison to match "Pixel"
        return "Pixel".equalsIgnoreCase(deviceName); 
    }

    /**
     * Performs the assessment logic for a Google Pixel device.
     * This method is called when the CoreSystem finds a matching plugin.
     */
    @Override
    public void assess() {
        // Output assessment details for the Pixel device
        System.out.println("Assessing Google Pixel: Clean Android, Tensor G4 chip, exceptional AI features, 7 years of updates.");
    }
}
```

## 3. Screenshots of the plugins folder
```
javac -cp PluginInterface/bin -d PluginImpl/bin PluginImpl/src/pluginimpl/*.java
```
![screenshot1](Docs/Screenshot%202026-04-06%20103713.png)

## 4. Screenshots of the CoreSystem running and displaying the results
```
java -cp CoreSystem/bin:PluginInterface/bin coresystem.CoreSystem
```
![screenshot2](Docs/Screenshot%202026-04-06%20104633.png)

## 5. Documentation of LLM Usage
Exact prompts I used with Grok:

"Java dynamic plugin loading from folder of JAR files using URLClassLoader and ServiceLoader without any hardcoded class names"
"Example of Java DevicePlugin interface with supports() and assess() for plugin architecture"

LLM responses summary:

Provided the exact pattern for URLClassLoader + ServiceLoader.load(...) with multiple JAR URLs.
Confirmed META-INF/services file format and that core has zero compile-time dependency on impl classes.

How I used the responses:

Copied/adapted the classloader + ServiceLoader code directly into loadPlugins() method (lines 28-55 of CoreSystem.java).
Used the example to confirm supports() + assess() pattern, then implemented it in all 5 plugins and updated the interface.
No full assignment was pasted; only function-level lookup for the loading mechanism.Documentation of LLM Usage

## 6. Screenshots of Java Build Path
```
javac -verbose -d CoreSystem/bin CoreSystem/src/coresystem/CoreSystem.java
```
![screenshot3](Docs/Screenshot%202026-04-06%20105328.png)

## 7. Screenshots of Run Configurations dependencies.
![sceenshot4](Docs/Screenshot%202026-04-06%20105522.png)