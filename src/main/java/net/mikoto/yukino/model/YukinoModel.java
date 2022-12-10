package net.mikoto.yukino.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YukinoModel {
    private String modelName;
    private Field[] fields;
    private Integer idFieldIndex = 0;
}
