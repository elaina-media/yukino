package net.mikoto.yukino.parser;

import lombok.extern.log4j.Log4j2;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.model.YukinoModel;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
@Log4j2
public abstract class ParserHandler {
    private ParserHandler next;
    private final YukinoModelManager yukinoModelManager;

    protected ParserHandler(YukinoModelManager yukinoModelManager) {
        this.yukinoModelManager = yukinoModelManager;
    }

    public ParserHandler setNext(ParserHandler next) {
        this.next = next;
        return next;
    }

    public final void parserHandle(File file) throws IOException {
        yukinoModelManager.register(doParse(file));
        if (next != null) {
            next.parserHandle(file);
        } else {
            parsed(file);
        }
    }

    protected void parsed(@NotNull File file) {
        log.info("[Yukino] Parsed file -> " + file.getName());
    }

    protected abstract YukinoModel doParse(File file) throws IOException;
}
