package net.mikoto.yukino.parser;

import com.alibaba.fastjson2.JSONObject;
import net.mikoto.yukino.manager.YukinoJsonManager;

import java.io.File;
import java.io.IOException;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
public abstract class JsonObjectParserHandler extends FileParserHandler<JSONObject> {
    private final YukinoJsonManager yukinoJsonManager;

    public JsonObjectParserHandler(YukinoJsonManager yukinoJsonManager) {
        this.yukinoJsonManager = yukinoJsonManager;
    }

    @Override
    public void parserHandle(Object target) throws IOException {
        yukinoJsonManager.put(((File) target).getName(), doParse((File) target));
        super.parserHandle(target);
    }
}
