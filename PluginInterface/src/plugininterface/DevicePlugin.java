package plugininterface;

public interface DevicePlugin {

    boolean supports(String deviceName);
    
    void assess();
}
