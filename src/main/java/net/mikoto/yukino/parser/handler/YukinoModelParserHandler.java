package net.mikoto.yukino.parser.handler;

import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.model.YukinoModel;
import net.mikoto.yukino.parser.FileParserHandle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author mikoto
 * &#064;date 2022/12/11
 * Create for yukino
 */
public abstract class YukinoModelParserHandler extends FileParserHandle<YukinoModel> {
    private final YukinoModelManager yukinoModelManager;
    public YukinoModelParserHandler(YukinoModelManager yukinoModelManager) {
        this.yukinoModelManager = yukinoModelManager;
    }

    @Override
    public void parserHandle(Object file) {
        YukinoModel yukinoModel = null;
        try {
            yukinoModel = doParse((File) file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (yukinoModel != null) {
            yukinoModelManager.register(yukinoModel);
        }
        super.parserHandle(file);
    }
}
