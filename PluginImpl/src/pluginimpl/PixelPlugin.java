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