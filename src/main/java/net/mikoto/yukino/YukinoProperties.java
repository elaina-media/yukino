package net.mikoto.yukino;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mikoto
 * &#064;date 2023/1/29
 * Create for yukino
 */
@ConfigurationProperties(prefix = "yukino")
public class YukinoProperties {
    String configName = "default";

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }
}
