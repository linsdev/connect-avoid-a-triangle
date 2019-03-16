package com.lins.drawable.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.lins.drawable.Drawable;

public class Rect2 extends Drawable {
    public static TextureRegion emptyTexReg;

    public Color color;
    public Rectangle boundRect2;

    public Rect2(Color color, Rectangle rect, Rectangle rect2) {
        super(rect);
        this.color = color;
        boundRect2 = rect2;
    }

    @Override
    public void draw(Batch batch) {
        batch.setColor(color);
        batch.draw(emptyTexReg, boundRect.x, boundRect.y, boundRect.width, boundRect.height);
        batch.draw(emptyTexReg, boundRect2.x, boundRect2.y, boundRect2.width, boundRect2.height);
    }
}