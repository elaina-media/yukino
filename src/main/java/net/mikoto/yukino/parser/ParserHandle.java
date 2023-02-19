package net.mikoto.yukino.parser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author mikoto
 * &#064;date 2022/12/11
 * Create for yukino
 */
public abstract class ParserHandle<T, R> {
    private ParserHandle<?, ?> next;

    public ParserHandle<?, ?> setNext(ParserHandle<?, ?> next) {
        this.next = next;
        return next;
    }

    public void parserHandle(Object target) {
        if (next != null) {
            next.parserHandle(target);
        } else {
            parsed(target);
        }
    }

    protected abstract void parsed(@NotNull Object target);

    protected abstract R doParse(T target) throws Exception;
}
