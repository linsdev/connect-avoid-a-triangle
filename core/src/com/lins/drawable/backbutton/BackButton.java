package com.lins.drawable.backbutton;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.lins.rendering.RenderToTexture;
import com.lins.drawable.Drawable;

public class BackButton extends Drawable implements Disposable {
    public Color color = Color.WHITE;
    public Color colorClicked = Color.BLACK;
    public boolean isClicked = false;
    public Texture texture;

    public BackButton(int x, int y, int size, float lineWidth) {
        boundRect = new Rectangle(x, y, size, size);

        BackIcon backIcon = new BackIcon(0,0, size, lineWidth);
        RenderToTexture r = new RenderToTexture(size, size);
        r.begin();
        r.draw.begin(ShapeRenderer.ShapeType.Filled);
        backIcon.draw(r.draw);
        texture = r.end();
    }

    public void setPos(int x, int y) {
        boundRect.setPosition(x, y);
    }

    public int getSize() {
        return texture.getWidth();
    }

    @Override
    public void draw(Batch batch) {
        batch.setColor(color);
        batch.draw(texture, boundRect.x, boundRect.y);
    }

    public void draw(Batch batch, TextureRegion texReg) {
        batch.setColor(color);
        batch.draw(texReg, boundRect.x, boundRect.y);
    }

    public void onClick() {
        isClicked = true;
        final Color tmp = color;
        color = colorClicked;
        colorClicked = tmp;
    }

    public void onPostClick() {
        if (isClicked) {
            isClicked = false;
            final Color tmp = color;
            color = colorClicked;
            colorClicked = tmp;
        }
    }

    public boolean isScreenPosInside(float x, float y) {
        return x <= boundRect.width && y >= boundRect.y;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}