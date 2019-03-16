package com.lins.drawable.circlenumberchooser;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.lins.drawable.Drawable;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

class Button extends Drawable {
    final Polygon bound;
    final int number;
    private TextureRegion tex, texNormal, texSelected;

    Button(int x, int y, float radius, int size, float angle, float halfAngleStep, int number) {
        super(x + (int) (radius * sinDeg(angle)),
                y + (int) (radius * cosDeg(angle)), size, size);
        this.number = number;

        final float beginAngle = angle - halfAngleStep;
        final float endAngle = angle + halfAngleStep;
        radius += size;
        bound = new Polygon(new float[]{
                radius * sinDeg(beginAngle), radius * cosDeg(beginAngle),
                radius * sinDeg(endAngle), radius * cosDeg(endAngle),
                0, 0
        });
        bound.translate(x + .5f * size, y + .5f * size);
    }

    Button select() {
        tex = texSelected;
        return this;
    }

    void setTextureRegions(TextureRegion normal, TextureRegion selected) {
        this.texNormal = normal;
        this.texSelected = selected;
        tex = normal;
    }

    void changeState() {
        tex = (tex == texSelected) ? texNormal : texSelected;
    }

    @Override
    public void moveBy(float deltaX, float deltaY) {
        super.moveBy(deltaX, deltaY);
        bound.translate(deltaX, deltaY);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(tex, boundRect.x, boundRect.y);
    }

    @Override
    public void debugDraw(ShapeRenderer draw) {
        super.debugDraw(draw);
        draw.polygon(bound.getTransformedVertices());
    }
}