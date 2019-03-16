package com.lins.anim;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class MoveAnim extends Anim {
    private Vector2 pos;
    private final Vector2 beginPos;
    private final Vector2 endPos;
    private final float delta;

    public MoveAnim(Vector2 objPos, float delta, Vector2 beginPos, Vector2 endPos) {
        pos = objPos;
        this.delta = delta;
        this.beginPos = beginPos;
        this.endPos = endPos;
    }

    @Override
    protected void act(float deltaTime) {
        deltaTime *= delta;
        interpolate(progress);
        progress += deltaTime;
        if (progress > 1) {
            progress = 1;
            pos.set(endPos);
        }
    }

    private void interpolate(float alpha) {
        alpha = Interpolation.smooth.apply(alpha);
        final float invAlpha = 1.0f - alpha;
        pos.x = (beginPos.x * invAlpha) + (endPos.x * alpha);
        pos.y = (beginPos.y * invAlpha) + (endPos.y * alpha);
    }
}