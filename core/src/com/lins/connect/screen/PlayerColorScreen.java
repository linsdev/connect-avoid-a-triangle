package com.lins.connect.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.lins.connect.App;
import com.lins.connect.Colour;
import com.lins.connect.player.Player;
import com.lins.connect.player.PlayersList;
import com.lins.drawable.DrawableTable;
import com.lins.drawable.shape.RectOutline;
import com.lins.drawable.text.Text;
import com.lins.screen.Screen;

import static com.badlogic.gdx.math.MathUtils.random;

public class PlayerColorScreen extends Screen {
    private static final String CAPTION_TEXT = "Select color for Player ";
    private static final String SELECTED_RECT_TEXT = "Tap to\nconfirm";
    private final App app;
    private final SpriteBatch batch;
    private Text caption, rectText;
    private DrawableTable<RectOutline> rectTable;
    private RectOutline selectedRect;
    private Player player1;

    public PlayerColorScreen(final App app) {
        super(app);
        this.app = app;
        batch = app.batch;
        initUI(CAPTION_TEXT + "1", 2, 3);
        selectRect(selectedRect = rectTable.randomObject());

        inputMultiplexer.addProcessor(new InputAdapter() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                for (RectOutline rect : rectTable.objects)
                    if (rect.boundRect.contains(screenX, screenY)) {
                        if (rect == selectedRect)
                            addPlayer(rect.color);
                        else
                            selectRect(rect);
                        return true;
                    }
                return false;
            }
        });
    }

    @Override
    public void onBackKey() {
        app.setScreen(new MenuScreen(app));
    }

    private void initUI(String text, int rectCountX, int rectCountY) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight() - (int) app.backBtn.getHeight();

        caption = new Text(text, 0, height, app.font25, width);

        float space = 13 * app.DP_SCALING;
        height -= space / 2;
        final float rectLineWidth = (int) (space/3);
        int rectSize = Math.min((int) (width/rectCountX - 2*space), (int) (height/rectCountY - 2*space));
        space = (int) space;

        rectTable = new DrawableTable<RectOutline>(RectOutline.class, rectCountX, rectCountY,
                .5f*width, .5f*height-space, Align.center, rectSize, rectSize, space,
                new DrawableTable.ObjectInit<RectOutline>() {
                    public void init(RectOutline obj, int objIndex) {
                        obj.colorLine = obj.color = Colour.COLORS[objIndex];
                        obj.lineWidth = rectLineWidth;
                    }
                });

        rectText = new Text(SELECTED_RECT_TEXT, app.font25, rectSize);
    }

    private void selectRect(RectOutline rect) {
        selectedRect.colorLine = selectedRect.color;
        selectedRect = rect;
        selectedRect.colorLine = Color.WHITE;
        Screen.ClearColor(rect.color);
        rectText.setCenterPos(rect.getCenterX(), rect.getCenterY());
    }

    private void addPlayer(Color color) {
        if (player1 == null) {
            player1 = new Player(1, color);
            caption.setText(CAPTION_TEXT + "2");
            selectRect(rectTable.randomObject());
        }
        else {
            Player player2 = new Player(2, color);
            PlayersList players = new PlayersList(player1, player2);
            if (Colour.isUAcolors(player1.color, player2.color) && random.nextInt(100) == 0)
                app.setScreen(new AboutScreen(app, players));
            else
                app.setScreen(new GameDifficultyScreen(app, players));
        }
    }

    @Override
    public void render(float delta) {
        Screen.Clear();
        batch.begin();
        app.drawBackButton();
        rectTable.draw(batch);
        caption.draw(batch);
        rectText.draw(batch);
        batch.end();
    }
}