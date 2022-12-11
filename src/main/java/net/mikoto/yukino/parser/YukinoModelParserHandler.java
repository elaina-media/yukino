package net.mikoto.yukino.parser;

import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.model.YukinoModel;

import java.io.File;
import java.io.IOException;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
public abstract class YukinoModelParserHandler extends FileParserHandler<YukinoModel> {
    private final YukinoModelManager yukinoModelManager;
    public YukinoModelParserHandler(YukinoModelManager yukinoModelManager) {
        this.yukinoModelManager = yukinoModelManager;
    }

    @Override
    public void parserHandle(Object file) throws IOException {
        yukinoModelManager.register(doParse((File) file));
        super.parserHandle(file);
    }
}
