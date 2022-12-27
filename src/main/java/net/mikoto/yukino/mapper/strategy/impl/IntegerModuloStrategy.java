package net.mikoto.yukino.mapper.strategy.impl;

import net.mikoto.yukino.mapper.strategy.TableNameStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author mikoto
 * @date 2022/12/24
 * Create for yukino
 */
public class IntegerModuloStrategy implements TableNameStrategy {
    private final String fieldName;
    private final String resultFormat;
    private final Integer divisor;

    /**
     * @param fieldName The id field name.
     * @param resultFormat The format e.g.: table_%i (%i is the variable).
     */
    public IntegerModuloStrategy(String fieldName, String resultFormat, Integer divisor) {
        this.fieldName = fieldName;
        this.resultFormat = resultFormat;
        this.divisor = divisor;
    }


    @Override
    public String getTableName(@NotNull Map<String, Object> data) {
        return resultFormat.replace("%i", String.valueOf(((Integer) data.get(fieldName)) % divisor));
    }
}
