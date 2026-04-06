package pluginimpl;

import plugininterface.DevicePlugin;

public class GalaxyPlugin implements DevicePlugin {
    @Override
    public boolean supports(String deviceName) { return "Galaxy".equalsIgnoreCase(deviceName); }
    @Override
    public void assess() {
        System.out.println("Assessing Galaxy: Samsung flagship with One UI, powerful processor, versatile camera setup.");
    }
}