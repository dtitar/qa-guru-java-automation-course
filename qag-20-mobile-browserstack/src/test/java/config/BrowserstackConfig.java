package config;

import org.aeonbits.owner.Config;

import java.net.URL;

public interface BrowserstackConfig extends Config {

    @Key("browserstack.hub.url")
    URL url();

    @Key("browserstack.api.url")
    String apiUrl();

    @Key("browserstack.user.name")
    String userName();

    @Key("browserstack.user.password")
    String userPassword();

    @Key("browserstack.app")
    String app();

    @Key("browserstack.project")
    String project();

    @Key("browserstack.build")
    String build();

    @Key("browserstack.name")
    String name();

}