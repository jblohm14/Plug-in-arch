package pluginimpl;

import plugininterface.DevicePlugin;

public class XiaomiPlugin implements DevicePlugin {
    @Override
    public boolean supports(String deviceName) { 
        return "Xiaomi".equalsIgnoreCase(deviceName); 
    }
    
    @Override
    public void assess() {
        System.out.println("Assessing Xiaomi: HyperOS features, powerful hardware at value price, great battery life.");
    }
}