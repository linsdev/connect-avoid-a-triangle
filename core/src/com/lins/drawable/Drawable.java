package com.lins.drawable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Drawable {
    public Rectangle boundRect;

    public Drawable() {
        boundRect = new Rectangle();
    }

    public Drawable(Rectangle rect) {
        boundRect = rect;
    }

    public Drawable(float x, float y, float width, float height) {
        boundRect = new Rectangle(x, y, width, height);
    }

    public void draw(Batch batch) {}

    public void draw(ShapeRenderer draw) {}

    public void debugDraw(ShapeRenderer draw) {
        draw.rect(boundRect.x, boundRect.y, boundRect.width, boundRect.height);
    }

    public void setPos(float x, float y) {
        boundRect.x = x;
        boundRect.y = y;
    }

    public void moveBy(float deltaX, float deltaY) {
        boundRect.x += deltaX;
        boundRect.y += deltaY;
    }

    public void moveByX(float deltaX) {
        boundRect.x += deltaX;
    }

    public void moveByY(float deltaY) {
        boundRect.y += deltaY;
    }

    public float getCenterX() {
        return boundRect.x + boundRect.width/2;
    }

    public float getCenterY() {
        return boundRect.y + boundRect.height/2;
    }

    public void setCenterX(float x) {
        boundRect.x = x - boundRect.width/2;
    }

    public void setCenterY(float y) {
        boundRect.y = y - boundRect.height/2;
    }

    public float getX() {
        return boundRect.x;
    }

    public void setX(float x) {
        boundRect.x = x;
    }

    public float getY() {
        return boundRect.y;
    }

    public void setY(float y) {
        boundRect.y = y;
    }

    public float getWidth() {
        return boundRect.width;
    }

    public float getHeight() {
        return boundRect.height;
    }

    public void setSize(float width, float height) {
        boundRect.width = width;
        boundRect.height = height;
    }
}