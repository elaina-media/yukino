package net.mikoto.yukino.autoconfigurer;

import net.mikoto.yukino.YukinoApplication;
import net.mikoto.yukino.YukinoApplicationConfiguration;
import net.mikoto.yukino.YukinoProperties;
import net.mikoto.yukino.manager.YukinoConfigManager;
import net.mikoto.yukino.manager.YukinoJsonManager;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.service.YukinoDaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author mikoto
 * &#064;date 2023/1/29
 * Create for yukino
 */
@Configuration
@Import({YukinoApplicationConfiguration.class})
public class YukinoAutoconfiguration {
    @Bean
    public YukinoDaoService yukinoDataService(YukinoProperties yukinoProperties,
                                              YukinoConfigManager yukinoConfigManager,
                                              YukinoModelManager yukinoModelManager) {
        return new YukinoDaoService(yukinoProperties, yukinoConfigManager, yukinoModelManager);
    }

    @Bean
    public YukinoApplication yukinoApplication(YukinoModelManager yukinoModelManager,
                                               YukinoJsonManager yukinoJsonManager,
                                               YukinoConfigManager yukinoConfigManager,
                                               YukinoDaoService yukinoDaoService) {
        return new YukinoApplication(yukinoModelManager, yukinoJsonManager, yukinoConfigManager, yukinoDaoService);
    }
}
