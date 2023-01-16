package net.mikoto.yukino.model;

import java.util.ArrayList;

/**
 * @author mikoto
 * &#064;date 2023/1/17
 * Create for yukino
 */
public abstract class HasAnArrayListClass<T> {
    protected final ArrayList<T> arrayList = new ArrayList<>();

    public T get(int index) {
        return arrayList.get(index);
    }

    public void put(T object) {
        arrayList.add(object);
    }

    public void put(int index, T object) {
        arrayList.add(index, object);
    }

    public T[] getAll() {
        return (T[]) arrayList.toArray();
    }


}
