package com.lins.connect.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lins.connect.Colour;
import com.lins.Font;
import com.lins.connect.App;
import com.lins.connect.player.PlayersList;
import com.lins.drawable.circlenumberchooser.CircleNumberChooser;
import com.lins.drawable.widget.FlatButton;
import com.lins.drawable.text.Text;
import com.lins.screen.Screen;

public class GameDifficultyScreen extends Screen {
    private static final String CAPTION_TEXT = "Select difficulty";
    private final App app;
    private final SpriteBatch batch;
    private final PlayersList players;

    private Text caption;
    private CircleNumberChooser numberChooser;
    private FlatButton buttonPlay;

    public GameDifficultyScreen(App app, PlayersList players) {
        super(app);
        this.app = app;
        batch = app.batch;
        this.players = players;

        final Color backColor = players.mixPlayersColor();
        initUI(backColor);
        Screen.ClearColor(backColor);
    }

    private void initUI(Color backColor) {
        final Font font80 = res.add(new Font(Gdx.files.internal("font/roboto.fnt"), app.dp(80)));
        final int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight() - (int) app.backBtn.getHeight();
        final float lineWidth = 2.5f * app.DP_SCALING;
        final Color background = Colour.darken(backColor);
        final Color foreground = Color.WHITE;

        caption = new Text(CAPTION_TEXT, 0, height, app.font25, width);
        height -= (int) caption.getHeight();

        numberChooser = res.add(new CircleNumberChooser(font80, lineWidth, width,
                app.dp(48), app.font25, 4, 14, 120, 30, background, foreground));
        numberChooser.addToInputMultiplexer(inputMultiplexer);

        buttonPlay = new FlatButton(0, 0, width, app.dp(70), app.font25, "P L A Y", background, foreground);
        buttonPlay.clickListener = new FlatButton.ClickListener() {
            public void clicked(int button) {
                app.setScreen(new GameScreen(app, players, numberChooser.getNumber()));
            }
        };
        buttonPlay.addToInputMultiplexer(inputMultiplexer);

        height -= (int) buttonPlay.getHeight();
        numberChooser.setCenterPos(width/2, buttonPlay.getHeight() + height/2);
    }

    @Override
    public void onBackKey() {
        app.setScreen(new PlayerColorScreen(app));
    }

    @Override
    public void render(float delta) {
        Screen.Clear();
        batch.begin();
        app.drawBackButton();
        caption.draw(batch);
        numberChooser.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }
}