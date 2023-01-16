package net.mikoto.yukino.observer;

import net.mikoto.yukino.model.HasAHashMapClass;

/**
 * @author mikoto
 * &#064;date 2023/1/17
 * Create for yukino
 */
public abstract class Observable extends HasAHashMapClass<Observer> {
    public void addObserver(String name, Observer observer) {
        super.put(name, observer);
    }

    public void removeObserver(String name) {
        super.remove(name);
    }

    public void notifyObservers() {
        for (Observer observer : super.getAllValues()) {
            observer.update(this);
        }
    }
}
