package net.mikoto.yukino.parser;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author mikoto
 * &#064;date 2022/12/11
 * Create for yukino
 */
@Log4j2
public abstract class FileParserHandler<R> extends ParserHandler<File, R> {
    @Override
    protected void parsed(@NotNull Object target) {
        log.info("[Yukino] Parsed file -> " + ((File) target).getName());
    }
}
