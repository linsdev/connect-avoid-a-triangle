package com.lins.drawable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.lang.reflect.Array;

import static com.badlogic.gdx.math.MathUtils.random;

public class DrawableTable<T extends Drawable> extends Drawable {
    public interface ObjectInit<T> {
        void init(T obj, int objIndex);
    }

    public T[] objects;

    @SuppressWarnings("unchecked")
    public DrawableTable(Class<T> cls, int countX, int countY, float posX, float posY, int align,
                         float width, float height, float space, ObjectInit<T> init)
    {
        objects = (T[]) Array.newInstance(cls, countX * countY);
        if (align == Align.center) {
            float tableWidth = countX * (width + space) - space;
            float tableHeight = countY * (height + space) - space;
            posX = (int) (posX - tableWidth / 2);
            posY = (int) (posY - tableHeight / 2);
        }
        final float ws = width + space, hs = height + space;
        int i = -1;
        for (int y = 0; y < countY; ++y)
            for (int x = 0; x < countX; ++x)
                try {
                    final T obj = cls.newInstance();
                    obj.boundRect.set(posX + ws*x, posY + hs*y, width, height);
                    objects[++i] = obj;
                    init.init(obj, i);
                }
                catch (InstantiationException e) { e.printStackTrace(); }
                catch (IllegalAccessException e) { e.printStackTrace(); }
    }

    @Override
    public void draw(Batch batch) {
        for (T obj : objects) obj.draw(batch);
    }

    @Override
    public void draw(ShapeRenderer draw) {
        for (T obj : objects) obj.draw(draw);
    }

    public T randomObject() {
        return objects[random.nextInt(objects.length)];
    }
}