package helpers;

import com.codeborne.selenide.Configuration;
import config.DriverConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

public class DriverHelper {
    static DriverConfig config = ConfigFactory.create(DriverConfig.class);

    public static void configureDriver() {

        addListener("AllureSelenide", new AllureSelenide());
        Configuration.browser = config.browser();
        Configuration.browserVersion = config.browserVersion();
        Configuration.timeout = 10000;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (isRemote()) {
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            Configuration.remote = config.driverUrl();
        }

        Configuration.browserCapabilities = capabilities;
    }

    public static boolean isRemote() {
        return !config.driverUrl().equals("");
    }
}
