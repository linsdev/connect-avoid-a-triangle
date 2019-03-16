package com.lins;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Disposer implements Disposable {

    private final Array<Disposable> objects = new Array<Disposable>(false, 16);

    public <T extends Disposable> boolean contains(T obj) {
        return objects.contains(obj, true);
    }

    public <T extends Disposable> T add(T obj) {
        objects.add(obj);
        return obj;
    }

    public <T extends Disposable> boolean remove(T obj) {
        obj.dispose();
        return objects.removeValue(obj, true);
    }

    public void dispose() {
        for (Disposable obj : objects)
            obj.dispose();
    }
}