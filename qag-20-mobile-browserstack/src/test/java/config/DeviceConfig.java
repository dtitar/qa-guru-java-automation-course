package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:config/devices/${os}/${device}.properties",
        "classpath:config/devices/${os}/default.properties",
        "classpath:config/devices/android/default.properties"
})
public interface DeviceConfig extends Config {
    @Key("device")
    String device();
    @Key("os.version")
    String osVersion();
}