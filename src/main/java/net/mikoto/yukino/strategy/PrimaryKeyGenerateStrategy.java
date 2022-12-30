package net.mikoto.yukino.strategy;

/**
 * @author mikoto
 * @date 2022/12/25
 * Create for yukino
 */
public interface PrimaryKeyGenerateStrategy<T> {
    T generateKey();
}
