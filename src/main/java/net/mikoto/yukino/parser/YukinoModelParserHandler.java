package net.mikoto.yukino.parser;

import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.model.YukinoModel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author mikoto
 * &#064;date 2022/12/11
 * Create for yukino
 */
public abstract class YukinoModelParserHandler extends FileParserHandler<YukinoModel> {
    private final YukinoModelManager yukinoModelManager;
    public YukinoModelParserHandler(YukinoModelManager yukinoModelManager) {
        this.yukinoModelManager = yukinoModelManager;
    }

    @Override
    public void parserHandle(Object file) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        YukinoModel yukinoModel = doParse((File) file);
        if (yukinoModel != null) {
            yukinoModelManager.register(yukinoModel);
        }
        super.parserHandle(file);
    }
}
