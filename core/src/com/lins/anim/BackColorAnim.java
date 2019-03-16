package com.lins.anim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class BackColorAnim extends Anim {
    private Color beginColor, endColor;
    private final float delta;

    public BackColorAnim(float delta, Color beginColor, Color endColor) {
        this.delta = delta;
        reset(beginColor, endColor);
    }

    public void reset(Color beginColor, Color endColor) {
        progress = 0;
        this.beginColor = beginColor;
        this.endColor = endColor;
    }

    @Override
    protected void act(float deltaTime) {
        deltaTime *= delta;
        progress += deltaTime;

        if (progress < 1)
            Gdx.gl.glClearColor(
                    li(beginColor.r, endColor.r),
                    li(beginColor.g, endColor.g),
                    li(beginColor.b, endColor.b),
                    1);
        else
            Gdx.gl.glClearColor(endColor.r, endColor.g, endColor.b, 1);
    }

    /** Linearly interpolates between c1 and c2 by this.progress */
    private float li(float c1, float c2) {
        return c1 + progress * (c2 - c1);
    }
}