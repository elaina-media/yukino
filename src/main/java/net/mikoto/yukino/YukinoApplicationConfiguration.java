package net.mikoto.yukino;

import net.mikoto.yukino.manager.YukinoConfigManager;
import net.mikoto.yukino.manager.YukinoJsonManager;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.model.Config;
import net.mikoto.yukino.parser.ParserHandle;
import net.mikoto.yukino.parser.handler.impl.JsonFileToObjectParserHandler;
import net.mikoto.yukino.parser.handler.impl.ModelFileParserHandler;
import net.mikoto.yukino.service.YukinoDaoService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author mikoto
 * &#064;date 2023/1/4
 * Create for yukino
 */
@Configuration
@EnableConfigurationProperties({YukinoProperties.class})
public class YukinoApplicationConfiguration {
    /**
     * Create a default config
     *
     * @param yukinoJsonManager The yukino json manager
     * @param yukinoModelManager The yukino model manager
     * @return The config object
     */
    private static @NotNull Config getConfig(YukinoJsonManager yukinoJsonManager, YukinoModelManager yukinoModelManager) {
        Config config = new Config();
        config.setParserHandles(
                new ParserHandle[]{
                        new JsonFileToObjectParserHandler(yukinoJsonManager),
                        new ModelFileParserHandler(yukinoModelManager)
                });
        try {
            config.setSqlSessionFactory(
                    new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return config;
    }
    @Bean
    public YukinoModelManager yukinoModelManager() {
        return new YukinoModelManager();
    }

    @Bean
    public YukinoJsonManager yukinoJsonManager() {
        return new YukinoJsonManager();
    }

    @Bean
    public YukinoConfigManager yukinoConfigManager(YukinoJsonManager yukinoJsonManager, YukinoModelManager yukinoModelManager) {
        YukinoConfigManager yukinoConfigManager = new YukinoConfigManager();
        yukinoConfigManager.put("default", getConfig(yukinoJsonManager, yukinoModelManager));
        return yukinoConfigManager;
    }
}
