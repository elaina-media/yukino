package net.mikoto.yukino.parser.handler.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import net.mikoto.yukino.manager.YukinoModelManager;
import net.mikoto.yukino.model.YukinoModel;
import net.mikoto.yukino.model.config.InstantiableObject;
import net.mikoto.yukino.parser.handler.YukinoModelParserHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author mikoto
 * &#064;date 2022/12/11
 * Create for yukino
 */
public class ModelFileParserHandler extends YukinoModelParserHandler {
    public ModelFileParserHandler(YukinoModelManager yukinoModelManager) {
        super(yukinoModelManager);
    }

    @Override
    protected YukinoModel doParse(@NotNull File target) throws Exception {
        if (target.getName().endsWith(".yukino.model.json")) {
            JSONObject rawModel = JSON.parseObject(new FileInputStream(target));
            YukinoModel yukinoModel;

            // Add fields.
            JSONArray fields = rawModel.getJSONArray("fields");
            YukinoModel.Field[] fieldsResult = new YukinoModel.Field[fields.size()];
            if (fields.size() > 0 && fields.get(0) instanceof JSONArray) {
                for (int i = 0; i < fields.size(); i++) {
                    JSONArray fieldJson = fields.getJSONArray(i);
                    YukinoModel.Field field = new YukinoModel.Field();
                    field.setClassName(fieldJson.getString(0));
                    field.setFieldName(fieldJson.getString(1));
                    fieldsResult[i] = field;
                }

                rawModel.remove("fields");

                yukinoModel = rawModel.to(YukinoModel.class);
                yukinoModel.setFields(fieldsResult);


                return yukinoModel;
            }
        }
        return null;
    }
}
