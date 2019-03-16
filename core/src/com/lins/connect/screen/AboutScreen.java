package com.lins.connect.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.lins.connect.App;
import com.lins.connect.Colour;
import com.lins.connect.player.PlayersList;
import com.lins.drawable.shape.Rect;
import com.lins.drawable.text.Text;
import com.lins.screen.Screen;

public class AboutScreen extends Screen {
    private final App app;
    private final SpriteBatch batch;
    private final PlayersList players;
    final Rect rectTop, rectBottom;
    final Text text, textBottom;

    public AboutScreen(App app, PlayersList players) {
        super(app);
        this.app = app;
        this.batch = app.batch;
        this.players = players;

        final int width = Gdx.graphics.getWidth();
        final int halfHeight = Gdx.graphics.getHeight() / 2;

        rectTop = new Rect(Colour.BELIZE_HOLE, 0,halfHeight, width,halfHeight);
        rectBottom = new Rect(Colour.ORANGE, 0,0, width,halfHeight);
        text = new Text("© 2017 Nazar Litvin\n\n@ Ukraine", app.font25, width);
        text.setCenterY(halfHeight);

        textBottom = new Text("Tap to continue", app.font25, width);
        textBottom.setY(2 * textBottom.getHeight());

        inputMultiplexer.addProcessor(new InputAdapter() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                onBackKey();
                return true;
            }
        });

        new Timer().scheduleTask(new Timer.Task() {
            public void run() {
                onBackKey();
            }
        }, 3);
    }

    @Override
    public void onBackKey() {
        app.setScreen(new GameDifficultyScreen(app, players));
    }

    @Override
    public void render(float delta) {
        Screen.Clear();
        batch.begin();
        rectTop.draw(batch);
        rectBottom.draw(batch);
        text.draw(batch);
        textBottom.draw(batch);
        app.drawBackButton();
        batch.end();
    }
}