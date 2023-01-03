package net.mikoto.yukino.strategy;

/**
 * @author mikoto
 * @date 2023/1/1
 * Create for yukino
 */
public interface Strategy<T> {
    T run(Object... objects);
}
