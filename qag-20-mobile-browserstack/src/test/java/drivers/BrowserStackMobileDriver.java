package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.BrowserstackConfig;
import config.DeviceConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.annotation.Nonnull;

public class BrowserStackMobileDriver implements WebDriverProvider {

    BrowserstackConfig browserstackConfig = ConfigFactory.create(BrowserstackConfig.class, System.getProperties());
    DeviceConfig deviceConfig = ConfigFactory.create(DeviceConfig.class, System.getProperties());

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull DesiredCapabilities capabilities) {

        capabilities.setCapability("browserstack.user", browserstackConfig.userName());
        capabilities.setCapability("browserstack.key", browserstackConfig.userPassword());
        capabilities.setCapability("app", browserstackConfig.app());
        capabilities.setCapability("device", deviceConfig.device());
        capabilities.setCapability("os_version", deviceConfig.osVersion());
        capabilities.setCapability("project", browserstackConfig.project());
        capabilities.setCapability("build", browserstackConfig.build());
        capabilities.setCapability("name", browserstackConfig.name());

        if ("ios".equals(deviceConfig.osVersion())) {
            return new IOSDriver(browserstackConfig.url(), capabilities);
        }
        return new AndroidDriver(browserstackConfig.url(), capabilities);
    }
}
