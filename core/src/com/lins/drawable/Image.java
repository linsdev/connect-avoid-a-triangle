package com.lins.drawable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Image extends Drawable {
    public TextureRegion texReg;
    public Color color = Color.WHITE;

    public Image(float x, float y, TextureRegion texReg) {
        this.texReg = texReg;
        boundRect = new Rectangle(x, y, texReg.getRegionWidth(), texReg.getRegionHeight());
    }

    public Image(float x, float y, Texture tex) {
        texReg = new TextureRegion(tex);
        boundRect = new Rectangle(x, y, texReg.getRegionWidth(), texReg.getRegionHeight());
    }

    public Image(float x, float y, Texture tex, int srcWidth, int srcHeight) {
        boundRect = new Rectangle(x, y, srcWidth, srcHeight);
        texReg = new TextureRegion(tex, srcWidth, srcHeight);
    }

    public Image(Texture tex, int srcWidth, int srcHeight) {
        this(0, 0, new TextureRegion(tex, srcWidth, srcHeight));
    }

    @Override
    public void draw(Batch batch) {
        batch.setColor(color);
        batch.draw(texReg, boundRect.x, boundRect.y, boundRect.width, boundRect.height);
    }

    public void setCenterPos(float x, float y) {
        boundRect.x = (int) (x - boundRect.width/2);
        boundRect.y = (int) (y - boundRect.height/2);
    }

    public void setScale(float xy) {
        boundRect.width = xy * texReg.getRegionWidth();
        boundRect.height = xy * texReg.getRegionHeight();
    }

    public void setScale(float x, float y) {
        boundRect.width = x * texReg.getRegionWidth();
        boundRect.height = y * texReg.getRegionHeight();
    }

    public int getTexWidth() {
        return texReg.getRegionWidth();
    }

    public int getTexHeight() {
        return texReg.getRegionHeight();
    }
}