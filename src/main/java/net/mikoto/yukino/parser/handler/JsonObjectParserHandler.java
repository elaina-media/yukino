package net.mikoto.yukino.parser.handler;

import com.alibaba.fastjson2.JSONObject;
import net.mikoto.yukino.manager.YukinoJsonManager;
import net.mikoto.yukino.parser.FileParserHandle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author mikoto
 * &#064;date 2022/12/11
 * Create for yukino
 */
public abstract class JsonObjectParserHandler extends FileParserHandle<JSONObject> {
    private final YukinoJsonManager yukinoJsonManager;

    public JsonObjectParserHandler(YukinoJsonManager yukinoJsonManager) {
        this.yukinoJsonManager = yukinoJsonManager;
    }

    @Override
    public void parserHandle(Object target) {
        try {
            yukinoJsonManager.put(((File) target).getName(), doParse((File) target));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        super.parserHandle(target);
    }
}
