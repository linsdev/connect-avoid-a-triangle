package com.lins.drawable.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lins.drawable.Drawable;

public class RectOutline extends Drawable {
    public static TextureRegion emptyTexReg;

    public Color color = Color.CLEAR;
    public Color colorLine = color;
    public float lineWidth = 1;

    public RectOutline() {
        super();
    }

    public RectOutline(Color color, Color colorLine) {
        super();
        this.color = color;
        this.colorLine = colorLine;
    }

    public RectOutline(Color color, Color colorLine, float x, float y, float width, float height, float lineWidth) {
        super(x, y, width, height);
        this.lineWidth = lineWidth;
        this.color = color;
        this.colorLine = colorLine;
    }

    @Override
    public void draw(Batch batch) {
        if (colorLine != color) {
            batch.setColor(colorLine);
            final float lw2 = 2 * lineWidth;
            batch.draw(emptyTexReg, boundRect.x-lineWidth, boundRect.y-lineWidth, boundRect.width+lw2, boundRect.height+lw2);
        }
        batch.setColor(color);
        batch.draw(emptyTexReg, boundRect.x, boundRect.y, boundRect.width, boundRect.height);
    }
}