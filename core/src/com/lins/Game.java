package com.lins;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.lins.drawable.backbutton.BackButton;
import com.lins.screen.Screen;

/** An {@link ApplicationListener} that delegates to a {@link Screen}. <br>
 * This allows an application to easily have multiple screens. <p>
 * Screens are disposed automatically. */
public class Game implements ApplicationListener {
    /** px = dp * DP_SCALING */
    public float DP_SCALING;
    /** Convert value from dp to pixels */
    public int dp(float dp) { return (int) (dp * DP_SCALING); }

    protected final Disposer res = new Disposer();
    protected Screen screen;
    /** Add this {@link InputListener} to {@link com.lins.InputMultiplexer} */
    public final InputAdapter inputAdapter = new InputAdapter() {
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.MENU:   // Android MENU key
                    return true;
                case Input.Keys.BACK:   // Android BACK key
                case Input.Keys.BACKSPACE:
                    screen.onBackKey();
                    return true;
                case Input.Keys.ESCAPE:
                    Gdx.app.exit();
                    return true;
            }
            return false;
        }

        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (backBtn.isScreenPosInside(screenX, screenY) && screen != null) {
                backBtn.onClick();
                screen.onBackKey();
                return true;
            }
            return false;
        }

        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            backBtn.onPostClick();
            return false;
        }
    };
    /** Icon for onscreen Back button */
    public BackButton backBtn;

    public void create() {
        //DP_SCALING = (float) Gdx.graphics.getWidth() * .0042372881356f * Gdx.graphics.getDensity();
        DP_SCALING = Gdx.graphics.getDensity();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        backBtn = res.add(new BackButton(0, 0, dp(60), 3*DP_SCALING));
    }

    public void resize(int width, int height) {
        backBtn.setPos(0, height-dp(60));
        screen.resize(width, height);
    }

    public void render() {
        screen.render(Gdx.graphics.getDeltaTime());
    }

    public void pause() {
        screen.pause();
    }

    public void resume() {
        screen.resume();
    }

    public void dispose() {
        screen.hide();
        res.dispose();
    }

    public void exit() {
        Gdx.app.exit();
    }

    /** Sets the current screen. {@link Screen#hide()} is called on any old screen,
     * and {@link Screen#show()} is called on the new screen.
     * @param screen may be {@code null} */
    public void setScreen(Screen screen) {
        removeCurrentScreen();
        setNewScreen(screen);
    }

    /** Sets the current screen. {@link Screen#hide()} is called on any old screen,
     * and {@link Screen#show()} is called on the new screen.
     * @param cls The new instance of this class will be created after disposing the previous screen
     * @param parameters Parameters for creating the instance of <i>cls</i> */
    public void setScreen(Class<? extends Screen> cls, Object ... parameters) {
        Class[] types = new Class[parameters.length];
        for (int i = 0; i < parameters.length; ++i)
            types[i] = parameters[i].getClass();
        try {
            Constructor c = ClassReflection.getConstructor(cls, types);
            removeCurrentScreen();
            setNewScreen((Screen) c.newInstance(parameters));
        }
        catch (ReflectionException e) { e.printStackTrace(); }
    }

    private void removeCurrentScreen() {
        if (this.screen != null) {
            this.screen.hide();
            res.remove(this.screen);
            this.screen.dispose();
        }
    }

    private void setNewScreen(Screen screen) {
        this.screen = screen;
        if (screen != null) {
            if (!res.contains(screen)) res.add(screen);
            screen.show();
            screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    /** @return the currently active {@link Screen}. */
    public Screen getScreen() {
        return screen;
    }
}