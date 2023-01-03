package net.mikoto.yukino;

import net.mikoto.yukino.manager.YukinoConfigManager;
import net.mikoto.yukino.manager.YukinoJsonManager;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.service.YukinoDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mikoto
 * @date 2023/1/4
 * Create for yukino
 */
@Configuration
public class YukinoApplicationConfiguration {
    @Bean
    public YukinoModelManager yukinoModelManager() {
        return new YukinoModelManager();
    }

    @Bean
    public YukinoJsonManager yukinoJsonManager() {
        return new YukinoJsonManager();
    }

    @Bean
    public YukinoConfigManager yukinoConfigManager() {
        return new YukinoConfigManager();
    }

    @Bean
    public YukinoDataService yukinoDataService(YukinoConfigManager yukinoConfigManager,
                                               YukinoModelManager yukinoModelManager) {
        return new YukinoDataService(yukinoConfigManager, yukinoModelManager);
    }

    @Bean
    public YukinoApplication yukinoApplication(YukinoModelManager yukinoModelManager,
                                               YukinoJsonManager yukinoJsonManager,
                                               YukinoConfigManager yukinoConfigManager,
                                               YukinoDataService yukinoDataService) {
        return new YukinoApplication(yukinoModelManager, yukinoJsonManager, yukinoConfigManager, yukinoDataService);
    }
}
