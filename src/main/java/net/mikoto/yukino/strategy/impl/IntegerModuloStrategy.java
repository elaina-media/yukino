package net.mikoto.yukino.strategy.impl;

import net.mikoto.yukino.strategy.TableNameStrategy;

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
    public String run(Object... objects) {
        if (objects[0] instanceof Map<?,?>) {
            Map<?, ?> map = (Map<?, ?>) objects[0];
            return resultFormat.replace("%i", String.valueOf(((Integer) map.get(fieldName)) % divisor));
        } else {
            throw new RuntimeException("Unknown type.");
        }
    }
}
