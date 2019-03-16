package com.lins.drawable.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.lins.Font;
import com.lins.drawable.Drawable;

public class Text extends Drawable {
    public Font font;
    public GlyphLayout glyphLayout;

    public Text(String text, Font font, float width) {
        this(text, 0, 0, font, Color.WHITE, width, Align.center);
    }

    public Text(String text, float x, float y, Font font, float width) {
        this(text, x, y, font, Color.WHITE, width, Align.center);
    }

    public Text(String text, float x, float y, Font font, float width, int horizontalAlign) {
        this(text, x, y, font, Color.WHITE, width, horizontalAlign);
    }

    public Text(String text, float x, float y, Font font, Color color, float width) {
        this(text, x, y, font, color, width, Align.center);
    }

    public Text(String text, float x, float y, Font font, Color color, float width, int horizontalAlign) {
        this.font = font;
        glyphLayout = new GlyphLayout(font, text, color, width, horizontalAlign, true);
        boundRect = new Rectangle(x, y, width, glyphLayout.height);
    }

    public void setText(String text) {
        glyphLayout.setText(font, text, Color.WHITE, boundRect.width, Align.center, true);
    }

    public void setText(String text, Color color) {
        glyphLayout.setText(font, text, color, boundRect.width, Align.center, true);
    }

    public void setText(String text, Color color, int horizontalAlign) {
        glyphLayout.setText(font, text, color, boundRect.width, horizontalAlign, true);
    }

    @Override
    public void draw(Batch batch) {
        font.draw(batch, glyphLayout, boundRect.x, boundRect.y);
    }

    @Override
    public void debugDraw(ShapeRenderer draw) {
        draw.rect(boundRect.x, boundRect.y-boundRect.height, boundRect.width, boundRect.height);
    }

    @Override
    public void setCenterY(float y) {
        boundRect.y = y + boundRect.height/2;
    }

    public void setCenterPos(float x, float y) {
        boundRect.x = x - boundRect.width/2;
        boundRect.y = y + boundRect.height/2;
    }
}