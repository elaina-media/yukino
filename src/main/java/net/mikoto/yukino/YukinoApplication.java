package net.mikoto.yukino;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.mikoto.yukino.model.Config;
import net.mikoto.yukino.parser.ParserHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static net.mikoto.yukino.util.FileUtil.createDir;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
@Log4j2
@Getter
@Setter
public class YukinoApplication {
    private ParserHandler<?, ?> parserHandler;
    private Config config;
    public YukinoApplication(@NotNull Config config) throws IOException {
        log.info("[Yukino] Started");
        this.config = config;
    }

    public void doScan(@NotNull Config config) throws IOException {
        // Do scan and parser
        this.config = config;
        if (config.getParserHandlers().length > 0) {
            parserHandler = config.getParserHandlers()[0];
            ParserHandler<?, ?> lastHandler = parserHandler;
            for (int i = 1; i < config.getParserHandlers().length; i++) {
                lastHandler = lastHandler.setNext(config.getParserHandlers()[i]);
            }

            // To make sure the dir is existed.
            createDir(config.getModelsPath());
            File configDir = new File(System.getProperty("user.dir") + config.getModelsPath());
            File[] modelFiles = configDir.listFiles();
            // Looking for models.
            if (modelFiles != null && modelFiles.length > 0) {
                for (File file :
                        modelFiles) {
                    parserHandler.parserHandle(file);
                }
            } else {
                log.warn("[Yukino] No file was found");
            }
        } else {
            log.warn("[Yukino] No parser was set.");
        }
    }
}
