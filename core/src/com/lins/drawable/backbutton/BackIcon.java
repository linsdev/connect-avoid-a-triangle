package com.lins.drawable.backbutton;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lins.drawable.Drawable;

public class BackIcon extends Drawable {
    public final int size;
    public float lineWidth;
    public Color color = Color.WHITE;
    private final Vector2 p0, p1, p2, p3, p4, p5;

    public BackIcon(int x, int y, int size, float lineWidth) {
        this.size = size;
        this.lineWidth = lineWidth;
        boundRect = new Rectangle(x, y, size, size);
        p0 = new Vector2(x, y).add(.75f*size, .5f*size);
        p1 = new Vector2(p0).sub(.5f*size, 0);
        final float arrowIndent = lineWidth / 3f;
        final float arrowLength = size / 4.5f;
        p2 = new Vector2(p1).sub(arrowIndent, arrowIndent);
        p3 = new Vector2(p1).add(arrowLength,  arrowLength);
        p4 = new Vector2(p1).sub(arrowIndent, -arrowIndent);
        p5 = new Vector2(p1).add(arrowLength, -arrowLength);
    }

    /** ShapeRenderer.ShapeType should be Filled */
    @Override
    public void draw(ShapeRenderer draw) {
        draw.setColor(color);
        draw.rectLine(p0, p1, lineWidth);
        draw.rectLine(p2, p3, lineWidth);
        draw.rectLine(p4, p5, lineWidth);
    }
}