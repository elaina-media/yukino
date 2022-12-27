package net.mikoto.yukino.mapper.strategy;

import java.util.Map;

/**
 * @author mikoto
 * @date 2022/12/24
 * Create for yukino
 */
public interface TableNameStrategy {
    String getTableName(Map<String, Object> data);
}
