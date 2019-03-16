package com.lins.drawable.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.lins.drawable.Drawable;

public class Rect extends Drawable {
    public static TextureRegion emptyTexReg;

    public Color color = Color.CLEAR;

    public Rect(Color color) {
        super();
        this.color = color;
    }

    public Rect(Color color, Rectangle rect) {
        super(rect);
        this.color = color;
    }

    public Rect(Color color, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.color = color;
    }

    @Override
    public void draw(Batch batch) {
        batch.setColor(color);
        batch.draw(emptyTexReg, boundRect.x, boundRect.y, boundRect.width, boundRect.height);
    }
}