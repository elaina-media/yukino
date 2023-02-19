package net.mikoto.yukino.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YukinoModel {
    private String modelName;
    private Field[] fields;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Field {
        String className;
        String fieldName;
    }

    public static boolean doCheckModel(@NotNull YukinoModel yukinoModel, Map<String, Object> data) {
        for (YukinoModel.Field field :
                yukinoModel.getFields()) {
            if (!data.containsKey(field.getFieldName())) {
                return false;
            }
        }
        return true;
    }
}
