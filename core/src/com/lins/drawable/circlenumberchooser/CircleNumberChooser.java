package com.lins.drawable.circlenumberchooser;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.lins.Font;
import com.lins.InputMultiplexer;
import com.lins.rendering.RenderToTexture;
import com.lins.drawable.Drawable;
import com.lins.drawable.Image;
import com.lins.drawable.text.Text;
import com.lins.shader.Shader;

public class CircleNumberChooser extends Drawable implements Disposable {
    private class Input extends InputAdapter {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int btn) {
            for (Button b : buttons)
                if (b.bound.contains(screenX, screenY)) {
                    selectButton(b);
                    return true;
                }
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            for (Button b : buttons)
                if (b.bound.contains(screenX, screenY)) {
                    selectButton(b);
                    return true;
                }
            return false;
        }
    }

    private TextureRegion texBack;
    private final Button[] buttons;
    private Button selectedButton;
    private Text textNumber;
    private Image background;

    public CircleNumberChooser(Font font, float lineWidth, int circleSize,
                               int buttonSize, Font fontButton,
                               int fromNumber, int toNumber, float startAngle, float stepAngle,
                               Color normal, Color pressed) {
        boundRect.setSize(circleSize, circleSize);
        final int centerOffset = circleSize / 2;

        buttons = new Button[toNumber-fromNumber+1];
        final float buttonRadius = .5f * buttonSize;
        final float pad = 1.6f * buttonSize;
        final float radius = .5f * (circleSize-pad);
        float angle = startAngle;

        final int btnOffset = (int) (centerOffset - buttonRadius);
        final float halfAngleStep = stepAngle / 2;
        for (int i = 0; i < buttons.length; ++i, angle+=stepAngle)
            buttons[i] = new Button(btnOffset, btnOffset, radius, buttonSize, angle, halfAngleStep, i+fromNumber);

        generateTexture(2*radius, lineWidth, buttonSize, fontButton, fromNumber, normal, pressed);

        selectedButton = buttons[0].select();
        textNumber = new Text(Integer.toString(selectedButton.number), font, boundRect.width);
        textNumber.setCenterY(centerOffset);

        background = new Image(0, 0, texBack);
        background.setCenterPos(centerOffset, centerOffset);
    }

    @Override
    public void draw(Batch batch) {
        background.draw(batch);
        for (Button b : buttons) b.draw(batch);
        textNumber.draw(batch);
    }

    @Override
    public void debugDraw(ShapeRenderer draw) {
        super.debugDraw(draw);
        for (Button b : buttons) b.debugDraw(draw);
    }

    private void selectButton(Button button) {
        selectedButton.changeState();
        button.changeState();
        selectedButton = button;
        textNumber.setText(Integer.toString(button.number));
    }

    public void setCenterPos(float x, float y) {
        x = (int) (x - boundRect.width/2);
        y = (int) (y - boundRect.height/2);
        super.setPos(x, y);
        background.moveBy(x, y);
        textNumber.moveBy(x, y);
        for (Button b : buttons) b.moveBy(x, y);
    }

    private void generateTexture(float circleDiameter, float lineWidth,
                                 int buttonSize, Font buttonFont, int fromNumber, Color normal, Color pressed) {
        final int circleSize = (int) (circleDiameter + Shader.Circle.BORDER_SIZE + 2*lineWidth);
        Texture texCircle = new Texture(circleSize, circleSize, Pixmap.Format.Alpha);
        Texture texDisk = new Texture(buttonSize, buttonSize, Pixmap.Format.Alpha);
        ShaderProgram shaderCircle = Shader.Circle.newShader();
        ShaderProgram shaderDisk = Shader.Disk.newShader();

        final int buttonsCountY = circleSize / buttonSize;
        final int buttonsCountX = 1 + buttons.length / buttonsCountY;
        final int buttonSelectedOffset = buttonSize * buttonsCountX;
        final int buttonsUVx[] = new int[buttons.length];
        final int buttonsUVy[] = new int[buttons.length];

        RenderToTexture r = new RenderToTexture(circleSize + 2*buttonsCountX*buttonSize, circleSize);
        r.begin();
        r.batch.begin();

        r.batch.setShader(shaderCircle);
        shaderCircle.setUniformf(Shader.Circle.texSize, circleSize);
        shaderCircle.setUniformf(Shader.Circle.lineWidth, lineWidth/circleSize);
        r.batch.draw(texCircle, 0, 0);

        r.batch.setShader(shaderDisk);
        shaderDisk.setUniformf(Shader.Disk.texSize, buttonSize);
        for (int i = 0; i < buttons.length; ++i) {
            int xi = i / buttonsCountY;
            int yi = i % buttonsCountY;
            int x = circleSize + xi * buttonSize;
            int y = yi * buttonSize;
            buttonsUVx[i] = x;
            buttonsUVy[i] = y;

            r.batch.setColor(normal);
            r.batch.draw(texDisk, x, y);

            x += buttonSelectedOffset;
            r.batch.setColor(pressed);
            r.batch.draw(texDisk, x, y);
        }

        r.batch.enableBlending();
        r.batch.setShader(null);
        final GlyphLayout glyph = new GlyphLayout();
        for (int i = 0; i < buttons.length; ++i) {
            int x = buttonsUVx[i];
            int y = buttonsUVy[i];
            buttonsUVx[i] = x - 1;
            buttonsUVy[i] = y - 1;
            String number = Integer.toString(i + fromNumber);

            glyph.setText(buttonFont, number, pressed, buttonSize, Align.center, false);
            //y = y + buttonSize - (int) (.42f * glyph.height);
            y = y + (int) (1.53f * glyph.height);
            buttonFont.draw(r.batch, glyph, x, y);

            x += buttonSelectedOffset;
            glyph.setText(buttonFont, number, normal, buttonSize, Align.center, false);
            buttonFont.draw(r.batch, glyph, x, y);
        }

        final Texture outputTex = r.end(Texture.TextureFilter.Nearest);
        texBack = new TextureRegion(outputTex, circleSize, circleSize);

        for (int i = 0; i < buttons.length; ++i) {
            TextureRegion n = new TextureRegion(outputTex, buttonsUVx[i], buttonsUVy[i], buttonSize, buttonSize);
            n.flip(false, true);
            TextureRegion s = new TextureRegion(outputTex,
                    buttonsUVx[i] + buttonSelectedOffset, buttonsUVy[i], buttonSize, buttonSize);
            s.flip(false, true);
            buttons[i].setTextureRegions(n, s);
        }

        shaderCircle.dispose();
        shaderDisk.dispose();
        texCircle.dispose();
        texDisk.dispose();
    }

    public int getNumber() {
        return selectedButton.number;
    }

    public void addToInputMultiplexer(InputMultiplexer im) {
        im.addProcessor(new Input());
    }

    @Override
    public void dispose() {
        texBack.getTexture().dispose();
    }
}