package net.mikoto.yukino.manager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
public abstract class AbstractManager<T> {

    protected final Map<String, T> dataMap = new HashMap<>();

    public T get(String name) {
        return dataMap.get(name);
    }

    public void put(String name, T object) {
        dataMap.put(name, object);
    }
}
