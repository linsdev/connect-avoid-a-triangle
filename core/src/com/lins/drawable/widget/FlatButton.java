package com.lins.drawable.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lins.Font;
import com.lins.InputMultiplexer;
import com.lins.drawable.text.TextDuo;

public class FlatButton extends Widget {
    public interface ClickListener {
        void clicked(int button);
    }

    public static TextureRegion emptyTexReg;

    public ClickListener clickListener;
    private Color color;
    private Color colorPressed;
    private final TextDuo text;
    private boolean notPressed = true;
    public FlatButton(float width, float height, Font font, String text, Color normal, Color pressed) {
        this(0, 0, width, height, font, text, normal, pressed);
    }

    public FlatButton(float x, float y, float width, float height, Font font, String text, Color normal, Color pressed) {
        super(x, y, width, height);
        this.text = new TextDuo(text, pressed, font, width)
                .setAnotherText(text, normal);
        this.text.setCenterY(height/2);
        this.text.moveBy(x, y);
        color = normal;
        colorPressed = pressed;
    }

    @Override
    public void draw(Batch batch) {
        if (notPressed) {
            batch.setColor(color);
            batch.draw(emptyTexReg, boundRect.x, boundRect.y, boundRect.width, boundRect.height);
            text.draw(batch);
        } else {
            batch.setColor(colorPressed);
            batch.draw(emptyTexReg, boundRect.x, boundRect.y, boundRect.width, boundRect.height);
            text.drawAnother(batch);
        }
    }

    @Override
    public void moveBy(float deltaX, float deltaY) {
        super.moveBy(deltaX, deltaY);
        text.moveBy(deltaX, deltaY);
    }

    public void addToInputMultiplexer(InputMultiplexer im) {
        im.addProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (boundRect.contains(screenX, screenY)) {
            notPressed = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!notPressed) {
            notPressed = true;
            if (boundRect.contains(screenX, screenY))
                clickListener.clicked(button);
        }
        return false;
    }
}