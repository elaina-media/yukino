package net.mikoto.yukino.strategy.impl;

import net.mikoto.yukino.strategy.TableNameStrategy;

import java.util.Map;

/**
 * @author mikoto
 * &#064;date 2023/1/25
 * Create for yukino
 */
public class LongModuloStrategy implements TableNameStrategy {
    private final String fieldName;
    private final String resultFormat;
    private final Integer divisor;

    /**
     * @param fieldName The id field name.
     * @param resultFormat The format e.g.: table_%i (%i is the variable).
     */
    public LongModuloStrategy(String fieldName, String resultFormat, Integer divisor) {
        this.fieldName = fieldName;
        this.resultFormat = resultFormat;
        this.divisor = divisor;
    }


    @Override
    public String run(Object... objects) {
        if (objects[0] instanceof Map<?, ?> map) {
            return resultFormat.replace("%i", String.valueOf(((Long) map.get(fieldName)) % divisor));
        } else {
            throw new RuntimeException("Unknown type.");
        }
    }
}
