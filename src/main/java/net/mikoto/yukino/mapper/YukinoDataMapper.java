package net.mikoto.yukino.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author mikoto
 * &#064;date 2022/12/18
 * Create for yukino
 */
public interface YukinoDataMapper {
    List<Map<String, Object>> select(Map<String, Object> paramsMap);
    int insert(Map<String, Object> paramsMap);
}
