package net.mikoto.yukino.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mikoto.yukino.mapper.strategy.PrimaryKeyGenerateStrategy;
import net.mikoto.yukino.mapper.strategy.TableNameStrategy;
import net.mikoto.yukino.model.config.InstantiableObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YukinoModel {
    private String modelName;
    private Field[] fields;
    private TableNameStrategy tableNameStrategy;
    private PrimaryKeyGenerateStrategy<?> pkGenerateStrategy;
    private Integer idFieldIndex = 0;
}
