package net.mikoto.yukino.test;

import net.mikoto.yukino.YukinoApplication;
import net.mikoto.yukino.YukinoApplicationConfiguration;
import net.mikoto.yukino.manager.YukinoJsonManager;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.model.Config;
import net.mikoto.yukino.parser.ParserHandle;
import net.mikoto.yukino.parser.handler.impl.ModelFileParserHandler;
import net.mikoto.yukino.parser.handler.impl.JsonFileToObjectParserHandler;
import net.mikoto.yukino.service.YukinoDaoService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
@SuppressWarnings("NewClassNamingConvention")
public class YukinoTestApplication {
    private static final ApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(YukinoApplicationConfiguration.class);
    private static final YukinoApplication yukinoApplication = applicationContext.getBean(YukinoApplication.class);

    // init config
    static {
        try {
            yukinoApplication.getYukinoConfigManager().put("default", getConfig());
            yukinoApplication.doScan("default");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull Config getConfig() throws IOException {
        Config config = new Config();
        config.setParserHandles(
                new ParserHandle[]{
                        new JsonFileToObjectParserHandler(yukinoApplication.getYukinoJsonManager()),
                        new ModelFileParserHandler(yukinoApplication.getYukinoModelManager())
                });
        config.setSqlSessionFactory(
                new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"))
        );
        return config;
    }

    @Test
    public void parserTest() {
        YukinoJsonManager yukinoJsonManager = yukinoApplication.getYukinoJsonManager();
        YukinoModelManager yukinoModelManager = yukinoApplication.getYukinoModelManager();

        Assertions.assertEquals(5, yukinoJsonManager.size());
        Assertions.assertNotNull(yukinoJsonManager.get("test.json"));
        Assertions.assertNotNull(yukinoJsonManager.get("artwork.yukino.model.json"));
        Assertions.assertNotNull(yukinoJsonManager.get("artwork_index_author_name.yukino.model.json"));
        Assertions.assertNotNull(yukinoJsonManager.get("artwork_index_tag.yukino.model.json"));
        Assertions.assertNotNull(yukinoJsonManager.get("artwork_index_title.yukino.model.json"));

        Assertions.assertEquals(4, yukinoModelManager.size());
        Assertions.assertNotNull(yukinoModelManager.get("Artwork"));
        Assertions.assertNotNull(yukinoModelManager.get("Artwork_Index_author_name"));
        Assertions.assertNotNull(yukinoModelManager.get("Artwork_Index_tag"));
        Assertions.assertNotNull(yukinoModelManager.get("Artwork_Index_title"));
    }

    @Test
    public void searchTest() {
        YukinoDaoService yukinoDaoService = yukinoApplication.getYukinoDaoService();

        yukinoDaoService.search("default", "select ");
    }
}
