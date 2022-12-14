package net.mikoto.yukino.parser.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import net.mikoto.yukino.manager.YukinoJsonManager;
import net.mikoto.yukino.parser.JsonObjectParserHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
public class JsonFileToObjectParser extends JsonObjectParserHandler {
    public JsonFileToObjectParser(YukinoJsonManager yukinoJsonManager) {
        super(yukinoJsonManager);
    }

    @Override
    protected JSONObject doParse(File target) throws IOException {
        return JSON.parseObject(new FileInputStream(target));
    }
}
