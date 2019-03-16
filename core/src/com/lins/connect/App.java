package com.lins.connect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.lins.Ad;
import com.lins.Font;
import com.lins.Game;
import com.lins.connect.screen.MenuScreen;
import com.lins.drawable.shape.Rect;
import com.lins.drawable.shape.RectOutline;
import com.lins.drawable.widget.FlatButton;
import com.lins.rendering.RenderToTexture;

public class App extends Game {
    public final Ad ad;
    public Matrix4 projectionMatrix;
    public SpriteBatch batch;
    public ShapeRenderer draw;

    public Font font25;
    public TextureRegion font25pointTex;

    public App(Ad ad) {
        this.ad = ad;
    }

    @Override
    public void create() {
        super.create();
        Gdx.graphics.setContinuousRendering(false);
        ShaderProgram.pedantic = false;
        projectionMatrix = new Matrix4();
        batch = res.add(new SpriteBatch());
        draw = res.add(new ShapeRenderer());

        font25 = res.add(new Font(Gdx.files.internal("font/roboto.fnt"), dp(25)));
        final Texture font25tex = font25.getOriginTexture();
        font25pointTex = RenderToTexture.drawPoint(font25tex, 1, font25tex.getHeight()-2);   // one pixel texture region
        Rect.emptyTexReg = RectOutline.emptyTexReg = FlatButton.emptyTexReg = font25pointTex;

        setScreen(new MenuScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        projectionMatrix.setToOrtho2D(0,0, width,height);
        batch.setProjectionMatrix(projectionMatrix);
        draw.setProjectionMatrix(projectionMatrix);
    }

    public void drawBackButton() {
        backBtn.draw(batch);
    }

    public void drawBackButton(TextureRegion texReg) {
        backBtn.draw(batch, texReg);
    }

    public static void log(SpriteBatch batch) {
        System.out.println(
                "renderCalls = " + batch.renderCalls +
                ", maxSpritesInBatch = " + batch.maxSpritesInBatch +
                ",  totalRenderCalls = " + batch.totalRenderCalls);
    }
}