package net.mikoto.yukino.test;

import net.mikoto.yukino.YukinoApplication;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.model.Config;
import net.mikoto.yukino.parser.ParserHandler;
import net.mikoto.yukino.parser.impl.JsonFileModelParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
public class YukinoTestApplication {
    private static final YukinoModelManager yukinoModelManager = new YukinoModelManager();

    @Test
    public void applicationTest() throws IOException {
        Config config = new Config();
        config.setParserHandlers(new ParserHandler[]{new JsonFileModelParser(yukinoModelManager)});
        YukinoApplication yukinoApplication = new YukinoApplication(config);
    }
}
