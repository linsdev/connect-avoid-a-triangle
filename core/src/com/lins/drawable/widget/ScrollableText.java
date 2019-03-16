package com.lins.drawable.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.lins.connect.Colour;
import com.lins.Font;
import com.lins.InputMultiplexer;
import com.lins.drawable.Drawable;
import com.lins.drawable.text.Text;
import com.lins.drawable.shape.Rect;
import com.lins.drawable.shape.Rect2;
import com.lins.drawable.shape.RectOutline;

public class ScrollableText extends Widget {
    public static int calcLineWidth(int space) {
        return Math.max(1, (int) (.1f * space));
    }

    private final Text text;

    private final RectOutline back;
    private final Rect scrollBar;
    private final Drawable rectsClip;
    private final float screenToScroll;
    private final float scrollTop, scrollBottom;
    private int prevScreenY;

    public ScrollableText(String text, Font font, Color textColor, Color backColor,
                          float x, float y, float width, float height, int space) {
        super();
        final int lineWidth = calcLineWidth(space);
        final Color frontColor = Colour.lighten(backColor);
        boundRect.set(x, y, width, height);
        final float topY = y + height;

        this.text = new Text(text, x + space, topY - space, font, textColor, width - 2*space, Align.left);

        back = new RectOutline(backColor, frontColor,
                x + lineWidth, y + lineWidth, width - 2*lineWidth, height - 2*lineWidth, lineWidth);

        scrollBottom = topY - space;
        scrollTop = y + this.text.getHeight() + space;
        screenToScroll = -height / this.text.getHeight();
        final float viewHeight = height - 2 * space;

        if (this.text.getHeight() > viewHeight) {
            int barHeight = (int) (-viewHeight * screenToScroll);
            scrollBar = new Rect(frontColor, x + width - space/2, y + height - barHeight, space/2, barHeight);
        } else
            scrollBar = new Rect(Color.CLEAR);

        final float scrHeight = Gdx.graphics.getHeight();
        final float top = y + height;
        Rectangle rectTop = new Rectangle(x, top, width, scrHeight - top);
        Rectangle rectBottom = new Rectangle(x, 0, width, y);
        rectsClip = (y <= 0)           ? new Rect(backColor, rectTop) :
                    (top >= scrHeight) ? new Rect(backColor, rectBottom)
                                       : new Rect2(backColor, rectTop, rectBottom);
    }

    @Override
    public void draw(Batch batch) {
        back.draw(batch);
        scrollBar.draw(batch);
        text.draw(batch);
        rectsClip.draw(batch);
    }

    public void addToInputMultiplexer(InputMultiplexer im) {
        im.addProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (boundRect.contains(screenX, screenY)) {
            prevScreenY = screenY;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (boundRect.contains(screenX, screenY)) {
            final int deltaY = screenY - prevScreenY;
            final float newY = text.getY() + deltaY;
            if (newY <= scrollTop && newY >= scrollBottom) {
                text.setY(newY);
                scrollBar.moveByY(deltaY * screenToScroll);
            }
            prevScreenY = screenY;
            return true;
        }
        return false;
    }
}