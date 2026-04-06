package pluginimpl;

import plugininterface.DevicePlugin;

public class OnePlusPlugin implements DevicePlugin {
    @Override
    public boolean supports(String deviceName) {
        return "OnePlus".equalsIgnoreCase(deviceName); 
    }
    
    @Override
    public void assess() {
        System.out.println("Assessing OnePlus: OxygenOS smoothness, flagship Snapdragon chip, premium build quality.");
    }
}