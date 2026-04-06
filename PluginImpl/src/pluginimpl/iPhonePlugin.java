package pluginimpl;
import plugininterface.DevicePlugin;

public class iPhonePlugin implements DevicePlugin {
    @Override
    public boolean supports(String deviceName) {
        return "iPhone".equalsIgnoreCase(deviceName);
    }

    @Override
    public void assess() {
        System.out.println("Assessing iPhone: High-end performance, excellent camera system, seamless iOS ecosystem integration.");
    }
}
