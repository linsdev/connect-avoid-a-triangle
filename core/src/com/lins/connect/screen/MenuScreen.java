package com.lins.connect.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lins.connect.Colour;
import com.lins.connect.App;
import com.lins.connect.preferences.GameSaving;
import com.lins.drawable.widget.FlatButton;
import com.lins.screen.Screen;

public class MenuScreen extends Screen {
    private final App app;
    private final SpriteBatch batch;
    private FlatButton buttonResume, buttonNewGame, buttonRules;

    public MenuScreen(App app) {
        super(app);
        this.app = app;
        batch = app.batch;

        final int width = Gdx.graphics.getWidth();
        int height = (Gdx.graphics.getHeight() - (int) app.backBtn.getHeight()) / 2;
        final Color backColor = Colour.MIDNIGHT_BLUE;
        Screen.ClearColor(backColor);
        final Color foreground = Color.WHITE;
        final int buttonHeight = app.dp(70);
        final int buttonSpace = (int) (.25f * buttonHeight);
        final int buttonOffset = buttonHeight + buttonSpace;

        height += buttonOffset;   // buttonOffset * (buttonsCount-1)/2

        if (GameSaving.isExists()) {
            buttonResume = new FlatButton(0, height, width, buttonHeight, app.font25,
                    "R E S U M E   G A M E", Colour.NEPHRITIS, foreground);
            buttonResume.clickListener = new FlatButton.ClickListener() {
                public void clicked(int button) {
                    MenuScreen.this.app.setScreen(new GameScreen(MenuScreen.this.app));
                }
            };
            buttonResume.addToInputMultiplexer(inputMultiplexer);
        }
        height -= buttonOffset;

        buttonNewGame = new FlatButton(0, height, width, buttonHeight, app.font25,
                "N E W   G A M E", Colour.PUMPKIN, foreground);
        buttonNewGame.clickListener = new FlatButton.ClickListener() {
            public void clicked(int button) {
                MenuScreen.this.app.setScreen(new PlayerColorScreen(MenuScreen.this.app));
            }
        };
        buttonNewGame.addToInputMultiplexer(inputMultiplexer);
        height -= buttonOffset;

        buttonRules = new FlatButton(0, height, width, buttonHeight, app.font25,
                "R U L E S", Colour.BELIZE_HOLE, foreground);
        buttonRules.clickListener = new FlatButton.ClickListener() {
            public void clicked(int button) {
                MenuScreen.this.app.setScreen(new RulesScreen(MenuScreen.this.app));
            }
        };
        buttonRules.addToInputMultiplexer(inputMultiplexer);
    }

    @Override
    public void onBackKey() {
        app.exit();
    }

    @Override
    public void render(float delta) {
        Screen.Clear();
        batch.begin();
        app.drawBackButton();
        if (buttonResume != null) buttonResume.draw(batch);
        buttonNewGame.draw(batch);
        buttonRules.draw(batch);
        batch.end();
    }
}