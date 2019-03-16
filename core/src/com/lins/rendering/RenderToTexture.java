package com.lins.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lins.screen.Screen;

public class RenderToTexture {
    public static TextureRegion drawPoint(Texture texture, int x, int y) {
        return drawRect(texture, x-1, y-1, 3, true);
    }

    public static TextureRegion drawPoint(Texture texture, int x, int y, boolean onePixelBorder) {
        if (onePixelBorder)
            return drawRect(texture, x-1, y-1, 3, true);
        else
            return drawRect(texture, x, y, 1, false);
    }

    public static TextureRegion drawRect(Texture texture, int x, int y, int pointSize, boolean onePixelBorder) {
        Pixmap p = new Pixmap(pointSize, pointSize, Pixmap.Format.RGB888);
        p.setColor(Color.WHITE);
        p.fill();
        texture.draw(p, x, y);
        p.dispose();
        if (onePixelBorder && pointSize > 2)
            return new TextureRegion(texture, x+1, y+1, pointSize-2, pointSize-2);
        else
            return new TextureRegion(texture, x, y, pointSize, pointSize);
    }

    private FrameBuffer2D fbo;
    public SpriteBatch batch;
    public ShapeRenderer draw;

    public RenderToTexture(int bufferWidth, int bufferHeight) {
        fbo = new FrameBuffer2D(bufferWidth, bufferHeight);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(fbo.projectionMatrix);
        batch.disableBlending();   // save alpha channel to result texture

        draw = new ShapeRenderer();
        draw.setProjectionMatrix(fbo.projectionMatrix);
    }

    /** Background color will be Color(0, 0, 0, 0) */
    public void begin() {
        begin(Color.CLEAR);
    }

    /** @param clearColor Background color */
    public void begin(Color clearColor) {
        fbo.begin();
        Gdx.gl.glViewport(0, 0, fbo.getWidth(), fbo.getHeight());
        Screen.Clear(clearColor);
    }

    /** The filter for returned texture is {@link Texture.TextureFilter Linear}
     * @return Result of the drawing.<br>Don't forget to dispose this texture */
    public Texture end() {
        if (batch.isDrawing()) batch.end();
        if (draw.isDrawing()) draw.end();
        fbo.end();
        fbo.dispose();
        batch.dispose();
        draw.dispose();
        return fbo.getColorBufferTexture();
    }

    /** @param filter minimization and magnification {@link Texture.TextureFilter} for returned texture
     * @return Result of the drawing.<br>Don't forget to dispose this texture */
    public Texture end(Texture.TextureFilter filter) {
        final Texture t = end();
        t.setFilter(filter, filter);
        return t;
    }

    /** @param minFilter minimization {@link Texture.TextureFilter} for returned texture
     * @param magFilter magnification {@link Texture.TextureFilter} for returned texture
     * @return Result of the drawing.<br>Don't forget to dispose this texture */
    public Texture end(Texture.TextureFilter minFilter, Texture.TextureFilter magFilter) {
        final Texture t = end();
        t.setFilter(minFilter, magFilter);
        return t;
    }

    public Texture getOutputTexture() {
        return fbo.getColorBufferTexture();
    }
}