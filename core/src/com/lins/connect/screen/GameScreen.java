package com.lins.connect.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lins.rendering.RenderToTexture;
import com.lins.connect.App;
import com.lins.connect.player.PlayersList;
import com.lins.connect.drawable.widget.points.PointsArray;
import com.lins.drawable.Image;
import com.lins.screen.Screen;
import com.lins.shader.Shader;

public class GameScreen extends Screen {
    private final App app;
    private final SpriteBatch batch;
    private final ShapeRenderer draw;
    private TextureRegion backBtnTex, diskTex;
    private Image circle;
    private PointsArray points;

    private GameScreen(App app, SpriteBatch batch, ShapeRenderer draw) {
        super(app);
        this.app = app;
        this.batch = batch;
        this.draw = draw;
    }

    public GameScreen(App app) {
        this(app, app.batch, app.draw);
        final float circleRadius = genTex(app);
        points = new PointsArray(diskTex, circleRadius);
        addPointListener(app);
    }

    public GameScreen(App app, final PlayersList players, final int pointsCount) {
        this(app, app.batch, app.draw);
        final float circleRadius = genTex(app);
        points = new PointsArray(diskTex, pointsCount, circleRadius, players);
        addPointListener(app);
    }

    /** @return circleRadius */
    private float genTex(App app) {
        final float lineWidth = 3 * app.DP_SCALING;
        Gdx.gl.glLineWidth(lineWidth);
        generateTexture(lineWidth);
        return .5f * circle.getTexWidth() - lineWidth - Shader.Circle.BORDER_SIZE;
    }

    private void addPointListener(final App app) {
        points.addToInputMultiplexer(inputMultiplexer);
        points.onCompleteTriangle = new PointsArray.GameEndListener() {
            public void end(PlayersList players) {
                app.setScreen(GameEndScreen.class, app, "Y O U\nW I N", players.getOtherPlayer().color, players);
            }
        };
        points.onGameEnd = new PointsArray.GameEndListener() {
            public void end(PlayersList players) {
                app.setScreen(GameEndScreen.class, app, "T I E", players.mixPlayersColor(), players);
            }
        };
    }

    @Override
    public void onBackKey() {
        app.setScreen(MenuScreen.class, app);
    }

    @Override
    public void render(float delta) {
        Screen.Clear();

        batch.begin();
        app.drawBackButton(backBtnTex);
        circle.draw(batch);
        batch.end();

        draw.begin(ShapeRenderer.ShapeType.Line);
        points.draw(draw);
        draw.end();

        batch.begin();
        points.draw(batch);
        batch.end();

        // Debug drawing
        /*draw.begin(ShapeRenderer.ShapeType.Line);
        draw.setColor(Color.LIME);
        points.debugDraw(draw);
        app.backBtn.debugDraw(draw);
        draw.end();*/
    }

    @Override
    public void show() {
        super.show();
        points.animStart();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        circle.setCenterPos(.5f * width, .5f * height);
    }

    @Override
    public void hide() {
        super.hide();
        points.saveGame();
    }

    private void generateTexture(float lineWidth) {
        final int circleD = Gdx.graphics.getWidth() - 2*app.dp(20);
        final int diskD = app.dp(25);

        ShaderProgram shaderDisk = Shader.Disk.newShader();
        ShaderProgram shaderCircle = Shader.Circle.newShader();
        Texture texCircle = new Texture(circleD, circleD, Pixmap.Format.Alpha);
        Texture texDisk = new Texture(diskD,diskD, Pixmap.Format.Alpha);

        RenderToTexture r = new RenderToTexture(circleD+Math.max(diskD, app.backBtn.getSize()), circleD);
        r.begin();
        r.batch.begin();

        r.batch.draw(app.backBtn.texture, circleD, diskD);

        r.batch.setShader(shaderCircle);
        shaderCircle.setUniformf(Shader.Circle.texSize, circleD);
        shaderCircle.setUniformf(Shader.Circle.lineWidth, lineWidth/circleD);
        r.batch.draw(texCircle, 0,0);

        r.batch.setShader(shaderDisk);
        shaderDisk.setUniformf(Shader.Disk.texSize, diskD);
        r.batch.draw(texDisk, circleD,0);

        Texture tex = res.add(r.end());
        backBtnTex = new TextureRegion(tex, circleD,diskD, app.backBtn.getSize(),app.backBtn.getSize());
        circle = new Image(tex, circleD, circleD);
        diskTex = new TextureRegion(tex, circleD,0, diskD,diskD);

        texCircle.dispose();
        texDisk.dispose();
        shaderDisk.dispose();
        shaderCircle.dispose();
    }
}