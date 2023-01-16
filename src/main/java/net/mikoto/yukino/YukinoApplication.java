package net.mikoto.yukino;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.mikoto.yukino.manager.YukinoConfigManager;
import net.mikoto.yukino.manager.YukinoJsonManager;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.model.Config;
import net.mikoto.yukino.parser.ParserHandle;
import net.mikoto.yukino.service.YukinoDaoService;
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
    private final YukinoConfigManager yukinoConfigManager;
    private final YukinoModelManager yukinoModelManager;
    private final YukinoJsonManager yukinoJsonManager;
    private final YukinoDaoService yukinoDaoService;

    public YukinoApplication(YukinoModelManager yukinoModelManager,
                             YukinoJsonManager yukinoJsonManager,
                             YukinoConfigManager yukinoConfigManager,
                             YukinoDaoService yukinoDaoService) {
        this.yukinoModelManager = yukinoModelManager;
        this.yukinoJsonManager = yukinoJsonManager;
        this.yukinoConfigManager = yukinoConfigManager;
        this.yukinoDaoService = yukinoDaoService;
        log.info("[Yukino] Started");
    }

    public void doScan(String name) throws Exception {
        doScan(yukinoConfigManager, name);
    }

    public void doScan(@NotNull YukinoConfigManager yukinoConfigManager, String name) throws Exception {
        // Do scan and parser
        Config config = yukinoConfigManager.get(name);
        if (config.getParserHandles().length > 0) {
            parserHandle = config.getParserHandles()[0];
            ParserHandle<?, ?> lastHandler = parserHandle;
            for (int i = 1; i < config.getParserHandles().length; i++) {
                lastHandler = lastHandler.setNext(config.getParserHandles()[i]);
            }

            // To make sure the dir is existed.
            createDir(config.getModelsPath());
            File configDir = new File(System.getProperty("user.dir") + config.getModelsPath());
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
