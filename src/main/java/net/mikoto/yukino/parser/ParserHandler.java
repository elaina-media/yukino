package net.mikoto.yukino.parser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author mikoto
 * @date 2022/12/11
 * Create for yukino
 */
public abstract class ParserHandler<T, R> {
    private ParserHandler<?, ?> next;

    public ParserHandler<?, ?> setNext(ParserHandler<?, ?> next) {
        this.next = next;
        return next;
    }

    public void parserHandle(Object target) throws IOException {
        if (next != null) {
            next.parserHandle(target);
        } else {
            parsed(target);
        }
    }

    protected abstract void parsed(@NotNull Object target);

    protected abstract R doParse(T target) throws IOException;
}
