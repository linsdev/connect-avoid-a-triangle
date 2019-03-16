package com.lins.connect.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lins.connect.Colour;
import com.lins.connect.App;
import com.lins.drawable.widget.ScrollableText;
import com.lins.drawable.text.Text;
import com.lins.screen.Screen;

public class RulesScreen extends Screen {
    /**
     * По окружности расставляют несколько точек.
     * При очередном ходе каждый из двух игроков
     * проводит линию своего цвета, соединяющюю любые две точки.
     * Проигрывает тот, кто вынужден первым
     * построить треугольник своего цвета с вершинами в этих точках.
     */
    private static final String RULES_TEXT =
            "There are several points on the circle.\n" +
            "To make a move the player draws a line connecting any two points " +
            "avoiding the formation of a triangle from one color lines.\n" +
            "The player who nothing remains as to construct a triangle is losing.\n" +
            "\n" +
            "During the game, the background color indicates who the current player. " +
            "After the game, it points to the winner.";

    private final App app;
    private final SpriteBatch batch;
    private final Text caption;
    private final ScrollableText scrollableText;

    public RulesScreen(App app) {
        super(app);
        this.app = app;
        batch = app.batch;

        final int space = app.dp(15);
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight() - space;
        final Color backColor = Colour.MIDNIGHT_BLUE;
        Screen.ClearColor(Colour.NEPHRITIS);

        caption = new Text("R U L E S", 0, height, app.font25, width);

        final int lineWidth = ScrollableText.calcLineWidth(space);
        height -= space + (int) caption.getHeight() + 2*lineWidth;
        width += 2 * lineWidth;
        scrollableText = new ScrollableText(RULES_TEXT, app.font25, Color.WHITE,
                backColor, -lineWidth, -lineWidth, width, height, space);
        scrollableText.addToInputMultiplexer(inputMultiplexer);
    }

    @Override
    public void onBackKey() {
        app.setScreen(new MenuScreen(app));
    }

    @Override
    public void render(float delta) {
        Screen.Clear();
        batch.begin();
        scrollableText.draw(batch);
        caption.draw(batch);
        app.drawBackButton();
        batch.end();
    }
}
