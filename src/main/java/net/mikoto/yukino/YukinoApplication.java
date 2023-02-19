package net.mikoto.yukino;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.mikoto.yukino.manager.YukinoConfigManager;
import net.mikoto.yukino.manager.YukinoJsonManager;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.model.YukinoConfig;
import net.mikoto.yukino.parser.ParserHandle;
import net.mikoto.yukino.parser.handler.impl.JsonFileToObjectParserHandler;
import net.mikoto.yukino.parser.handler.impl.ModelFileParserHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static net.mikoto.yukino.util.FileUtil.createDir;

/**
 * @author mikoto
 * &#064;date 2022/12/11
 * Create for yukino
 */
@Log4j2
@Getter
@Setter
public class YukinoApplication {
    private ParserHandle<?, ?> parserHandle;
    private final YukinoConfig defaultConfig;
    private final YukinoModelManager yukinoModelManager;
    private final YukinoJsonManager yukinoJsonManager;

    public YukinoApplication(YukinoModelManager yukinoModelManager,
                             YukinoJsonManager yukinoJsonManager,
                             YukinoConfig defaultConfig) {
        this.yukinoModelManager = yukinoModelManager;
        this.yukinoJsonManager = yukinoJsonManager;
        this.defaultConfig = defaultConfig;
        log.info("[Yukino] Started");
    }

    public YukinoApplication(YukinoModelManager yukinoModelManager,
                             YukinoJsonManager yukinoJsonManager) {
        this.yukinoModelManager = yukinoModelManager;
        this.yukinoJsonManager = yukinoJsonManager;
        this.defaultConfig = new YukinoConfig();
        defaultConfig.setParserHandles(
                new ParserHandle[]{
                        new JsonFileToObjectParserHandler(yukinoJsonManager),
                        new ModelFileParserHandler(yukinoModelManager)
                }
        );
        log.info("[Yukino] Started");
    }

    public void doScan() {
        doScan(defaultConfig);
    }

    public void doScan(@NotNull YukinoConfig yukinoConfig) {
        // Do scan and parser
        if (yukinoConfig.getParserHandles().length > 0) {
            parserHandle = yukinoConfig.getParserHandles()[0];
            ParserHandle<?, ?> lastHandler = parserHandle;
            for (int i = 1; i < yukinoConfig.getParserHandles().length; i++) {
                lastHandler = lastHandler.setNext(yukinoConfig.getParserHandles()[i]);
            }

            // To make sure the dir is existed.
            createDir(yukinoConfig.getModelsPath());
            File configDir = new File(System.getProperty("user.dir") + yukinoConfig.getModelsPath());
            File[] modelFiles = configDir.listFiles();
            // Looking for models.
            if (modelFiles != null && modelFiles.length > 0) {
                for (File file :
                        modelFiles) {
                    parserHandle.parserHandle(file);
                }
            } else {
                log.warn("[Yukino] No file was found");
            }
        } else {
            log.warn("[Yukino] No parser was set.");
        }
    }
}
