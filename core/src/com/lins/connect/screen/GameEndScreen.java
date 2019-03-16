package com.lins.connect.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lins.Font;
import com.lins.connect.App;
import com.lins.connect.Colour;
import com.lins.connect.player.PlayersList;
import com.lins.connect.preferences.GameSaving;
import com.lins.drawable.text.Text;
import com.lins.drawable.widget.FlatButton;
import com.lins.screen.Screen;

public class GameEndScreen extends Screen {
    private final App app;
    private final SpriteBatch batch;
    private final Text text;
    private final FlatButton buttonToMenu, buttonNewGame;

    public GameEndScreen(App app, String text, Color backColor, final PlayersList players) {
        super(app);
        this.app = app;
        batch = app.batch;
        GameSaving.clear();

        final int width = Gdx.graphics.getWidth();
        final int height = Gdx.graphics.getHeight() - (int) app.backBtn.getHeight();
        final Color background = Colour.darken(backColor);
        final Color foreground = Color.WHITE;
        final int buttonHeight = app.dp(70);

        final Font font = res.add(new Font(Gdx.files.internal("font/roboto.fnt"), app.dp(80)));
        this.text = new Text(text, font, width);
        this.text.setCenterY(height/2);

        buttonToMenu = new FlatButton(width, buttonHeight, app.font25, "T O   M E N U", background, foreground);
        buttonToMenu.moveBy(0, height-buttonToMenu.getHeight());
        buttonToMenu.clickListener = new FlatButton.ClickListener() {
            public void clicked(int button) {
                onBackKey();
            }
        };
        buttonToMenu.addToInputMultiplexer(inputMultiplexer);

        buttonNewGame = new FlatButton(width, buttonHeight, app.font25, "N E W   G A M E", background, foreground);
        buttonNewGame.clickListener = new FlatButton.ClickListener() {
            public void clicked(int button) {
                GameEndScreen.this.app.setScreen(new GameDifficultyScreen(GameEndScreen.this.app, players));
            }
        };
        buttonNewGame.addToInputMultiplexer(inputMultiplexer);

        Screen.ClearColor(backColor);
    }

    @Override
    public void onBackKey() {
        app.setScreen(new MenuScreen(app));
    }

    @Override
    public void render(float delta) {
        Screen.Clear();
        batch.begin();
        app.drawBackButton();
        text.draw(batch);
        buttonToMenu.draw(batch);
        buttonNewGame.draw(batch);
        batch.end();
    }

    @Override
    public void hide() {
        app.ad.showFullscreenAd();
        super.hide();
    }
}